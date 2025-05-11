package com.crane.cpb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.mapper.CakeTagMapper;
import com.crane.cpb.mapper.TagMapper;
import com.crane.cpb.model.domain.CakeTag;
import com.crane.cpb.model.domain.Tag;
import com.crane.cpb.service.CakeTagService;
import com.crane.cpb.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

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

    private final CakeTagService cakeTagService;

    @Override
    public void setTagsType(ModelAndView modelAndView) {
        //应该只给前几个，不能太多，选出标签数最多的前十个
        List<Map<String, Object>> tagMaps = cakeTagMapper.selectTagListDesc10();
        List<Object> tagId = tagMaps.stream().map(e -> e.get("tag_id")).toList();
        if (!tagId.isEmpty()) {
            modelAndView.addObject("typeList", tagMapper.selectList(new QueryWrapper<Tag>().in("tag_id", tagId)));
        }
    }

    @Override
    public Boolean saveTag(Tag tag) {
        return super.saveOrUpdate(tag);
    }

    /**
     * 获取热门类别
     * 采用最大堆实现自动排序
     **/
    @Override
    public void setHotCategory(ModelAndView modelAndView) {
        List<Map<String, Long>> cakeIdSum = tagMapper.getCakeIdSum();
        //tagId:count
        Map<String, Long> tagCountMap = new HashMap<>();
        for (Map<String, Long> tagMap : cakeIdSum) {
            Long cakeId = tagMap.get("cake_id");
            List<CakeTag> tagList = cakeTagService.list(Wrappers.<CakeTag>lambdaQuery().eq(CakeTag::getCakeId, cakeId));
            for (CakeTag cakeTag : tagList) {
                String tagId = cakeTag.getTagId().toString();
                tagCountMap.put(tagId, tagCountMap.getOrDefault(tagId, 0L) + tagMap.get("sum"));
            }
        }
        if (tagCountMap.isEmpty()) {
            return;
        }
        PriorityQueue<Map.Entry<String, Long>> maxHeap = new PriorityQueue<>((a, b) ->
                b.getValue().compareTo(a.getValue()));
        maxHeap.addAll(tagCountMap.entrySet());
        List<Tag> tagList = new ArrayList<>();
        for (int i = 0; i < (Math.min(maxHeap.size(), 6)); i++) {
            Map.Entry<String, Long> poll = maxHeap.poll();
            assert poll != null;
            tagList.add(tagMapper.selectById(poll.getKey()));
        }
        modelAndView.addObject("hotCategory", tagList);
    }


}




