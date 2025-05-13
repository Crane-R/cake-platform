package com.crane.cpb.util;

import java.util.Random;

/**
 * 蛋糕信息生成工具类
 * @author Xanthos
 */
public class CakeGenerator {
    private static final Random random = new Random();

    // 蛋糕名称组成部分
    private static final String[] CAKE_TYPES = {
            "巧克力", "草莓", "香草", "抹茶", "红丝绒", "黑森林", "芝士", "提拉米苏",
            "芒果", "蓝莓", "柠檬", "咖啡", "焦糖", "椰子", "榴莲", "覆盆子",
            "伯爵茶", "栗子", "南瓜", "紫薯", "芝麻", "花生", "杏仁", "开心果",
            "百香果", "蜜桃", "樱桃", "香蕉", "菠萝", "石榴", "柚子", "西柚"
    };

    private static final String[] CAKE_STYLES = {
            "奶油蛋糕", "慕斯蛋糕", "戚风蛋糕", "千层蛋糕", "芝士蛋糕", "冰淇淋蛋糕",
            "杯子蛋糕", "磅蛋糕", "天使蛋糕", "瑞士卷", "马卡龙蛋糕", "拿破仑蛋糕",
            "熔岩蛋糕", "舒芙蕾", "布丁蛋糕", "泡芙塔", "闪电泡芙", "甜甜圈",
            "华夫饼蛋糕", "松饼蛋糕", "可丽饼蛋糕", "羊角面包蛋糕", "法式蛋糕", "日式和风蛋糕"
    };

    private static final String[] DECORATIONS = {
            "水果装饰", "巧克力碎片", "糖霜装饰", "鲜花装饰", "金箔点缀", "坚果 topping",
            "奶油玫瑰花", "马卡龙装饰", "卡通造型", "节日主题", "简约风格", "艺术画风",
            "星空淋面", "镜面装饰", "糖艺造型", "翻糖人偶", "巧克力雕塑", "水果切片",
            "奶油波浪", "糖珠点缀", "巧克力卷", "蛋白糖装饰", "糖网装饰", "巧克力刨花"
    };

    // 蛋糕描述模板
    private static final String[] DESCRIPTIONS = {
            "一款精致的%s，采用优质%s制作，表面%s，口感%s，适合%s场合。",
            "这款%s选用上等%s原料，搭配%s，味道%s，是%s的理想选择。",
            "我们的%s以%s为基础，精心%s，呈现出%s的风味，特别适合%s。",
            "经典%s配方，加入%s元素，通过%s工艺制作，带来%s体验，完美搭配%s。",
            "创新%s设计，融合%s风味，搭配%s装饰，带来%s的味觉享受，推荐用于%s。",
            "这款%s采用传统%s工艺，结合现代%s技术，打造%s口感，最适合%s时享用。",
            "来自大师之手的%s，精选%s原料，配以%s装饰，呈现%s层次感，专为%s设计。",
            "充满创意的%s，将%s与%s完美结合，带来%s的惊喜，特别适合%s场景。"
    };

    private static final String[] TEXTURES = {
            "绵密", "松软", "细腻", "浓郁", "丝滑", "酥脆", "Q弹", "湿润",
            "轻盈", "蓬松", "扎实", "松脆", "绵软", "入口即化", "层次分明", "外酥内软"
    };

    private static final String[] OCCASIONS = {
            "生日派对", "婚礼庆典", "周年纪念", "朋友聚会", "下午茶", "节日庆祝",
            "商务宴请", "家庭聚餐", "情人节", "儿童派对", "毕业典礼", "公司年会",
            "乔迁之喜", "宝宝满月", "圣诞派对", "新年聚会", "感恩节", "母亲节",
            "父亲节", "七夕节", "中秋佳节", "端午聚会", "万圣节", "复活节"
    };

    private static final String[] SPECIAL_INGREDIENTS = {
            "法国进口奶油", "比利时巧克力", "意大利马斯卡彭奶酪", "日本宇治抹茶",
            "马达加斯加香草", "土耳其榛子", "美国加州核桃", "澳大利亚芒果",
            "泰国金枕头榴莲", "智利蓝莓", "新西兰奇异果", "南非葡萄柚",
            "西班牙橄榄油", "瑞士牛奶", "德国黑森林樱桃", "法国盐之花"
    };

    private static final String[] BAKING_TECHNIQUES = {
            "低温慢烤", "水浴烘焙", "蒸汽烘焙", "传统窑烤",
            "法式工艺", "日式技法", "分子料理技术", "低温慢煮",
            "手工揉制", "天然发酵", "精准控温", "多层烘烤"
    };

    /**
     * 生成随机蛋糕名称
     * @return 蛋糕名称
     */
    public static String generateCakeName() {
        String type = CAKE_TYPES[random.nextInt(CAKE_TYPES.length)];
        String style = CAKE_STYLES[random.nextInt(CAKE_STYLES.length)];
        String decoration = DECORATIONS[random.nextInt(DECORATIONS.length)];

        // 随机决定是否包含装饰部分
        if (random.nextBoolean()) {
            return type + style + "（" + decoration + "）";
        } else {
            return type + style;
        }
    }

    /**
     * 生成随机蛋糕描述
     * @return 蛋糕描述
     */
    public static String generateCakeDescription() {
        String name = generateCakeName();
        String type = CAKE_TYPES[random.nextInt(CAKE_TYPES.length)];
        String ingredient = SPECIAL_INGREDIENTS[random.nextInt(SPECIAL_INGREDIENTS.length)];
        String decoration = DECORATIONS[random.nextInt(DECORATIONS.length)];
        String technique = BAKING_TECHNIQUES[random.nextInt(BAKING_TECHNIQUES.length)];
        String texture = TEXTURES[random.nextInt(TEXTURES.length)];
        String occasion = OCCASIONS[random.nextInt(OCCASIONS.length)];

        String template = DESCRIPTIONS[random.nextInt(DESCRIPTIONS.length)];
        return String.format(template, name, type, ingredient, decoration,
                technique, texture, occasion);
    }

    /**
     * 生成随机蛋糕价格
     * @return 蛋糕价格（元）
     */
    public static double generateCakePrice() {
        // 基础价格在50-500元之间
        double basePrice = 50 + random.nextInt(451);

        // 添加小数部分（常见价格尾数）
        double[] decimals = {0.0, 0.5, 0.8, 0.9, 0.99, 0.88, 0.66};
        basePrice += decimals[random.nextInt(decimals.length)];

        // 30%几率价格更高（高端蛋糕）
        if (random.nextDouble() < 0.3) {
            basePrice *= 1.2 + random.nextDouble() * 0.8;
        }

        // 10%几率是特价蛋糕
        if (random.nextDouble() < 0.1) {
            basePrice *= 0.7 + random.nextDouble() * 0.2;
        }

        // 保留两位小数
        return Math.round(basePrice * 100) / 100.0;
    }

    /**
     * 生成完整的蛋糕信息
     * @return 包含名称、描述和价格的字符串
     */
    public static String generateFullCakeInfo() {
        String name = generateCakeName();
        String desc = generateCakeDescription();
        double price = generateCakePrice();

        return String.format("名称: %s\n描述: %s\n价格: ￥%.2f", name, desc, price);
    }
}