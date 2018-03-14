package com.yatang.sc.order.provider.order;

public enum ProviderOrderStatus {
    ACCEPT("accept", "供应商接单"), ASN("asn", "录入ASN"), PROVIDER_SINGED_REQ("singed_req", "供应商签收请求");
    private String val;
    private String desc;

    ProviderOrderStatus(String pVal, String pDesc) {
        val = pVal;
        desc = pDesc;
    }

    public String getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }
}
