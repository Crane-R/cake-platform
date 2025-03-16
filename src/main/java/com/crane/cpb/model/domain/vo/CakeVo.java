package com.crane.cpb.model.domain.vo;

import com.crane.cpb.model.domain.CakeImg;
import com.crane.cpb.model.domain.Tag;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

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

    private List<CakeImg> cakeImgList;

    private List<Tag> tagList;

}
