package com.yatang.sc.facade.domain.pm;

import com.yatang.sc.facade.common.BasePo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述: 商品采购item查询Po
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/26 14:55
 * @版本: v1.0
 */
public class PmPurchaseOrderItemQueryParamPo extends BasePo implements Serializable {


    private static final long serialVersionUID = -688976540816644535L;
    private String productId;//商品id

    private String spAdrId;//供应商地点id

    private String spId;//供应商id

    private String purchaseOrderNo;//采购单单号

    private String brandId;//品牌iD

    private String name;//品牌名称

    private String productCode;//商品编码

    private String productName;//商品名称

    private Integer isSuccess;  //是否成功收货 0：否  1：是

    private String spAdrNo;//供应商地点no

    private String logicWareHouseCode;//退货地点编码


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }



    public String getProductId() {
        return productId;
    }



    public void setProductId(String productId) {
        this.productId = productId;
    }



    public String getSpAdrId() {
        return spAdrId;
    }



    public void setSpAdrId(String spAdrId) {
        this.spAdrId = spAdrId;
    }



    public String getSpId() {
        return spId;
    }



    public void setSpId(String spId) {
        this.spId = spId;
    }



    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }



    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }



    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public String getProductName() {
        return productName;
    }



    public void setProductName(String productName) {
        this.productName = productName;
    }



    public String getBrandId() {
        return brandId;
    }



    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }



    public String getProductCode() {
        return productCode;
    }



    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }



    public Integer getIsSuccess() {
        return isSuccess;
    }



    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }



    public String getSpAdrNo() {
        return spAdrNo;
    }



    public void setSpAdrNo(String spAdrNo) {
        this.spAdrNo = spAdrNo;
    }



    public String getLogicWareHouseCode() {
        return logicWareHouseCode;
    }



    public void setLogicWareHouseCode(String logicWareHouseCode) {
        this.logicWareHouseCode = logicWareHouseCode;
    }


}
