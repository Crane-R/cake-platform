package com.crane.cpb.mapper;

import com.crane.cpb.model.domain.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author Xanthos
 * @description 针对表【tag】的数据库操作Mapper
 * @createDate 2025-03-16 14:50:17
 * @Entity com.crane.cpb.model.domain.Tag
 */
public interface TagMapper extends BaseMapper<Tag> {

    @Select("select cake_id,CAST(SUM(quantity) AS SIGNED) as sum from order_item group by cake_id order by sum desc")
    List<Map<String, Long>> getCakeIdSum();

}




