package com.crane.cpb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.mapper.ShoppingCartMapper;
import com.crane.cpb.model.domain.Cake;
import com.crane.cpb.model.domain.ShoppingCart;
import com.crane.cpb.model.domain.User;
import com.crane.cpb.model.domain.vo.CakeVo;
import com.crane.cpb.model.domain.vo.CartItem;
import com.crane.cpb.service.CakeService;
import com.crane.cpb.service.ShoppingCartService;
import com.crane.cpb.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xanthos
 * @description 针对表【shopping_cart】的数据库操作Service实现
 * @createDate 2025-03-02 14:56:49
 */
@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
        implements ShoppingCartService {

    private final UserService userService;

    private final CakeService cakeService;

    @Override
    public List<CartItem> userCartList(HttpServletRequest request, int isWish) {
        User user = userService.currentUser(request);
        if(user == null) {
            return new ArrayList<>();
        }
        QueryWrapper<ShoppingCart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user.getUserId());
        wrapper.eq("is_wish", isWish);
        List<ShoppingCart> shoppingCarts = this.list(wrapper);
        List<CartItem> resultList = new ArrayList<>();
        shoppingCarts.forEach(item -> {
            CartItem cartItem = new CartItem();
            int num = item.getQuantity();
            Cake cake = cakeService.getById(item.getCakeId());
            CakeVo cakeVo = cakeService.toVo(cake);
            cakeVo.setAmount(cake.getPrice().multiply(new BigDecimal(num)));
            cartItem.setCake(cakeVo);
            cartItem.setNum(num);
            resultList.add(cartItem);
        });
        return resultList;
    }

    @Override
    public void setCartData(HttpServletRequest request, ModelAndView modelAndView) {
        List<CartItem> cartItems = userCartList(request, 0);
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            subtotal = subtotal.add(cartItem.getCake().getPrice().multiply(new BigDecimal(cartItem.getNum())));
        }
        modelAndView.addObject("cartItems", cartItems);
        modelAndView.addObject("subtotal", subtotal);
        modelAndView.addObject("cartSize", cartItems.size());
    }

    @Override
    public void setWishData(HttpServletRequest request, ModelAndView modelAndView) {
        List<CartItem> cartItems = userCartList(request, 1);
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            subtotal = subtotal.add(cartItem.getCake().getPrice().multiply(new BigDecimal(cartItem.getNum())));
        }
        modelAndView.addObject("wishItems", cartItems);
        modelAndView.addObject("subtotal", subtotal);
        modelAndView.addObject("cartSize", cartItems.size());
    }

}




