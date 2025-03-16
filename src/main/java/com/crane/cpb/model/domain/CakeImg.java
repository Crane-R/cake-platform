package com.crane.cpb.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName cake_img
 */
@TableName(value ="cake_img")
@Data
public class CakeImg {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long ciId;

    /**
     * 附件名称
     */
    private String attachmentName;

    /**
     * 
     */
    private Integer isDelete;
}