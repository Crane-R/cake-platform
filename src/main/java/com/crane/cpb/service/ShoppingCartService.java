package com.crane.cpb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crane.cpb.model.domain.ShoppingCart;
import com.crane.cpb.model.domain.vo.CartItem;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Xanthos
 * @description 针对表【shopping_cart】的数据库操作Service
 * @createDate 2025-03-02 14:56:49
 */
public interface ShoppingCartService extends IService<ShoppingCart> {

    /**
     * 获取该用户的购物车列表
     *
     * @date 2025/3/2 15:34
     **/
    List<CartItem> userCartList(HttpServletRequest request);

    /**
     * 获取购物车数据
     **/
    void setCartData(HttpServletRequest request, ModelAndView modelAndView);

}
