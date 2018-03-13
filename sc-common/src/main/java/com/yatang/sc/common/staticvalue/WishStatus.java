package com.yatang.sc.common.staticvalue;

/**
 * 心愿单状态
 */
public enum  WishStatus {

    init("init", "待处理"),
    complete("complete", "已完成"),
    close("close", "关闭"),
    ;
    private String	code;
    private String	name;
    WishStatus(String code,String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
