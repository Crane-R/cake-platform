package com.crane.cpb.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Xanthos
 * @TableName tag
 */
@TableName(value = "tag")
@Data
public class Tag {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long tagId;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 是否是分类，0不是，1是
     */
    private Integer isType;

    /**
     *
     */
    private Integer isDelete;

    @TableField(exist = false)
    private String isTypeStr;
}