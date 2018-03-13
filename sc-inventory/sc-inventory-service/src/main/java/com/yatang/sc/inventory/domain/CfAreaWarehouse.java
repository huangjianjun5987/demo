package com.yatang.sc.inventory.domain;

import java.io.Serializable;

public class CfAreaWarehouse implements Serializable {
    private static final long serialVersionUID = -5941322663009871091L;
    private Long id;

    private String province;

    private String warehouseCode;

    private String branchCompanyId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId == null ? null : branchCompanyId.trim();
    }
}