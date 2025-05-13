package com.crane.cpb.controller;

import com.crane.cpb.model.domain.User;
import com.crane.cpb.service.OrderService;
import com.crane.cpb.service.ShoppingCartService;
import com.crane.cpb.service.UserService;
import com.crane.cpb.util.MessageUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 订单接口
 *
 * @author Xanthos
 * @date 2025/5/12 20:53
 */
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;

    private final OrderService orderService;

    private final ShoppingCartService shoppingCartService;

    @GetMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.currentUser(request);
        if (user == null) {
            return new ModelAndView("register");
        }
        orderService.setOrderData(request, modelAndView);
        shoppingCartService.setWishData(request, modelAndView);
        MessageUtil.getStrideMessage(request, modelAndView);
        modelAndView.setViewName("order");
        modelAndView.addObject("currentUser", user);
        return modelAndView;
    }

}
