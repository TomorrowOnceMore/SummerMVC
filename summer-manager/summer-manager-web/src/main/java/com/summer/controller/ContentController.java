package com.summer.controller;

import com.summer.common.pojo.EasyUIDataGridResult;
import com.summer.common.pojo.SummerResult;
import com.summer.pojo.TbContent;
import com.summer.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by toy on 7/2/16.
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    ContentService contentService;

    @RequestMapping("/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentQueryList(Long categoryId,Integer page, Integer rows) {
        EasyUIDataGridResult result = contentService.getContentQueryList(categoryId,page, rows);
        return result;
    }

    @RequestMapping("/save")
    @ResponseBody
    public SummerResult insertContent(TbContent content) {
        SummerResult result = contentService.insertContent(content);
        return result;
    }
}
