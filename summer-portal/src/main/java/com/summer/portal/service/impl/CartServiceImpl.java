package com.summer.portal.service.impl;

import com.summer.common.pojo.SummerResult;
import com.summer.common.utils.CookieUtils;
import com.summer.common.utils.JsonUtils;
import com.summer.pojo.TbItem;
import com.summer.portal.pojo.CartItem;
import com.summer.portal.service.CartService;
import com.summer.portal.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by toy on 7/18/16.
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ItemService itemService;
    @Value("${COOKIE_EXPIRE}")
    private Integer COOKIE_EXPIRE;
    @Override
    public SummerResult addCart(Long itemId, Integer number, HttpServletRequest request, HttpServletResponse response) {
        //从购物车中取出购物列表
        List<CartItem> itemList = getCartItemList(request);
        boolean haveFlg = false;
        for (CartItem cartItem: itemList) {
            //商品存在
            if (cartItem.getId().longValue() == itemId) {
                cartItem.setNum(cartItem.getNum() + number);
                haveFlg = true;
                break;
            }
        }
        if (!haveFlg) {
            TbItem item = itemService.getItemById(itemId);
            CartItem cartItem = new CartItem();
            cartItem.setId(itemId);
            cartItem.setNum(number);
            cartItem.setPrice(item.getPrice());
            cartItem.setTitle(item.getTitle());
            if (StringUtils.isNotBlank(item.getImage())) {
                String image = item.getImage();
                String[] strings = image.split(",");
                cartItem.setImage(strings[0]);
            }
            itemList.add(cartItem);
        }
       //商品写入cookie
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(itemList), COOKIE_EXPIRE);
        return SummerResult.ok();
    }

    @Override
    public List<CartItem> getCartItems(HttpServletRequest request) {
        List<CartItem> list = getCartItemList(request);
        return list;
    }

    @Override
    public SummerResult updateCartItem(long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> itemList = getCartItemList(request);
        for (CartItem cartItem: itemList) {
            if (cartItem.getId() == itemId) {
                cartItem.setNum(num);
                break;
            }
        }
        //写入cookie
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(itemList), COOKIE_EXPIRE);
        return SummerResult.ok();

    }

    @Override
    public SummerResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> itemList = getCartItemList(request);
        for (CartItem cartItem: itemList) {
            if (cartItem.getId() == itemId) {
                itemList.remove(cartItem);
                break;
            }
        }
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(itemList), COOKIE_EXPIRE);
        return SummerResult.ok();
    }

    private List<CartItem> getCartItemList(HttpServletRequest request) {
        try {
            //cookie中取商品列表
            String json = CookieUtils.getCookieValue(request, "TT_CART", true);
            //json -> pojo
            List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
            return list==null?new ArrayList<>():list;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
