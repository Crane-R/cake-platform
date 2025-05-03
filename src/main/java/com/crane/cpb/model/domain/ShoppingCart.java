package com.crane.cpb.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName shopping_cart
 */
@TableName(value ="shopping_cart")
@Data
public class ShoppingCart {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long scId;

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private Long cakeId;

    private Integer quantity;

    private Integer isWish;
}