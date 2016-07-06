package com.summer.rest.controller;

import com.summer.common.pojo.SummerResult;
import com.summer.common.utils.ExceptionUtil;
import com.summer.pojo.TbContent;
import com.summer.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by toy on 7/3/16.
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/content/{cid}")
    @ResponseBody
    public SummerResult getContentList(@PathVariable Long cid) {
        try {
            List<TbContent> list = contentService.getContentList(cid);
            return SummerResult.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return SummerResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/sync/content/{cid}")
    @ResponseBody
    public SummerResult syncContent(@PathVariable Long cid) {
        try {
            SummerResult result = contentService.syncContent(cid);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return SummerResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
