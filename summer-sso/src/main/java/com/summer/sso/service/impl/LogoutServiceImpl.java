package com.summer.sso.service.impl;

import com.summer.common.pojo.SummerResult;
import com.summer.sso.component.JedisClient;
import com.summer.sso.service.LogoutService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by toy on 7/18/16.
 */
@Service
public class LogoutServiceImpl implements LogoutService {
    @Autowired
    JedisClient jedisClient;

    @Value("${REDIS_SESSION_KEY}")
    private String REDIS_SESSION_KEY;

    @Override
    public SummerResult logout(String token) {
        String json = jedisClient.get(REDIS_SESSION_KEY + ":" + token);
        if (StringUtils.isBlank(json)) {
            return SummerResult.build(400, "已经退出了");
        }
        jedisClient.del(REDIS_SESSION_KEY + ":" + token);
        return SummerResult.ok();
    }
}
