package com.summer.order.service;

import com.summer.common.pojo.SummerResult;
import com.summer.order.pojo.OrderInfo;

/**
 * Created by toy on 7/19/16.
 */
public interface OrderService {
    SummerResult createOrder(OrderInfo orderInfo);
}
