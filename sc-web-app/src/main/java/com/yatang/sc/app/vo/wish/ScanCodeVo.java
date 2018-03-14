package com.yatang.sc.app.vo.wish;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ScanCodeVo implements Serializable{
    private static final long serialVersionUID = -3187484630484353910L;

    @NotNull(message = "{msg.notEmpty.message}")
    private String barCode;
    private String branchCompanyId;
    private String wareHouseCode;

    public String getWareHouseCode() {
        return wareHouseCode;
    }

    public void setWareHouseCode(String wareHouseCode) {
        this.wareHouseCode = wareHouseCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }
}
