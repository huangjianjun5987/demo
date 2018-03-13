package com.yatang.sc.inventory.dto;

import java.io.Serializable;

public class DirectValidateInventoryDto implements Serializable {

    private static final long serialVersionUID = -3374299909447973282L;

    private String loc;                     //仓库编码

    private String branchCompanyId;    //分公司id

    private String productId;   //商品id


    private long itemQty;    //订单确认数量


    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getItemQty() {
        return itemQty;
    }

    public void setItemQty(long itemQty) {
        this.itemQty = itemQty;
    }
}
