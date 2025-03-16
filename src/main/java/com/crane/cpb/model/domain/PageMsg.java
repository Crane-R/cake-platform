package com.crane.cpb.model.domain;

import lombok.Data;

/**
 * 分页信息对象
 *
 * @author Xanthos
 * @date 2025/3/2 13:21
 */
@Data
public class PageMsg {

    private Integer total;

    private Integer pageStart;

    private Integer pageEnd;

}
