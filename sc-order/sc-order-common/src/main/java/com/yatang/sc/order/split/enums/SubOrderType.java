package com.yatang.sc.order.split.enums;

public enum SubOrderType {
    YT_SHIPPING(0, "统配订单"), PROVIDER_SHIPPING(1, "直配订单"), PROVIDER_SHIPPING_ASN(2, "直配订单已录入ASN分组"), LESS_STOCK(3, "库存不足"), ENOUGH_STOCK(4, "库存充足");
    private int val;
    private String desc;

    SubOrderType(int val, String desc) {
        this.val = val;
        this.desc = desc;
    }

    public int getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }
}
