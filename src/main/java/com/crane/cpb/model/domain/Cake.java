package com.crane.cpb.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 蛋糕表
 *
 * @TableName cake
 */
@TableName(value = "cake")
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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date launchDate;

    @TableField(exist = false)
    private String launchDateStr;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<Long> tagIds;

    @TableField(exist = false)
    private List<CakeImg> imgList;
}