package com.crane.cpb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.mapper.CakeTagMapper;
import com.crane.cpb.mapper.TagMapper;
import com.crane.cpb.model.domain.Tag;
import com.crane.cpb.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @author Xanthos
 * @description 针对表【tag】的数据库操作Service实现
 * @createDate 2025-03-16 14:50:17
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
        implements TagService {

    private final TagMapper tagMapper;

    private final CakeTagMapper cakeTagMapper;

    @Override
    public void setTagsType(ModelAndView modelAndView) {
        //应该只给前几个，不能太多，选出标签数最多的前十个
        List<Map<String, Object>> tagMaps = cakeTagMapper.selectTagListDesc10();
        List<Object> tagId = tagMaps.stream().map(e -> e.get("tag_id")).toList();
        modelAndView.addObject("typeList", tagMapper.selectList(new QueryWrapper<Tag>().in("tag_id", tagId)));
    }

    @Override
    public Boolean saveTag(Tag tag) {
        return super.saveOrUpdate(tag);
    }
}




