package com.crane.cpb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.model.domain.Address;
import com.crane.cpb.service.AddressService;
import com.crane.cpb.mapper.AddressMapper;
import org.springframework.stereotype.Service;

/**
* @author Xanthos
* @description 针对表【address(地址)】的数据库操作Service实现
* @createDate 2025-02-12 14:27:53
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
    implements AddressService{

}




