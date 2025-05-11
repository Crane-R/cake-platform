package com.crane.cpb.controller;

import com.crane.cpb.model.domain.User;
import com.crane.cpb.service.OrderService;
import com.crane.cpb.service.ShoppingCartService;
import com.crane.cpb.service.TagService;
import com.crane.cpb.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 结账相关接口
 *
 * @author Xanthos
 * @date 2025/3/16 21:39
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckOutController {

    private final ShoppingCartService shoppingCartService;

    private final TagService tagService;

    private final UserService userService;

    private final OrderService orderService;

    @GetMapping("/index")
    public ModelAndView toCheckout(HttpServletRequest request) {
        User user = userService.currentUser(request);
        if (user == null) {
            return new ModelAndView("redirect:/register");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("checkout");
        shoppingCartService.setCartData(request, modelAndView);
        tagService.setTagsType(modelAndView);
        modelAndView.addObject("currentUser",user);
        return modelAndView;
    }

    @GetMapping("/checkout")
    public Boolean checkout(HttpServletRequest request) {
        return orderService.checkout(request);
    }

}
