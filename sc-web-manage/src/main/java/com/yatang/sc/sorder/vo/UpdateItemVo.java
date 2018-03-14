package com.yatang.sc.sorder.vo;

import java.io.Serializable;

public class UpdateItemVo implements Serializable {

    private static final long serialVersionUID = -9116062266606635711L;

    private String productCode;           //商品编号

    private long quantity;              //商品数量

    private String branchCompanyId;     //分公司id

    private String  deliveryWarehouseCode;  //出货仓

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getDeliveryWarehouseCode() {
        return deliveryWarehouseCode;
    }

    public void setDeliveryWarehouseCode(String deliveryWarehouseCode) {
        this.deliveryWarehouseCode = deliveryWarehouseCode;
    }
}
