package com.crane.cpb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crane.cpb.model.domain.Order;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
* @author Xanthos
* @description 针对表【order(订单)】的数据库操作Service
* @createDate 2025-02-12 14:28:05
*/
public interface OrderService extends IService<Order> {

    Boolean checkout(HttpServletRequest request, String address);

    void setOrderData(HttpServletRequest request, ModelAndView modelAndView);

}
