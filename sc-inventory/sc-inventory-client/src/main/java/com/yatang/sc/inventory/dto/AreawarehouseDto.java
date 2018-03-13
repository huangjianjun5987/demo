package com.yatang.sc.inventory.dto;

import java.io.Serializable;

public class AreawarehouseDto implements Serializable{
    private static final long serialVersionUID = -1282889408801835561L;

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
        this.province = province;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }
}
