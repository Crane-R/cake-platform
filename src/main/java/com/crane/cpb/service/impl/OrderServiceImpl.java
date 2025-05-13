package com.crane.cpb.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.mapper.OrderMapper;
import com.crane.cpb.model.domain.*;
import com.crane.cpb.model.domain.vo.CartItem;
import com.crane.cpb.model.domain.vo.OrderVo;
import com.crane.cpb.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private final CakeService cakeService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Boolean checkout(HttpServletRequest request, String address) {
        List<CartItem> cartItems = shoppingCartService.userCartList(request, 0);
        User user = userService.currentUser(request);
        Long userId = user.getUserId();
        Order order = new Order();
        order.setUserId(userId);
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            totalPrice = totalPrice.add(cartItem.getCake().getPrice().multiply(new BigDecimal(cartItem.getNum())));
        }
        //订单为0不下单
        if (totalPrice.compareTo(BigDecimal.ZERO) == 0) {
            return false;
        }
        order.setTotalPrice(totalPrice);
        order.setAddress(address);
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

    @Override
    public void setOrderData(HttpServletRequest request, ModelAndView modelAndView) {
        User user = userService.currentUser(request);
        List<Order> orderList = this.list(Wrappers.<Order>lambdaQuery().eq(Order::getUserId, user.getUserId()));
        List<OrderVo> orderVoList = new ArrayList<>();
        for (Order order : orderList) {
            OrderVo orderVo = new OrderVo();
            orderVo.setOrderId(order.getOrderId());
            orderVo.setOrderDate(DateUtil.format(order.getOrderDate(), "yyyy-MM-dd HH:mm:ss"));
            orderVo.setTotalPrice(order.getTotalPrice());
            orderVo.setAddress(order.getAddress());
            orderVo.setOrderStatus(order.getStatus() == 0 ? "进行中" : "已完成");
            List<OrderItem> orderItemList = orderItemService.list(Wrappers.<OrderItem>lambdaQuery().eq(OrderItem::getOrderId, order.getOrderId()));
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < orderItemList.size(); i++) {
                OrderItem orderItem = orderItemList.get(i);
                Cake cake = cakeService.getById(orderItem.getCakeId());
                stringBuilder.append(cake.getName()).append("x");
                stringBuilder.append(orderItem.getQuantity());
                if (i != orderItemList.size() - 1) {
                    stringBuilder.append("<br/>");
                }
            }
            orderVo.setOrderItems(stringBuilder.toString());
            orderVoList.add(orderVo);
        }
        modelAndView.addObject("orderList", orderVoList);
    }
}




