package com.summer.portal;

import com.summer.pojo.TbUser;
import com.summer.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by toy on 7/18/16.
 */
@Service
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${SSO_LOGIN_URL}")
    private String SSO_LOGIN_URL;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        TbUser user = userService.getUserByToken(httpServletRequest, httpServletResponse);
        if (user == null) {
            httpServletResponse.sendRedirect(SSO_LOGIN_URL + "?redirectURL=" + httpServletRequest.getRequestURI());
            return false;
        }
        //把用户对象放入request中
        httpServletRequest.setAttribute("usser", user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
