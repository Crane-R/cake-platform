package com.crane.cpb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.mapper.CakeImgMapper;
import com.crane.cpb.mapper.CakeMapper;
import com.crane.cpb.model.domain.Cake;
import com.crane.cpb.model.domain.CakeImg;
import com.crane.cpb.model.domain.vo.CakeVo;
import com.crane.cpb.service.CakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        CakeImg ciId = cakeImgMapper.selectList(new QueryWrapper<CakeImg>().eq("ci_id", cake.getCakeId())).getFirst();
        cakeVo.setAttachmentName(ciId.getAttachmentName());
        return cakeVo;
    }
}




