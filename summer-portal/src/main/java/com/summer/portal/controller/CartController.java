package com.summer.portal.controller;

import com.summer.common.pojo.SummerResult;
import com.summer.portal.pojo.CartItem;
import com.summer.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by toy on 7/18/16.
 */
@Controller
public class CartController {
    @Autowired
    CartService cartService;
    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        SummerResult summerResult =cartService.addCart(itemId, num, request, response);
        return "cartSuccess";
    }

    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request, Model model) {
        List<CartItem> list = cartService.getCartItems(request);
        model.addAttribute("cartList", list);
        return "cart";
    }

    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public SummerResult updateCartItemNum(@PathVariable Long itemId, @PathVariable Integer num,
                                          HttpServletRequest  request, HttpServletResponse response) {
        SummerResult result = cartService.updateCartItem(itemId, num, request, response);
        return result;
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,
                                HttpServletRequest  request, HttpServletResponse response) {
        SummerResult result = cartService.deleteCartItem(itemId, request, response);
        return "redirect:/cart/cart.html";
    }
}
