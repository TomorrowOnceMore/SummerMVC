package com.summer.portal.service;

import com.summer.common.pojo.SummerResult;
import com.summer.portal.pojo.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by toy on 7/18/16.
 */
public interface CartService {
    SummerResult addCart(Long itemId, Integer number, HttpServletRequest request, HttpServletResponse response);
    List<CartItem> getCartItems(HttpServletRequest request);
    SummerResult updateCartItem(long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);
    SummerResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response);
}
