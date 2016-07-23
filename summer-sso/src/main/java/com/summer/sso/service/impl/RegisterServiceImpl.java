package com.summer.sso.service.impl;

import com.summer.common.pojo.SummerResult;
import com.summer.mapper.TbUserMapper;
import com.summer.pojo.TbUser;
import com.summer.pojo.TbUserExample;
import com.summer.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by toy on 7/17/16.
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbUserMapper userMapper;

    //检查数据,没有查到返回 true 查到了 返回 false

    @Override
    public SummerResult checkData(String param, int type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        // 1: username; 2: phone; 3:email
        if (1 == type) {
            criteria.andUsernameEqualTo(param);
        } else if ( 2 == type) {
            criteria.andPhoneEqualTo(param);
        } else if ( 3 == type) {
            criteria.andEmailEqualTo(param);
        }
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return SummerResult.ok(true);
        }
        return SummerResult.ok(false);
    }

    @Override
    public SummerResult register(TbUser user) {
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return SummerResult.build(400, "用户名或密码不能为空");
        }
        //数据是否重复
        //校验用户名
        SummerResult result = checkData(user.getUsername(), 1);
        if (!(Boolean)result.getData()) {
            return SummerResult.build(400, "用户名重复");
        }
        if (user.getPhone() != null) {
            result = checkData(user.getPhone(), 2);
            if (!(Boolean)result.getData()) {
                return SummerResult.build(400, "手机号重复");
            }
        }
        if (user.getEmail() != null) {
            result = checkData(user.getEmail(), 2);
            if (!(Boolean)result.getData()) {
                return SummerResult.build(400, "邮箱重复");
            }
        }
        //insert data
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //MD5 encoding
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
        return SummerResult.ok();
    }
}
