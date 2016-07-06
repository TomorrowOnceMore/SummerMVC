package com.summer.rest.service;

import com.summer.common.pojo.SummerResult;
import com.summer.pojo.TbContent;

import java.util.List;

/**
 * Created by toy on 7/3/16.
 */
public interface ContentService {
    List<TbContent> getContentList(Long cid);
    SummerResult syncContent(Long cid);
}
