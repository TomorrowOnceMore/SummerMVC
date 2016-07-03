package com.summer.controller;

import com.summer.common.pojo.EasyUITreeNode;
import com.summer.common.pojo.SummerResult;
import com.summer.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by toy on 7/2/16.
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0")Long parentId) {
        List<EasyUITreeNode> list = contentCategoryService.getContentCatList(parentId);
        return list;
    }

    @RequestMapping("/create")
    @ResponseBody
    public SummerResult createNode(Long parentId, String name) {
        SummerResult result = contentCategoryService.insertCategory(parentId, name);
        return result;
    }

    @RequestMapping("/update")
    public SummerResult updateNode(Long id, String name) {
        SummerResult result = contentCategoryService.updateCategory(id, name);
        return result;
    }

    @RequestMapping("/delete")
    public SummerResult deleteNode(Long parentId, Long id) {
        SummerResult result = contentCategoryService.deleteCategory(id);
        return result;
    }
}
