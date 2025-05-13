package com.crane.cpb.controller;

import cn.hutool.core.util.RandomUtil;
import com.crane.cpb.model.domain.Cake;
import com.crane.cpb.model.domain.CakeImg;
import com.crane.cpb.model.domain.CakeTag;
import com.crane.cpb.model.domain.Tag;
import com.crane.cpb.service.CakeImgService;
import com.crane.cpb.service.CakeService;
import com.crane.cpb.service.CakeTagService;
import com.crane.cpb.service.TagService;
import com.crane.cpb.util.CakeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 启动测试
 *
 * @Date 2024/10/5 14:59
 * @Author Crane Resigned
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final CakeService cakeService;

    private final CakeImgService cakeImgService;

    private final TagService tagService;

    private final CakeTagService cakeTagService;

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

    @GetMapping("/testIndex")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("title", "蛋糕管理系统");
        return modelAndView;
    }

    /**
     * 批量生成蛋糕数据
     * 会为其添加一张图片
     *
     * @date 2025/3/2 12:22
     **/
    @GetMapping("/getCake/{num}")
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public String generateCakeData(@PathVariable Integer num) {
        if (num == null) {
            throw new RuntimeException("num不可为空");
        }
        List<Tag> list = tagService.list();
        int count = 0;
        for (int i = 0; i < num; i++) {
            Cake cake = new Cake();
            cake.setName(CakeGenerator.generateCakeName());
            cake.setDescription(CakeGenerator.generateCakeDescription());
            cake.setPrice(BigDecimal.valueOf(CakeGenerator.generateCakePrice()));
            cake.setMerchantId(-1L);
            cakeService.save(cake);
            //添加蛋糕图片
            int imgCount = 4;
            for (int j = 0; j < imgCount; j++) {
                String cakeImgName = getCakeImgName();
                CakeImg cakeImg = new CakeImg();
                cakeImg.setAttachmentName(cakeImgName);
                cakeImg.setCakeId(cake.getCakeId());
                cakeImgService.save(cakeImg);
            }
            //添加蛋糕标签关联数据
            int tagCount = RandomUtil.randomInt(0, 4);
            for (int j = 0; j < tagCount; j++) {
                CakeTag cakeTag = new CakeTag();
                cakeTag.setCakeId(cake.getCakeId());
                cakeTag.setTagId(list.get(RandomUtil.randomInt(0, list.size())).getTagId());
                cakeTagService.save(cakeTag);
            }
            count++;
        }
        return "成功添加数：" + count;
    }

    /**
     * 随机获得一张图片名称
     *
     * @date 2025/3/2 12:29
     **/
    private String getCakeImgName() {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            // 匹配 upload_img 目录下的所有图片文件
            Resource[] resources = resolver.getResources(
                    "file:D:/Projects/graduation_design/cake-platform/upload_img/*.{jpg,png,gif,jpeg,bmp,webp}");

            if (resources.length == 0) {
                throw new RuntimeException("No images found in the specified directory");
            }

            // 随机返回一个文件名
            return resources[(int) (Math.random() * resources.length)].getFilename();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load images", e);
        }
    }

    /**
     * 批量生成标签
     **/
    @GetMapping("/getTag/{num}")
    public String getTag(@PathVariable Integer num) {
        if (num == null) {
            throw new RuntimeException("num不可为空");
        }
        int count = 0;
        for (int i = 0; i < num; i++) {
            Tag tag = new Tag();
            tag.setIsType(RandomUtil.randomInt(0, 2));
            tag.setName(RandomUtil.randomString(6));
            tagService.save(tag);
            count++;
        }
        return "成功添加数：" + count;
    }

}