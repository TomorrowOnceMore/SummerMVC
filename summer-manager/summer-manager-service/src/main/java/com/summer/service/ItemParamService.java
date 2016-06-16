package com.summer.service;

import com.summer.common.pojo.EasyUIDataGridResult;
import com.summer.common.pojo.SummerResult;

/**
 * Created by toy on 6/15/16.
 */
public interface ItemParamService {

    SummerResult getItemParamByCid(Long cid);
    EasyUIDataGridResult getItemParamList(int page, int rows);
    SummerResult insertItemParam(Long cid, String paramDate);
}
