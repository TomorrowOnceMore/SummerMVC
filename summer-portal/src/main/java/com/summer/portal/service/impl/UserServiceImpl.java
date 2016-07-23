package com.summer.portal.service.impl;

import com.summer.common.pojo.SummerResult;
import com.summer.common.utils.CookieUtils;
import com.summer.common.utils.HttpClientUtil;
import com.summer.common.utils.JsonUtils;
import com.summer.pojo.TbUser;
import com.summer.portal.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by toy on 7/18/16.
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;
    @Value("${SSO_USER_TOKEN_SERVICE}")
    private String SSO_USER_TOKEN_SERVICE;

    @Override
    public TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response) {
        try {
        // cookie -> token
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN_SERVICE + token);
        SummerResult result = SummerResult.format(json);
        if (result.getStatus() != 200) {
            return null;
        }
        result = SummerResult.formatToPojo(json, TbUser.class);
        TbUser user = (TbUser)result.getData();
        return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
