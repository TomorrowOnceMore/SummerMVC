package com.summer.sso.controller;

import com.summer.common.pojo.SummerResult;
import com.summer.common.utils.ExceptionUtil;
import com.summer.sso.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by toy on 7/17/16.
 */
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public SummerResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        try {
            SummerResult summerResult = loginService.login(username, password, request, response);
            return summerResult;

        } catch (Exception e) {
            e.printStackTrace();
            return SummerResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/user/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        try {
            SummerResult result = loginService.getUserByToken(token);
            if (StringUtils.isNotBlank(callback)) {
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return SummerResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String echo(@RequestBody String call) {
        return call;
    }

}
