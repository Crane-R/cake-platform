package com.crane.cpb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.model.domain.Order;
import com.crane.cpb.service.OrderService;
import com.crane.cpb.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author Xanthos
* @description 针对表【order(订单)】的数据库操作Service实现
* @createDate 2025-02-12 14:28:05
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




