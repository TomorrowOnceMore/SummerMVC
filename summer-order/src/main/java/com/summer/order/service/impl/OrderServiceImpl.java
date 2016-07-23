package com.summer.order.service.impl;

import com.summer.common.pojo.SummerResult;
import com.summer.mapper.TbOrderItemMapper;
import com.summer.mapper.TbOrderMapper;
import com.summer.mapper.TbOrderShippingMapper;
import com.summer.order.component.JedisClient;
import com.summer.order.pojo.OrderInfo;
import com.summer.order.service.OrderService;
import com.summer.pojo.TbOrderItem;
import com.summer.pojo.TbOrderShipping;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by toy on 7/19/16.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ORDER_GEN_KEY}")
    private String REDIS_ORDER_GEN_KEY;

    @Value("${ORDER_ID_BEGIN}")
    private String ORDER_ID_BEGIN;

    @Value("${REDIS_ORDER_DETAIL_GEN_KEY}")
    private String REDIS_ORDER_DETAIL_GEN_KEY;

    @Override
    public SummerResult createOrder(OrderInfo orderInfo) {
        String id = jedisClient.get(REDIS_ORDER_GEN_KEY);
        if (StringUtils.isBlank(id)) {
            jedisClient.set(REDIS_ORDER_GEN_KEY, ORDER_ID_BEGIN);
        }
        Long orderId = jedisClient.incr(REDIS_ORDER_GEN_KEY);
        orderInfo.setOrderId(orderId.toString());
        //1 not pay yet, 2 paid 3 not express, 4 expressed, 5 complete 6 closed
        orderInfo.setStatus(1);
        Date date = new Date();
        orderInfo.setCreateTime(date);
        orderInfo.setUpdateTime(date);
        orderMapper.insert(orderInfo);

        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem orderItem:
             orderItems) {
            Long detailId = jedisClient.incr(REDIS_ORDER_DETAIL_GEN_KEY);
            orderItem.setId(detailId.toString());
            orderItem.setOrderId(orderId.toString());
            orderItemMapper.insert(orderItem);
        }
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId.toString());
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);

        orderShippingMapper.insert(orderShipping);
        return SummerResult.ok(orderId);

    }
}
