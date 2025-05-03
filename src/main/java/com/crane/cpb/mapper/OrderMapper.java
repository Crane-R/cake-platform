package com.crane.cpb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crane.cpb.model.domain.Order;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
* @author Xanthos
* @description 针对表【order(订单)】的数据库操作Mapper
* @createDate 2025-02-12 14:28:05
* @Entity com.crane.cpb.model.domain.Order
*/
public interface OrderMapper extends BaseMapper<Order> {

    @Select("SELECT COUNT(*) as count,  DATE(order_date) AS order_date FROM`order`WHERE DATE(order_date) BETWEEN DATE_SUB(CURDATE(), INTERVAL 6 DAY) AND CURDATE()GROUP BY  DATE(order_date);")
    List<Map<String, Object>> selectAnalysis();

}




