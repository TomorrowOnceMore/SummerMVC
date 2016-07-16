package com.summer.portal.service;

import com.summer.pojo.TbItem;

/**
 * Created by toy on 7/15/16.
 */
public interface ItemService {
    TbItem getItemById(Long itemId);
    String getItemDescById(Long itemId);
    String getItemParamById(Long itemId);
}
