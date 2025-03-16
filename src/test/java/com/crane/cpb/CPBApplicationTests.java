package com.crane.cpb;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crane.cpb.mapper.ShoppingCartMapper;
import com.crane.cpb.model.domain.ShoppingCart;
import com.crane.cpb.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class CPBApplicationTests {

    @Test
    void contextLoads() throws IOException {
        // 创建一个PathMatchingResourcePatternResolver实例，用于解析资源模式
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 根据指定的路径模式获取资源数组
        Resource[] resources = resolver.getResources("classpath:static/cake_imgs/*");
        String[] fileNames = new String[resources.length];
        for (int i = 0; i < resources.length; i++) {
            // 获取每个资源的文件名
            fileNames[i] = resources[i].getFilename();
            System.out.println(fileNames[i]);
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Test
    void aVoid(){
        QueryWrapper<ShoppingCart> wrapper = new QueryWrapper<>();
        wrapper.select("user_id","cake_id","count(*) as num");
        wrapper.eq("user_id","2");
        wrapper.groupBy("cake_id");
        List<ShoppingCart> items = shoppingCartMapper.selectList(wrapper);
        System.out.println(items);
    }

}
