package com.crane.cpb.model.domain.vo;

import lombok.Data;

/**
 * 购物车vo
 *
 * @author Xanthos
 * @date 2025/3/2 15:40
 */
@Data
public class CartItem {

    private CakeVo cake;

    private Integer num;

}
