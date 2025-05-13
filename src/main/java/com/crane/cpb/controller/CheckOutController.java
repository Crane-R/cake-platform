package com.crane.cpb.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.crane.cpb.model.domain.Address;
import com.crane.cpb.model.domain.User;
import com.crane.cpb.service.*;
import com.crane.cpb.util.MessageUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
        MessageUtil.getStrideMessage(request, modelAndView);
        modelAndView.setViewName("checkout");
        shoppingCartService.setCartData(request, modelAndView);
        tagService.setTagsType(modelAndView);
        //获取地址信息
        List<Address> addressList = addressService.list(Wrappers.<Address>lambdaQuery().eq(Address::getUserId, user.getUserId()));
        modelAndView.addObject("addressList", addressList);
        modelAndView.addObject("currentUser", user);
        return modelAndView;
    }

    @PostMapping("/checkout")
    public ModelAndView checkout(HttpServletRequest request) {
        User user = userService.currentUser(request);
        if (user == null) {
            return new ModelAndView("redirect:/register");
        }
        ModelAndView modelAndView = new ModelAndView();
        //新建地址
        String address = request.getParameter("address");
        if (StrUtil.isNotEmpty(address)) {
            Address addressObj = new Address();
            addressObj.setUserId(user.getUserId());
            addressObj.setContent(address);
            addressService.save(addressObj);
        } else if (StrUtil.isNotEmpty(request.getParameter("existAddress"))) {
            address = request.getParameter("existAddress");
        } else {
            MessageUtil.setStrideErrorMessage(request, "未输入地址，请先输入地址");
            return new ModelAndView("redirect:/checkout/index");
        }
        Boolean checkout = orderService.checkout(request, address);
        if (!checkout) {
            MessageUtil.setStrideErrorMessage(request, "购物车无商品，下单失败");
        } else {
            MessageUtil.setStrideMessage(request, "下单成功");
        }
        modelAndView.setViewName("redirect:/order/index");
        return modelAndView;
    }

}
