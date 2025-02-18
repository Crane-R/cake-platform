package com.crane.cpb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.model.domain.Customer;
import com.crane.cpb.service.CustomerService;
import com.crane.cpb.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

/**
* @author Xanthos
* @description 针对表【customer(顾客表)】的数据库操作Service实现
* @createDate 2025-02-12 14:28:01
*/
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer>
    implements CustomerService{

}




