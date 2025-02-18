package com.crane.cpb.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 地址
 * @TableName address
 */
@TableName(value ="address")
@Data
public class Address implements Serializable {
    /**
     * 收货地址
     */
    @TableId(type = IdType.AUTO)
    private Long addressId;

    /**
     * 内容
     */
    private String content;

    /**
     * 所属用户
     */
    private Long userId;

    /**
     * 
     */
    @TableLogic
    private Integer isDelete;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}