package com.yatang.sc.common;

import java.util.HashMap;
import java.util.Map;

public class ProvinceProperty {

    public static Map<String, String> provinces = new HashMap<>();
    public static Map<String, String> citys = new HashMap<>();

    static {
        provinces.put("四川省","四川");
        provinces.put("重庆市","重庆");
        provinces.put("北京市","北京");
        provinces.put("上海市","上海");
        provinces.put("安徽省","安徽");
        provinces.put("山东省","山东");
        provinces.put("广东省","广东");
        provinces.put("湖北省","湖北");
        provinces.put("湖南省","湖南");
        provinces.put("浙江省","浙江");
        provinces.put("河北省","河北");
        provinces.put("河南省","河南");
        provinces.put("陕西省","陕西");
        provinces.put("西藏自治区","西藏");
        provinces.put("香港特别行政区","香港");

        citys.put("深圳市","深圳");
        citys.put("广州市","广州");
    }
}
