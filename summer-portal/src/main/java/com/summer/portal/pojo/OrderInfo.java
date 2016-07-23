package com.summer.portal.pojo;

import com.summer.pojo.TbOrder;
import com.summer.pojo.TbOrderItem;
import com.summer.pojo.TbOrderShipping;

import java.util.List;

/**
 * Created by toy on 7/19/16.
 */
public class OrderInfo extends TbOrder {
    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
