package com.crane.cpb.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.cpb.model.domain.Tag;
import com.crane.cpb.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签接口
 *
 * @author Xanthos
 * @date 2025/3/16 14:51
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
@CrossOrigin
public class TagController {

    private final TagService tagService;

    @GetMapping("/api/page")
    public Page<Tag> page(@RequestParam int current,
                          @RequestParam int size,
                          @RequestParam(required = false) String tagName) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Tag::getTagId);
        if (StrUtil.isNotEmpty(tagName)) {
            queryWrapper.like(Tag::getName, tagName);
        }
        Page<Tag> page = tagService.page(new Page<>(current, size), queryWrapper);
        page.getRecords().forEach(tag -> tag.setIsTypeStr(tag.getIsType() == 1 ? "是" : "否"));
        return page;
    }

    @PostMapping("/api/save")
    public Boolean saveTag(@RequestBody Tag tag) {
        return tagService.saveOrUpdate(tag);
    }

    @GetMapping("/api/delete/{tagId}")
    public Boolean deleteTag(@PathVariable Long tagId) {
        return tagService.removeById(tagId);
    }

    @GetMapping("/api/list")
    public List<Tag> list() {
        return tagService.list();
    }

}
