package com.summer.service;

import com.summer.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * Created by toy on 6/8/16.
 */
public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(long parentId) ;
}
