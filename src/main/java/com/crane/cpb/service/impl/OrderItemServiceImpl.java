package com.crane.cpb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.model.domain.OrderItem;
import com.crane.cpb.service.OrderItemService;
import com.crane.cpb.mapper.OrderItemMapper;
import org.springframework.stereotype.Service;

/**
* @author Xanthos
* @description 针对表【order_item(订单项)】的数据库操作Service实现
* @createDate 2025-02-12 14:28:07
*/
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem>
    implements OrderItemService{

}




