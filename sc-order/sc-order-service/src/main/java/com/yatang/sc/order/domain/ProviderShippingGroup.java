package com.yatang.sc.order.domain;

import java.io.Serializable;

public class ProviderShippingGroup implements Serializable {
    private Long id;

    private String shippingGroupId;

    private String spId;                                        // 供应商ID

    private String spNo;                                        // 供应商编码

    private String spName;                                        // 供应商名称

    private String spAdrId;                                    // 供应商地点ID

    private String spAdrNo;                                    // 供应商地点编码

    private String spAdrName;                                    // 供应商地点名称

    public Long getId() {
        return id;
    }

    public void setId(Long pId) {
        id = pId;
    }

    public String getShippingGroupId() {
        return shippingGroupId;
    }

    public void setShippingGroupId(String pShippingGroupId) {
        shippingGroupId = pShippingGroupId;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String pSpId) {
        spId = pSpId;
    }

    public String getSpNo() {
        return spNo;
    }

    public void setSpNo(String pSpNo) {
        spNo = pSpNo;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String pSpName) {
        spName = pSpName;
    }

    public String getSpAdrId() {
        return spAdrId;
    }

    public void setSpAdrId(String pSpAdrId) {
        spAdrId = pSpAdrId;
    }

    public String getSpAdrNo() {
        return spAdrNo;
    }

    public void setSpAdrNo(String pSpAdrNo) {
        spAdrNo = pSpAdrNo;
    }

    public String getSpAdrName() {
        return spAdrName;
    }

    public void setSpAdrName(String pSpAdrName) {
        spAdrName = pSpAdrName;
    }
}
