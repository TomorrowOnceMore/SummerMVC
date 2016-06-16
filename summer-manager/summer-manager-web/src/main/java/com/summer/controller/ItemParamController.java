package com.summer.controller;

import com.summer.common.pojo.EasyUIDataGridResult;
import com.summer.common.pojo.SummerResult;
import com.summer.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by toy on 6/15/16.
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping("/query/itemcatid/{cid}")
    @ResponseBody
    public SummerResult getItemCatByCid(@PathVariable Long cid) {
        SummerResult result = itemParamService.getItemParamByCid(cid);
        return result;
    }


    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        EasyUIDataGridResult result = itemParamService.getItemParamList(page, rows);
        return result;
    }

    @RequestMapping("/save/{cid}")
    @ResponseBody
    public SummerResult insertItemParam(@PathVariable Long cid, String paramData) {
        SummerResult result = itemParamService.insertItemParam(cid, paramData);
        return result;
    }
}
