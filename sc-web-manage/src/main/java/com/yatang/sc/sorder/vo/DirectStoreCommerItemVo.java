package com.yatang.sc.sorder.vo;

import com.yatang.xc.mbd.pi.es.dto.InternationalCodeDto;

import java.io.Serializable;
import java.util.List;

public class DirectStoreCommerItemVo implements Serializable {

    private static final long serialVersionUID = -1283763967013987687L;

    /**
     * 商品编码
     */
    private String                          productCode;

    /**
     * 商品条码/国际码
     */
    private List<InternationalCodeDto>      internationalCodes;

    /**
     * 商品id
     */
    private String                          productId;

    /**
     * 商品名称
     */
    private String productName;

    // 一级商品分类名称
    private String							firstLevelCategoryName;

    // 二级商品分类名称
    private String							secondLevelCategoryName;

    // 三级商品分类名称
    private String							thirdLevelCategoryName;

    // 标准包装单位参考值，例如：箱*盒*个
    private String							unitExplanation;

    // 标准包装规格参考值，例如：1*2*6'
    private String							packingSpecifications;

    // 是否整箱销售：0-否；1-是
    private Integer							sellFullCase;

    // 整箱单位
    private String							fullCaseUnit;

    //最小销售单位
    private String                          minUnit;

    //起订量
    private Integer                         minNumber;

    //单价
    private Double                          salePrice;

    //最小销售单位转换成的数量
    private long                            quantity;

    //销售数量
    private long                            saleQuantity;

    //每个商品的总价
    private Double                          amount;

    // 销售内装数
    private Integer						    salesInsideNumber;

    //库存是否充足
    private boolean                         enough =true;

    // 是否支持改区域销售
    private boolean                         available= true;

    // 导入失败数据
    private UploadFailedVo                  uploadFailedVos;

    //是否是内装数的整数倍
    private boolean                         integerTimes = true;

    // 错误信息
    private String                          message;

    /** 商品优惠券ID*/
    private String couponId;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public List<InternationalCodeDto> getInternationalCodes() {
        return internationalCodes;
    }

    public void setInternationalCodes(List<InternationalCodeDto> internationalCodes) {
        this.internationalCodes = internationalCodes;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFirstLevelCategoryName() {
        return firstLevelCategoryName;
    }

    public void setFirstLevelCategoryName(String firstLevelCategoryName) {
        this.firstLevelCategoryName = firstLevelCategoryName;
    }

    public String getSecondLevelCategoryName() {
        return secondLevelCategoryName;
    }

    public void setSecondLevelCategoryName(String secondLevelCategoryName) {
        this.secondLevelCategoryName = secondLevelCategoryName;
    }

    public String getThirdLevelCategoryName() {
        return thirdLevelCategoryName;
    }

    public void setThirdLevelCategoryName(String thirdLevelCategoryName) {
        this.thirdLevelCategoryName = thirdLevelCategoryName;
    }

    public String getUnitExplanation() {
        return unitExplanation;
    }

    public void setUnitExplanation(String unitExplanation) {
        this.unitExplanation = unitExplanation;
    }

    public String getPackingSpecifications() {
        return packingSpecifications;
    }

    public void setPackingSpecifications(String packingSpecifications) {
        this.packingSpecifications = packingSpecifications;
    }

    public Integer getSellFullCase() {
        return sellFullCase;
    }

    public void setSellFullCase(Integer sellFullCase) {
        this.sellFullCase = sellFullCase;
    }

    public String getFullCaseUnit() {
        return fullCaseUnit;
    }

    public void setFullCaseUnit(String fullCaseUnit) {
        this.fullCaseUnit = fullCaseUnit;
    }

    public Integer getMinNumber() {
        if (this.minNumber == null){
            minNumber = 1;
        }
        return minNumber;
    }

    public void setMinNumber(Integer minNumber) {
        this.minNumber = minNumber;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getSalesInsideNumber() {
        if (this.salesInsideNumber == null){
            salesInsideNumber = 1;
        }
        return salesInsideNumber;
    }

    public void setSalesInsideNumber(Integer salesInsideNumber) {
        this.salesInsideNumber = salesInsideNumber;
    }

    public boolean isEnough() {
        return enough;
    }

    public void setEnough(boolean enough) {
        this.enough = enough;
    }

    public long getSaleQuantity() {
        return saleQuantity;
    }

    public void setSaleQuantity(long saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    public UploadFailedVo getUploadFailedVos() {
        return uploadFailedVos;
    }

    public void setUploadFailedVos(UploadFailedVo uploadFailedVos) {
        this.uploadFailedVos = uploadFailedVos;
    }

        public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMinUnit() {
        return minUnit;
    }

    public void setMinUnit(String minUnit) {
        this.minUnit = minUnit;
    }

    public boolean isIntegerTimes() {
        return integerTimes;
    }

    public void setIntegerTimes(boolean integerTimes) {
        this.integerTimes = integerTimes;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String pCouponId) {
        couponId = pCouponId;
    }
}
