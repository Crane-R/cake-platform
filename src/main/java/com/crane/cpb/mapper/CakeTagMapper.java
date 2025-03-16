package com.crane.cpb.mapper;

import com.crane.cpb.model.domain.CakeTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author Xanthos
 * @description 针对表【cake_tag】的数据库操作Mapper
 * @createDate 2025-03-16 15:17:36
 * @Entity com.crane.cpb.model.domain.CakeTag
 */
public interface CakeTagMapper extends BaseMapper<CakeTag> {

    @Select("select tag.tag_id,count(*) tagCount from cake_tag right join tag on cake_tag.tag_id = tag.tag_id " +
            "where cake_tag.cake_id is not null and tag.is_type = 1 group by cake_tag.tag_id order by tagCount desc limit 11")
    List<Map<String, Object>> selectTagListDesc10();

}




