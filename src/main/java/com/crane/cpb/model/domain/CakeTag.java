package com.crane.cpb.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName cake_tag
 */
@TableName(value ="cake_tag")
@Data
public class CakeTag {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long ctId;

    /**
     * 
     */
    private Long cakeId;

    /**
     * 
     */
    private Long tagId;

    /**
     * 
     */
    private Integer isDelete;
}