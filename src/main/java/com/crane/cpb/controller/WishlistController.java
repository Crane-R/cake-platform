package com.crane.cpb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crane.cpb.model.domain.ShoppingCart;
import com.crane.cpb.model.domain.User;
import com.crane.cpb.service.ShoppingCartService;
import com.crane.cpb.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 心愿单接口
 *
 * @author Xanthos
 * @date 2025/3/22 12:37
 */
@Controller
@RequestMapping("/wish")
@RequiredArgsConstructor
public class WishlistController {

    private final UserService userService;

    private final ShoppingCartService shoppingCartService;

    @GetMapping("/index")
    public ModelAndView toWishlist(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.currentUser(request);
        if (user == null) {
            return new ModelAndView("register");
        }
        shoppingCartService.setWishData(request, modelAndView);
        modelAndView.setViewName("wishlist");
        modelAndView.addObject("currentUser", user);
        return modelAndView;
    }

    @GetMapping("/addToWish/{cakeId}/{quantity}")
    public ModelAndView addToWish(@PathVariable Long cakeId, @PathVariable Integer quantity, HttpServletRequest request) {
        User user = userService.currentUser(request);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCakeId(cakeId);
        shoppingCart.setQuantity(quantity);
        shoppingCart.setUserId(user.getUserId());
        shoppingCart.setIsWish(1);
        shoppingCartService.save(shoppingCart);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/cake/grid/1");
        return modelAndView;
    }

    @GetMapping("/remove/{cakeId}")
    public ModelAndView removeFromWish(@PathVariable Long cakeId, HttpServletRequest request) {
        Long userId = userService.currentUser(request).getUserId();
        shoppingCartService.remove(new QueryWrapper<ShoppingCart>()
                .eq("user_id", userId)
                .eq("cake_id", cakeId)
                .eq("is_wish", 1));
        return new ModelAndView("redirect:/wish/index");
    }

}
