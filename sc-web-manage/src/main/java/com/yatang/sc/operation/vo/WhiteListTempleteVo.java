package com.yatang.sc.operation.vo;

import com.yatang.sc.common.utils.excel.ExcelFieldName;
import com.yatang.sc.common.utils.excel.ExcelName;

import java.io.Serializable;

@ExcelName(name="baimingdandaoru")
public class WhiteListTempleteVo implements Serializable{

    @ExcelFieldName(name = "门店编号")
    private String storeId;

    @ExcelFieldName(name="上线状态(1:上线,0:下线)")
    private Integer scPurchaseFlag;


    @ExcelFieldName(name="仓库编码")
    private String deliveryWarehouseCode;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getScPurchaseFlag() {
        return scPurchaseFlag;
    }

    public void setScPurchaseFlag(Integer scPurchaseFlag) {
        this.scPurchaseFlag = scPurchaseFlag;
    }

    public String getDeliveryWarehouseCode() {
        return deliveryWarehouseCode;
    }

    public void setDeliveryWarehouseCode(String deliveryWarehouseCode) {
        this.deliveryWarehouseCode = deliveryWarehouseCode;
    }
}
