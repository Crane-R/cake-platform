package com.crane.cpb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.mapper.OrderMapper;
import com.crane.cpb.model.domain.Order;
import com.crane.cpb.model.domain.OrderItem;
import com.crane.cpb.model.domain.ShoppingCart;
import com.crane.cpb.model.domain.User;
import com.crane.cpb.model.domain.vo.CartItem;
import com.crane.cpb.service.OrderItemService;
import com.crane.cpb.service.OrderService;
import com.crane.cpb.service.ShoppingCartService;
import com.crane.cpb.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Xanthos
 * @description 针对表【order(订单)】的数据库操作Service实现
 * @createDate 2025-02-12 14:28:05
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {

    private final ShoppingCartService shoppingCartService;

    private final UserService userService;

    private final OrderItemService orderItemService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Boolean checkout(HttpServletRequest request) {
        List<CartItem> cartItems = shoppingCartService.userCartList(request,0);
        User user = userService.currentUser(request);
        Long userId = user.getUserId();
        Order order = new Order();
        order.setUserId(userId);
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            totalPrice = totalPrice.add(cartItem.getCake().getPrice().multiply(new BigDecimal(cartItem.getNum())));
        }
        order.setTotalPrice(totalPrice);
        boolean save = save(order);
        if (!save) {
            throw new RuntimeException("下单失败");
        }
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            Long cakeId = cartItem.getCake().getCakeId();
            orderItem.setCakeId(cakeId);
            orderItem.setOrderId(order.getOrderId());
            orderItem.setQuantity(cartItem.getNum());
            orderItemService.save(orderItem);
            shoppingCartService.remove(new QueryWrapper<ShoppingCart>().eq("user_id", userId).eq("cake_id", cakeId));
        }
        return true;
    }
}




