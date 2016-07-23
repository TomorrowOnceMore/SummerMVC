package com.summer.sso.service.impl;

import com.summer.common.pojo.SummerResult;
import com.summer.common.utils.CookieUtils;
import com.summer.common.utils.JsonUtils;
import com.summer.mapper.TbUserMapper;
import com.summer.pojo.TbUser;
import com.summer.pojo.TbUserExample;
import com.summer.sso.component.JedisClient;
import com.summer.sso.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * Created by toy on 7/17/16.
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;
    @Value("${REDIS_SESSION_KEY}")
    private String REDIS_SESSION_KEY;
    @Override
    public SummerResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return SummerResult.build(400, "用户名或密码错误");
        }
        TbUser user = list.get(0);
        //校验密码
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return SummerResult.build(400, "用户名或密码错误");
        }
        //生成 token
        String token = UUID.randomUUID().toString();
        user.setPassword(null);
        //key: REDIS_SESSION:{TOKEN}

        //value: user转成json
        jedisClient.set(REDIS_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
        //session expire
        jedisClient.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE);
        // to cookie 关闭浏览器就过期
        CookieUtils.setCookie(request, response, "TT_TOKEN", token);
        return SummerResult.ok(token);

    }

    @Override
    public SummerResult getUserByToken(String token) {
        String json = jedisClient.get(REDIS_SESSION_KEY + ":" + token);
        //判断是否有结果
        if (StringUtils.isBlank(json)) {
            return SummerResult.build(400, "用户session已经过期");
        }
        // json -> pojo
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        // update session expire
        jedisClient.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE);
        return SummerResult.ok(user);
    }
}
