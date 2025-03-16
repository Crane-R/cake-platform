package com.crane.cpb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crane.cpb.model.domain.Cake;
import com.crane.cpb.model.domain.vo.CakeVo;

import java.util.List;

/**
* @author Xanthos
* @description 针对表【cake(蛋糕表)】的数据库操作Service
* @createDate 2025-02-12 14:27:58
*/
public interface CakeService extends IService<Cake> {

    /**
     * 获取列表蛋糕
     *
     * @date 2025/3/2 12:52
     **/
    List<CakeVo> getCakeVoList();

    CakeVo toVo(Cake cake);

}
