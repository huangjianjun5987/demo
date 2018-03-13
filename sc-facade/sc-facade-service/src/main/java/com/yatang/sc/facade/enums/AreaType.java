package com.yatang.sc.facade.enums;

/**
 * @描述:
 * @作者: tangqi
 * @创建时间: 2017/11/21
 */
public enum AreaType{

    BANNER(1,"横幅广告"),
    FLOOR(2,"楼层"),
    CAROUSEL(3,"轮播"),
    QUICK_NAV(4,"快捷导航"),
    HOT(5,"热搜");

    private int value;
    private String desc;

    private AreaType(int value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public int getValue(){
        return value;
    }
    public String getDesc(){
        return desc;
    }
}
