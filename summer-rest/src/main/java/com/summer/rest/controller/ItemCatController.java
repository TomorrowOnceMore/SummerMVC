package com.summer.rest.controller;

import com.summer.common.utils.JsonUtils;
import com.summer.rest.pojo.ItemCatResult;
import com.summer.rest.service.ItemCatService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by toy on 6/25/16.
 */
@Controller
@RequestMapping("item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;
/*
    @RequestMapping("/list")
    @ResponseBody
    public Object getItemCatList(String callback) {
        ItemCatResult result = itemCatService.getItemCatList();
        if (StringUtils.isBlank(callback)) {
            return result;
        }
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }
*/
    @RequestMapping(value="/list", produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemCatList(String callback) {
        ItemCatResult result = itemCatService.getItemCatList();
        if (StringUtils.isBlank(callback)) {
            // result to string
            String json = JsonUtils.objectToJson(result);
            return json;
        }
        String json = JsonUtils.objectToJson(result);
        return callback + "(" + json + ");";
    }

}
