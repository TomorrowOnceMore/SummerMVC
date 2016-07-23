package com.summer.sso.controller;

import com.summer.common.pojo.SummerResult;
import com.summer.common.utils.ExceptionUtil;
import com.summer.pojo.TbUser;
import com.summer.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by toy on 7/17/16.
 */
@Controller
@RequestMapping("/user")
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {

        try {
            SummerResult result = registerService.checkData(param, type);
            if (StringUtils.isNotBlank(callback)) {
                //请求为jsonp
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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public SummerResult register(TbUser user) {
        try {
            SummerResult result = registerService.register(user);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return SummerResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
