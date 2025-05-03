package com.crane.cpb.mapper;

import com.crane.cpb.model.domain.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Xanthos
 * @description 针对表【order_item(订单项)】的数据库操作Mapper
 * @createDate 2025-02-12 14:28:07
 * @Entity com.crane.cpb.model.domain.OrderItem
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    @Select("select sum(quantity) sum,cake_id from order_item group by cake_id order by sum desc limit 6")
    List<Map<String, Object>> select5Cake();

    @Select("select sum(quantity) from order_item")
    BigDecimal cakeSum();

}




