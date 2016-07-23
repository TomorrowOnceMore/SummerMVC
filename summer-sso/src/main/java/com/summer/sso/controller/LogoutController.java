package com.summer.sso.controller;

import com.summer.common.pojo.SummerResult;
import com.summer.common.utils.ExceptionUtil;
import com.summer.sso.service.LogoutService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by toy on 7/18/16.
 */
@Controller
public class LogoutController {
    @Autowired
    private LogoutService logoutService;

    @RequestMapping("/user/logout/{token}")
    @ResponseBody
    public Object logout(@PathVariable String token, String callback) {
        try {
            SummerResult result = logoutService.logout(token);
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
}
