package com.crane.cpb.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 商家
 * @TableName merchant
 */
@TableName(value ="merchant")
@Data
public class Merchant implements Serializable {
    /**
     * 商家id
     */
    @TableId(type = IdType.AUTO)
    private Long merchantId;

    /**
     * 商家码，系统生成
     */
    private String code;

    /**
     * 商家名称
     */
    private String name;

    /**
     * 联系信息
     */
    private String contactInfo;

    /**
     * 地址
     */
    private String address;

    /**
     * 
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}