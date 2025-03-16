package com.crane.cpb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.mapper.CakeImgMapper;
import com.crane.cpb.mapper.CakeMapper;
import com.crane.cpb.mapper.CakeTagMapper;
import com.crane.cpb.mapper.TagMapper;
import com.crane.cpb.model.domain.Cake;
import com.crane.cpb.model.domain.CakeImg;
import com.crane.cpb.model.domain.CakeTag;
import com.crane.cpb.model.domain.Tag;
import com.crane.cpb.model.domain.vo.CakeVo;
import com.crane.cpb.service.CakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xanthos
 * @description 针对表【cake(蛋糕表)】的数据库操作Service实现
 * @createDate 2025-02-12 14:27:58
 */
@Service
@RequiredArgsConstructor
public class CakeServiceImpl extends ServiceImpl<CakeMapper, Cake>
        implements CakeService {

    private final CakeMapper cakeMapper;

    private final CakeImgMapper cakeImgMapper;

    private final TagMapper tagMapper;

    private final CakeTagMapper cakeTagMapper;

    @Override
    public List<CakeVo> getCakeVoList() {
        List<Cake> cakes = cakeMapper.selectList(null);
        List<CakeVo> cakeVoList = new ArrayList<>();
        for (Cake cake : cakes) {
            cakeVoList.add(toVo(cake));
        }
        return cakeVoList;
    }

    @Override
    public CakeVo toVo(Cake cake) {
        CakeVo cakeVo = new CakeVo();
        cakeVo.setDescription(cake.getDescription());
        cakeVo.setName(cake.getName());
        cakeVo.setPrice(cake.getPrice());
        cakeVo.setCakeId(cake.getCakeId());
        List<CakeImg> cakeImgList = cakeImgMapper.selectList(new QueryWrapper<CakeImg>().eq("cake_id", cake.getCakeId()));
        if (!cakeImgList.isEmpty()) {
            cakeVo.setAttachmentName(cakeImgList.getFirst().getAttachmentName());
            cakeVo.setCakeImgList(cakeImgList);
        }
        List<Long> tagIds = cakeTagMapper.selectList(new QueryWrapper<CakeTag>().eq("cake_id", cake.getCakeId()))
                .stream().map(CakeTag::getTagId).toList();
        if (!tagIds.isEmpty()) {
            List<Tag> tagList = tagMapper.selectList(new QueryWrapper<Tag>().in("tag_id", tagIds));
            cakeVo.setTagList(tagList);
        }
        return cakeVo;
    }

    @Override
    public void setCarouselImage(ModelAndView modelAndView) {
        //todo：应该是根据订单量获取订单量最高的前三个
        List<CakeVo> list = list(new QueryWrapper<Cake>().last("limit 5")).stream().map(this::toVo).toList();
        modelAndView.addObject("carouselImages", list);
    }
}




