package com.summer.service;

import com.summer.common.pojo.EasyUITreeNode;
import com.summer.common.pojo.SummerResult;

import java.util.List;

/**
 * Created by toy on 7/2/16.
 */
public interface ContentCategoryService {
    List<EasyUITreeNode>  getContentCatList(Long parentId);
    SummerResult insertCategory(Long parentId, String name);
    SummerResult updateCategory(Long id, String name);
    SummerResult deleteCategory(Long id);
}
