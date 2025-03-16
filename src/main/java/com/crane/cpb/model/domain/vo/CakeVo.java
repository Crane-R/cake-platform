package com.crane.cpb.model.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 蛋糕vo
 *
 * @author Xanthos
 * @date 2025/3/2 12:50
 */
@Data
public class CakeVo {

    private Long cakeId;

    private String name;

    private String description;

    private BigDecimal price;

    private BigDecimal amount;

    private String attachmentName;

}
