package com.yatang.sc.common.staticvalue;

/**
 * @描述: 商品地点关系相关枚举
 * @作者: tankejia
 * @创建时间: 2018/1/29-14:34 .
 * @版本: 1.0 .
 */
public enum ProdAdrEnum {

    /**
     * 物流模式
     * */
    directSending(0, "直送"),
    dispatching(1, "配送");

    private Integer	code;
    private String	name;

    ProdAdrEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
