package com.summer.rest.service.impl;

import com.summer.common.utils.JsonUtils;
import com.summer.mapper.TbItemDescMapper;
import com.summer.mapper.TbItemMapper;
import com.summer.mapper.TbItemParamItemMapper;
import com.summer.pojo.*;
import com.summer.rest.component.JedisClient;
import com.summer.rest.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by toy on 7/13/16.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;

    @Value("${ITEM_DESC_KEY}")
    private String ITEM_DESC_KEY;

    @Value("${ITEM_BASE_INFO_KEY}")
    private String ITEM_BASW_INFO_KEY;

    @Value("${ITEM_PARAM_KEY}")
    private String ITEM_PARAM_KEY;

    @Value("${ITEM_EXPIRE_SECOND}")
    private Integer ITEM_EXPIRE_SECOND;
    @Override
    public TbItem getItemById(Long itemId) {
        String redisKey = REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_BASW_INFO_KEY;
        //查询缓存 如果有 直接返回
        try {
            String json = jedisClient.get(redisKey);
            if (StringUtils.isNotBlank(json)) {
                // json -> java
                TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        try {
            //向redis中添加缓存 并设置过期时间 不能影响正常逻辑
            //使用String数据类型,为了便于分组可以使用":"分隔命名方式
            jedisClient.set(redisKey, JsonUtils.objectToJson(item));
            jedisClient.expire(redisKey, ITEM_EXPIRE_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public TbItemDesc getItemDescById(Long itemId) {
        String redisKey = REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_DESC_KEY;
        //查询缓存 如果有 直接返回
        try {
            String json = jedisClient.get(redisKey);
            if (StringUtils.isNotBlank(json)) {
                // json -> java
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return itemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        try {
            //向redis中添加缓存 并设置过期时间 不能影响正常逻辑
            //使用String数据类型,为了便于分组可以使用":"分隔命名方式
            jedisClient.set(redisKey, JsonUtils.objectToJson(itemDesc));
            jedisClient.expire(redisKey, ITEM_EXPIRE_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc;
    }

    @Override
    public TbItemParamItem getItemParamById(Long itemId) {
        String redisKey = REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_PARAM_KEY;
        //查询缓存 如果有 直接返回
        try {
            String json = jedisClient.get(redisKey);
            if (StringUtils.isNotBlank(json)) {
                // json -> java
                TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return itemParamItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size() > 0) {
            TbItemParamItem itemParamItem = list.get(0);
            try {
                //向redis中添加缓存 并设置过期时间 不能影响正常逻辑
                //使用String数据类型,为了便于分组可以使用":"分隔命名方式
                jedisClient.set(redisKey, JsonUtils.objectToJson(itemParamItem));
                jedisClient.expire(redisKey, ITEM_EXPIRE_SECOND);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return itemParamItem;
        }
        return null;
    }
}
