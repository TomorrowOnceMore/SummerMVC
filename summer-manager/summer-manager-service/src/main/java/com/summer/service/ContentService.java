package com.summer.service;

import com.summer.common.pojo.EasyUIDataGridResult;
import com.summer.common.pojo.SummerResult;
import com.summer.pojo.TbContent;

/**
 * Created by toy on 7/2/16.
 */
public interface ContentService {
    EasyUIDataGridResult getContentQueryList(Long categoryId, Integer page, Integer rows);
    SummerResult insertContent(TbContent content);
}
