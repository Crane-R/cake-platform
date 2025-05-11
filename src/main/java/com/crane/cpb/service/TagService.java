package com.crane.cpb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crane.cpb.model.domain.Tag;
import org.springframework.web.servlet.ModelAndView;

/**
* @author Xanthos
* @description 针对表【tag】的数据库操作Service
* @createDate 2025-03-16 14:50:17
*/
public interface TagService extends IService<Tag> {

    void setTagsType(ModelAndView modelAndView);

    Boolean saveTag(Tag tag);
}
