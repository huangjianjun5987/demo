package com.yatang.sc.order.split.enums;

public enum SplitOrderFromType {
    SPLIT_FROM_INVENTORY(1, "实时库存拆单"), SPLIT_FROM_MANUAL(2, "手动拆单"), SPLIT_FROM_SHIPPING_MODELS(3, "配送方式拆单"),
    STORAGE_MODE(4, "储藏方式拆单"), GIFT_PRODUCT_SPLIT(5, "赠品拆单"), SALE_MODE_SPLIT(6, "销售模式拆单"),ASN_NOTICE_SPLIT(7, "直供ASN录入拆单");
    private int val;
    private String desc;

    SplitOrderFromType(int val, String desc) {
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
