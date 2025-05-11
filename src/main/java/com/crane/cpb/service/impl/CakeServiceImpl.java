package com.crane.cpb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.mapper.*;
import com.crane.cpb.model.domain.*;
import com.crane.cpb.model.domain.vo.CakeVo;
import com.crane.cpb.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

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

    private final CakeTagService cakeTagService;

    private final UserService userService;

    private final MerchantService merchantService;

    private final OrderItemMapper orderItemMapper;

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
        cakeVo.setLaunchDate(cake.getLaunchDate());
        return cakeVo;
    }

    @Override
    public void setCarouselImage(ModelAndView modelAndView) {
        List<Long> cakeIds = orderItemMapper.select5Cake().stream().map(m ->
                Long.parseLong(m.get("cake_id").toString())).toList();
        List<CakeVo> list = list(new QueryWrapper<Cake>().in("cake_id", cakeIds)).stream().map(this::toVo).toList();
        modelAndView.addObject("carouselImages", list);
    }

    @Override
    public void setLatestCake(ModelAndView modelAndView) {
        List<CakeVo> list = this.list(new QueryWrapper<Cake>().orderByAsc("launch_date").last("limit 8"))
                .stream().map(this::toVo).toList();
        modelAndView.addObject("latestCake", list);
    }

    @Override
    public void setHotCake(ModelAndView modelAndView) {
        List<Long> cakeIds = new ArrayList<>();
        List<Map<String, Object>> hotCake = orderItemMapper.getHotCake();
        for (Map<String, Object> map : hotCake) {
            cakeIds.add((Long) map.get("cake_id"));
        }
        List<CakeVo> list = this.list(Wrappers.<Cake>lambdaQuery().in(Cake::getCakeId, cakeIds)).stream().map(this::toVo).toList();
        modelAndView.addObject("hotCake", list);
    }

//    @Override
//    public ModelAndView queryPage(QueryWrapper<Cake> queryWrapper, Integer pageNum, HttpServletRequest request) {
//
//        return modelAndView;
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Cake saveCake(Cake cake, HttpServletRequest request) {
        User user = userService.currentUser(request);
        if (user.getIdentity() == 0) {
            throw new RuntimeException("用户无法添加");
        }
        Merchant merchant = merchantService.getById(user.getIdentityIndex());
        cake.setMerchantId(merchant.getMerchantId());
        boolean cakeSaved = super.saveOrUpdate(cake);
        if (!cakeSaved) {
            throw new RuntimeException("保存失败");
        }
        List<Long> tagIds = cake.getTagIds();
        if (tagIds == null) {
            return cake;
        }
        List<CakeTag> addList = new ArrayList<>();
        for (Long tagId : tagIds) {
            CakeTag cakeTag = new CakeTag();
            cakeTag.setTagId(tagId);
            cakeTag.setCakeId(cake.getCakeId());
            addList.add(cakeTag);
        }
        cakeTagService.saveOrUpdateBatch(addList);
        return cake;
    }

}




