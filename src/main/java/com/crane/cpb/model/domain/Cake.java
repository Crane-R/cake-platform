package com.crane.cpb.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 蛋糕表
 * @TableName cake
 */
@TableName(value ="cake")
@Data
public class Cake implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long cakeId;

    /**
     * 蛋糕名称
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 描述
     */
    private String description;

    /**
     * 逻辑外键，商家id
     */
    private Long merchantId;

    /**
     * 
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}