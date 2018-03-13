package com.yatang.sc.facade.enums;

/**
 * @描述: 公共枚举类定义.
 * @作者: tankejia
 * @创建时间: 2017年7月4日-下午20:05:00 .
 * @版本: 1.0 .
 */

public enum CommonEnum {

    /**************************定义供应商状态枚举--start***********************************/
    /**
     * 草稿
     * */
    SUPPLIER_INFO_ZERO(0, "制单"),
    /**
     * 已提交
     * */
    SUPPLIER_INFO_ONE(1, "已提交"),
    /**
     * 已审核
     * */
    SUPPLIER_INFO_TWO(2, "已审核"),
    /**
     * 已拒绝
     * */
    SUPPLIER_INFO_THREE(3, "已拒绝"),
    /**
     * 修改中
     * */
    SUPPLIER_INFO_FOUR(4, "修改中"),

    /**************************定义供应商状态枚举--end***********************************/

    /**************************定义供应商子表状态枚举--start***********************************/
    SUPPLIER_SUBLIST_ZERO(0, "原记录"),
    SUPPLIER_SUBLIST_ONE(1, "修改记录"),
    /**************************定义供应商子表状态枚举--end***********************************/

    /**************************供应商类型枚举--start***********************************/
    /**供应商*/
    SP(1, "SP"),
    /**供应商地点*/
    SP_ADR(2,"SP_ADR"),
    /**************************供应商类型枚举--end***********************************/

    /**************************定义供应商新增或修改时提交类型枚举--start***********************************/
    SUPPLIER_SUBMIT_ZERO(0, "保存制单"),
    SUPPLIER_SUBMIT_ONE(1, "提交"),
    /**************************定义供应商新增修改时提交类型枚举--end***********************************/
    
    /**************************供应商类型枚举--start***********************************/
    /**供应商编码初始值*/
    SP_CONF_VAL(1, "100001"),
    /**供应商地点编码初始值*/
    SP_ADR_CONF_VAL(2,"1000001"),
    /**************************供应商类型枚举--end***********************************/

    /**************************订单状态枚举--start***********************************/
    /**商品采购-制单状态*/
    PM_PRUCHASE_ORDER_STATUS_DRAFT(0, "制单状态"),
    PM_PRUCHASE_ORDER_STATUS_SUBMIT(1, "提交状态");
//    /**供应商地点编码初始值*/
//    SP_ADR_CONF_VAL(2,"1000001");
    /**************************订单状态枚举--end***********************************/
    CommonEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;
    private String name;

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
