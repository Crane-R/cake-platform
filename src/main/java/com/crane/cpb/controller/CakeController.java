package com.crane.cpb.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.cpb.model.domain.*;
import com.crane.cpb.model.domain.vo.CakeVo;
import com.crane.cpb.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 蛋糕接口
 *
 * @author Xanthos
 * @date 2025/3/2 12:39
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/cake")
@CrossOrigin
public class CakeController {

    private final CakeService cakeService;

    private final ShoppingCartService shoppingCartService;

    private final TagService tagService;

    private final UserService userService;
    private final CakeTagService cakeTagService;

    // 上传目录，会在项目根目录下的static/upload_img文件夹
    private static final String UPLOAD_DIR = "upload_img/";
    private final CakeImgService cakeImgService;
    private final MerchantService merchantService;

    /**
     * 跳转到网格列表
     *
     * @date 2025/3/12 19:58
     **/
    @GetMapping("/grid/{pageNum}/{type}")
    public ModelAndView shopGrid(@PathVariable Integer pageNum, HttpServletRequest request, @PathVariable String type) {
        if (pageNum < 1) {
            pageNum = 1;
        }
        LambdaQueryWrapper<Cake> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Cake::getCakeId);
        if (StrUtil.isNotEmpty(type)) {
            Long tagId = tagService.getOne(Wrappers.<Tag>lambdaQuery().eq(Tag::getName, type)).getTagId();
            List<Long> cakeIds = cakeTagService.list(Wrappers.<CakeTag>lambdaQuery().eq(CakeTag::getTagId, tagId))
                    .stream().map(CakeTag::getCakeId).toList();
            if (!cakeIds.isEmpty()) {
                queryWrapper.in(Cake::getCakeId, cakeIds);
            } else {
                queryWrapper.eq(Cake::getCakeId, -1);
            }
        }
        Page<Cake> page = cakeService.page(new Page<>(pageNum, 12), queryWrapper);
        Page<CakeVo> pageVo = new Page<>();
        BeanUtils.copyProperties(page, pageVo);
        pageVo.setRecords(page.getRecords().stream().map(cakeService::toVo).toList());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("page", pageVo);
        modelAndView.addObject("currentPageNum", pageNum);
        modelAndView.setViewName("shop_grid");
        //计算pageEnd
        double l = (double) pageVo.getTotal() / pageVo.getSize();
        modelAndView.addObject("pageEnd", Math.ceil(l));
        Object flag = request.getSession().getAttribute("addFail");
        if (flag != null) {
            modelAndView.addObject("addFail", flag);
            request.getSession().removeAttribute("addFail");
        }
        tagService.setTagsType(modelAndView);
        User user = userService.currentUser(request);
        if (user != null) {
            modelAndView.addObject("currentUser", user);
            //获取购物车数据
            shoppingCartService.setCartData(request, modelAndView);
        }
        return modelAndView;
    }

    @GetMapping("/grid/{pageNum}")
    public ModelAndView shopGrid1(@PathVariable Integer pageNum, HttpServletRequest request, @RequestParam(required = false) String search) {
        if (pageNum < 1) {
            pageNum = 1;
        }
        LambdaQueryWrapper<Cake> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Cake::getCakeId);
        if (StrUtil.isNotEmpty(search)) {
            queryWrapper.like(Cake::getName, search);
        }
        String type = request.getParameter("type");
        if (StrUtil.isNotEmpty(type)) {
            List<Long> cakeIds = cakeTagService.list(Wrappers.<CakeTag>lambdaQuery().eq(CakeTag::getTagId, type))
                    .stream().map(CakeTag::getCakeId).toList();
            queryWrapper.in(Cake::getCakeId, cakeIds);
        }
        Page<Cake> page = cakeService.page(new Page<>(pageNum, 12), queryWrapper);
        Page<CakeVo> pageVo = new Page<>();
        BeanUtils.copyProperties(page, pageVo);
        pageVo.setRecords(page.getRecords().stream().map(cakeService::toVo).toList());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("page", pageVo);
        modelAndView.addObject("currentPageNum", pageNum);
        modelAndView.setViewName("shop_grid");
        //计算pageEnd
        double l = (double) pageVo.getTotal() / pageVo.getSize();
        modelAndView.addObject("pageEnd", Math.ceil(l));
        Object flag = request.getSession().getAttribute("addFail");
        if (flag != null) {
            modelAndView.addObject("addFail", flag);
            request.getSession().removeAttribute("addFail");
        }
        tagService.setTagsType(modelAndView);
        User user = userService.currentUser(request);
        if (user != null) {
            modelAndView.addObject("currentUser", user);
            //获取购物车数据
            shoppingCartService.setCartData(request, modelAndView);
        }
        return modelAndView;
    }

    /**
     * 跳转至商品详情
     **/
    @GetMapping("/showOne/{cakeId}")
    public ModelAndView showOne(@PathVariable Long cakeId, HttpServletRequest request) {
        Cake byId = cakeService.getById(cakeId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("cake", cakeService.toVo(byId));
        modelAndView.setViewName("shop_details");
        shoppingCartService.setCartData(request, modelAndView);
        User user = userService.currentUser(request);
        if (user != null) {
            modelAndView.addObject("currentUser", user);
        } else {
            return new ModelAndView("register");
        }
        return modelAndView;
    }

    @PostMapping("/api/save")
    public Cake saveCake(@RequestBody Cake cake, HttpServletRequest request) {
        return cakeService.saveCake(cake, request);
    }

    @GetMapping("/page")
    public Page<Cake> page(@RequestParam int current,
                           @RequestParam int size,
                           @RequestParam(required = false) String cakeName, HttpServletRequest request) {
        LambdaQueryWrapper<Cake> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Cake::getCakeId);
        //如果是管理员，蛋糕都可以看，如果是商家，只能看自己的
        User user = userService.currentUser(request);
        if (user.getIdentity() == 1) {
            queryWrapper.eq(Cake::getMerchantId, merchantService.getById(user.getIdentityIndex()).getMerchantId());
        }
        if (StrUtil.isNotEmpty(cakeName)) {
            queryWrapper.like(Cake::getName, cakeName);
        }
        return cakeService.page(new Page<>(current, size), queryWrapper);
    }

    @GetMapping("/getById/{cakeId}")
    public Cake getById(@PathVariable Long cakeId) {
        Cake byId = cakeService.getById(cakeId);
        List<CakeTag> cakeTagList = cakeTagService.list(Wrappers.<CakeTag>lambdaQuery().eq(CakeTag::getCakeId, byId.getCakeId()));
        byId.setTagIds(cakeTagList.stream().map(CakeTag::getTagId).collect(Collectors.toList()));
        List<CakeImg> imgList = cakeImgService.list(Wrappers.<CakeImg>lambdaQuery().eq(CakeImg::getCakeId, byId.getCakeId()));
        imgList.forEach(cakeImg -> {
            cakeImg.setUrl("http://localhost:8089/" + UPLOAD_DIR + cakeImg.getAttachmentName());
            cakeImg.setName(cakeImg.getAttachmentName());
        });
        byId.setImgList(imgList);
        return byId;
    }

    @PostMapping("/api/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("cakeId") Long cakeId) {
        try {
            // 确保上传目录存在
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            // 生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID() + fileExtension;
            // 保存文件
            Path filePath = Paths.get(UPLOAD_DIR + uniqueFileName);
            Files.write(filePath, file.getBytes());
            CakeImg cakeImg = new CakeImg();
            cakeImg.setCakeId(cakeId);
            cakeImg.setAttachmentName(uniqueFileName);
            cakeImgService.save(cakeImg);
            // 返回文件的访问URL和cakeId
            String fileUrl = "/upload_img/" + uniqueFileName;
            return ResponseEntity.ok().body(new UploadResponse(fileUrl, cakeId));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("文件上传失败: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("文件上传失败: " + e.getMessage());
        }
    }

    // 扩展响应DTO
    @Data
    private static class UploadResponse {
        private String url;
        private Long cakeId;

        public UploadResponse(String url, Long cakeId) {
            this.url = url;
            this.cakeId = cakeId;
        }

    }

    @GetMapping("/api/delete/{cakeId}")
    public Boolean deleteCake(@PathVariable Long cakeId) {
        return cakeService.removeById(cakeId);
    }

}
