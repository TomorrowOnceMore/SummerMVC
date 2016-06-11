package com.summer.service;

import com.summer.common.pojo.EasyUIDataGridResult;
import com.summer.pojo.TbItem;

/**
 * Created by toy on 6/4/16.
 */
public interface ItemService {
    TbItem getItemById(long itemid);


    EasyUIDataGridResult getItemList(int page, int rows);
}
