package com.yatang.sc.common;

/**
 * Created by qiugang on 7/25/2017.
 */
public enum OrderTypeEnum {

    FranchiseStore("ZCXS"),
    DirectSaleStore("ZYYH"),
    ElectronicOrder("XNSP"),
    ;
    private String orderType;

    OrderTypeEnum(String orderType){
        this.orderType = orderType;
    }

    public String getOrderType(){
        return this.orderType;
    }


}
