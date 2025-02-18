package com.crane.cpb.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单项
 * @TableName order_item
 */
@TableName(value ="order_item")
@Data
public class OrderItem implements Serializable {
    /**
     * 订单项id
     */
    @TableId(type = IdType.AUTO)
    private Long orderItemId;

    /**
     * 对应的蛋糕id
     */
    private Long cakeId;

    /**
     * 所属的订单编号
     */
    private String orderId;

    /**
     * 该种蛋糕的订购数量
     */
    private Integer quantity;

    /**
     * 
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}