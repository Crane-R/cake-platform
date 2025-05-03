package com.crane.cpb.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crane.cpb.mapper.OrderItemMapper;
import com.crane.cpb.mapper.OrderMapper;
import com.crane.cpb.model.domain.Cake;
import com.crane.cpb.model.domain.User;
import com.crane.cpb.service.CakeService;
import com.crane.cpb.service.OrderService;
import com.crane.cpb.service.TagService;
import com.crane.cpb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 统计分析接口
 *
 * @author Xanthos
 * @date 2025/3/22 18:04
 */
@RequiredArgsConstructor
@RequestMapping("/analysis")
@RestController
@CrossOrigin
public class AnalysisController {

    private final UserService userService;

    private final CakeService cakeService;

    private final TagService tagService;

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    @GetMapping("/analysis")
    public Map<String, Object> getAnalysis() {
        Map<String, Object> resultMap = new HashMap<>();
        //统计数量
        long customerCount = userService.count(new QueryWrapper<User>().eq("identity", 0));
        long merchantCount = userService.count(new QueryWrapper<User>().eq("identity", 1));
        long cakeCount = cakeService.count();
        long tagCount = tagService.count();
        resultMap.put("customerCount", customerCount);
        resultMap.put("merchantCount", merchantCount);
        resultMap.put("cakeCount", cakeCount);
        resultMap.put("tagCount", tagCount);
        //获取订单
        List<Map<String, Object>> orderAnalysis = orderMapper.selectAnalysis();
        List<Map<String, String>> orderList = new ArrayList<>();
        if (orderAnalysis.size() < 7) {
            for (int i = 0; i < 7; i++) {
                DateTime dateTime = DateUtil.offsetDay(new Date(), -i);
                String dayStr = DateUtil.format(dateTime, "yyyy-MM-dd");
                Map<String, Object> stringObjectMap = orderAnalysis.stream()
                        .filter(e ->
                                StrUtil.equals(DateUtil.format((Date) e.get("order_date"), "yyyy-MM-dd"), dayStr))
                        .findAny().orElse(null);
                Map<String, String> map = new HashMap<>();
                map.put("order_date", dayStr);
                map.put("count", stringObjectMap == null ? "0" : stringObjectMap.get("count").toString());
                orderList.add(map);
            }
        }
        resultMap.put("orderAnalysis", orderList);

        BigDecimal cakeSum = orderItemMapper.cakeSum();
        List<Map<String, Object>> select5Cake = orderItemMapper.select5Cake();
        List<Map<String, Object>> cakeResult = new ArrayList<>();
        for (Map<String, Object> map : select5Cake) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("cake_name", cakeService.getOne(new QueryWrapper<Cake>().eq("cake_id", map.get("cake_id"))).getName());
            temp.put("sum", map.get("sum"));
            BigDecimal sum = new BigDecimal(map.get("sum").toString());
            temp.put("percent", sum.divide(cakeSum, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
            cakeResult.add(temp);
        }
        resultMap.put("cakeResult", cakeResult);

        return resultMap;
    }

}
