package com.crane.cpb.controller;

import com.crane.cpb.model.domain.Address;
import com.crane.cpb.model.domain.User;
import com.crane.cpb.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final AddressService addressService;

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
        modelAndView.addObject("currentUser", user);
        return modelAndView;
    }

    @PostMapping("/checkout")
    public ModelAndView checkout(HttpServletRequest request) {
        User user = userService.currentUser(request);
        if (user == null) {
            return new ModelAndView("redirect:/register");
        }
        //新建地址
        String address = request.getParameter("address");
        Address addressObj = new Address();
        addressObj.setUserId(user.getUserId());
        addressObj.setContent(address);
        addressService.save(addressObj);
        orderService.checkout(request);
        ModelAndView modelAndView = new ModelAndView();
        request.getSession().setAttribute("orderTrue", true);
        modelAndView.setViewName("redirect:/index");
        return modelAndView;
    }

}
