package com.crane.cpb.model.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Xanthos
 * @date 2025/5/12 21:07
 */
@Data
public class OrderVo {

    private String orderId;

    private String orderDate;

    private BigDecimal totalPrice;

    private String orderItems;

    private String orderStatus;

    private String address;

}
