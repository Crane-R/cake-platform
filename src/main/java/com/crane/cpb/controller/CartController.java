package com.crane.cpb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crane.cpb.model.domain.ShoppingCart;
import com.crane.cpb.model.domain.User;
import com.crane.cpb.service.ShoppingCartService;
import com.crane.cpb.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 购物车接口
 *
 * @author Xanthos
 * @date 2025/3/2 14:59
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final ShoppingCartService shoppingCartService;

    private final UserService userService;

    /**
     * 添加商品到购物车
     *
     * @date 2025/3/2 15:00
     **/
    @GetMapping("/addToCart/{cakeId}/{quantity}")
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public ModelAndView addToCart(@PathVariable Long cakeId, @PathVariable Integer quantity, HttpServletRequest request) {
        if (quantity <= 0) {
            quantity = 1;
        }
        User user = userService.currentUser(request);
        if (user == null) {
            return new ModelAndView("register");
        }
        Long userId = user.getUserId();
        //判断购物车商品数量是不是超过50，最大50
        ShoppingCart one = shoppingCartService.getOne(new QueryWrapper<ShoppingCart>().eq("user_id", userId).eq("cake_id", cakeId));
        long cardCount = one == null ? 0 : one.getQuantity();
        ModelAndView modelAndView = new ModelAndView();
        if (cardCount >= 50) {
            request.getSession().setAttribute("addFail", true);
        } else {
            if (cardCount == 0) {
                ShoppingCart shoppingCart = new ShoppingCart();
                shoppingCart.setCakeId(cakeId);
                shoppingCart.setUserId(userId);
                shoppingCart.setQuantity(quantity);
                shoppingCart.setIsWish(0);
                shoppingCartService.save(shoppingCart);
            } else {
                one.setQuantity(one.getQuantity() + quantity);
                one.setIsWish(0);
                shoppingCartService.updateById(one);
            }
        }
        modelAndView.setViewName("redirect:/cake/grid/1/");
        return modelAndView;
    }

    @GetMapping("/remove/{cakeId}")
    public ModelAndView removeFromCart(@PathVariable Long cakeId, HttpServletRequest request) {
        Long userId = userService.currentUser(request).getUserId();
        shoppingCartService.remove(new QueryWrapper<ShoppingCart>().eq("user_id", userId).eq("cake_id", cakeId));
        return new ModelAndView("redirect:/cart/index");
    }

    /**
     * 跳转至购物车页
     *
     * @date 2025/3/2 16:27
     **/
    @GetMapping("/index")
    public ModelAndView jumpToCart(HttpServletRequest request) {
        User user = userService.currentUser(request);
        if (user == null) {
            return new ModelAndView("register");
        }
        long cardCount = shoppingCartService.count(new QueryWrapper<ShoppingCart>().eq("user_id", user.getUserId()));
        ModelAndView modelAndView = new ModelAndView();
        if (cardCount == 0) {
            modelAndView.setViewName("cart_empty");
            return modelAndView;
        }
        shoppingCartService.setCartData(request, modelAndView);
        modelAndView.setViewName("cart");
        modelAndView.addObject("currentUser", user);
        return modelAndView;
    }


}
