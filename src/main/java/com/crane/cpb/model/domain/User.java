package com.crane.cpb.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名称，用户输入或者系统生成
     */
    private String username;

    /**
     *
     */
    private String password;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 电子邮件
     */
    private String email;

    private BigDecimal balance;

    /**
     * 身份，0顾客1商家2管理员
     */
    private Integer identity;

    @TableField(exist = false)
    private String identityName;

    /**
     * 身份索引，对应身份信息的主键
     */
    private Long identityIndex;

    /**
     * 是否删除，0否1是
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}