package com.crane.cpb.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 * @TableName order
 */
@TableName(value ="`order`")
@Data
public class Order implements Serializable {
    /**
     * 系统生成的订单号
     */
    @TableId
    private String orderId;

    /**
     * 下单日期
     */
    private Date orderDate;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    private String address;

    /**
     * 状态，0进行中1已完成（收货了）
     */
    private Integer status;

    /**
     * 所属用户
     */
    private Long userId;

    /**
     * 
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}