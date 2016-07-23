package com.summer.portal.service.impl;

import com.summer.common.pojo.SummerResult;
import com.summer.common.utils.HttpClientUtil;
import com.summer.common.utils.JsonUtils;
import com.summer.portal.pojo.OrderInfo;
import com.summer.portal.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by toy on 7/19/16.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Value("${ORDER_CREATE_URL}")
    private String ORDER_CREATE_URL;

    @Value("${ORDER_BASE_URL}")
    private String OREDER_BASE_URL;
    @Override
    public String createOrder(OrderInfo orderInfo) {
        //pojo -> json
        String json = JsonUtils.objectToJson(orderInfo);
        String jsonResult = HttpClientUtil.doPostJson(OREDER_BASE_URL + ORDER_CREATE_URL, json);

        SummerResult summerResult = SummerResult.format(jsonResult);
        //取订单号
        String orderId = summerResult.getData().toString();
        return orderId;
    }
}
