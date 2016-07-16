package com.summer.rest.service;

import com.summer.pojo.TbItem;
import com.summer.pojo.TbItemDesc;
import com.summer.pojo.TbItemParamItem;

/**
 * Created by toy on 7/13/16.
 */
public interface ItemService {

    TbItem getItemById(Long itemId);
    TbItemDesc getItemDescById(Long itemId);
    TbItemParamItem getItemParamById(Long itemId);
}
