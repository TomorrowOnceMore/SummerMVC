package com.summer.order.controller;

import com.summer.common.pojo.SummerResult;
import com.summer.common.utils.ExceptionUtil;
import com.summer.order.pojo.OrderInfo;
import com.summer.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by toy on 7/19/16.
 */
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value="/order/create", method = RequestMethod.POST)
    @ResponseBody
    public SummerResult createOrder(@RequestBody OrderInfo orderInfo) {
        try {
            SummerResult result = orderService.createOrder(orderInfo);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return SummerResult.build(500, ExceptionUtil.getStackTrace(e));
        }

    }
}
