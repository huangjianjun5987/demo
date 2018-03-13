package com.yatang.sc.glink.dto.product;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 商品明细DTO
 * @作者: yijiang
 * @创建时间: 2017年7月31日20:50:50
 */
public class CommodityItemDto implements Serializable{

    private String itemCode;//货主商品编码，sku编码

    private String itemCodeWms;//WMS商品编码，sku编码

    private String itemName;//商品名称

    private String ownerCode;//货主编码，所属货主系统编号

    private String warehouseCode;//所属仓储商编号，通常对应wms的仓库编号

    private String projectCode;//所属项目，存在link

    private String barcode;//条形码，可多个，用分号（;）隔开

    private String itemType;//商品类型(ZC=正常商品,FX=分销商品,ZH=组合商品,ZP=赠品,BC=包材,HC=耗材,FL=辅料,XN=虚拟品,FS=附属品,CC=残次品,OTHER=其它)

    private String supplierCode;//供应商编码

    private String supplierName;//供应商名称

    private int safetyStock;//安全库存

    private String skuProperty;//商品属性

    private String color;//颜色

    private String size;//尺寸

    private double length;//长

    private double height;//高

    private double width;//宽

    private double netWeight;//净重

    private double grossWeight;//毛重

    private double volume;//体积

    private String pcs;//箱规

    private String stockUnit;//商品计量单位

    private String remark;//备注

    private String pricingCategory;//计价种类

    private double purchasePrice;//采购价

    private double costPrice;//成本价

    private double tagPrice;//吊牌价

    private double retailPrice;//零售价

    private String packCode;//包装代码

    private String seasonCode;//季节编码

    private String seasonName;//季节名称

    private String approvalNumber;//批准文号

    private String brandCode;//品牌代码

    private String brandName;//品牌名称

    private String title;//渠道中的商品标题

    private String packageMaterial;//商品包装材料类型

    private String originAddress;//商品的原产地

    private String shortName;//商品简称

    private String categoryId;//商品类别ID

    private String categoryName;//商品类别名称

    private String isSku;//是否sku,Y/N默认为Y

    private String isHazardous;//是否危险品Y/N默认为N

    private String isShelfLifeMgmt;//是否需要保质期管理Y/N默认为N

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date productDate;//生产日期YYYY-MM-DD HH:mm:ss

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expireDate;//过期日期yyyy-MM-dd HH:mm:ss

    private int shelfLife;//保质期(小时)

    private int rejectLifecycle;//保质期禁收天数（天）

    private int lockupLifecycle;//保质期禁售天数

    private int adventLifecycle;//保质期临期预警天数

    private String isSNMgmt;//是否需要串号管理,Y/N默认为N

    private String isBatchMgmt;//是否需要批次管理,Y/N默认为N

    private String batchRemark;//批次备注

    private String batchCode;//批次代码

    private String isFragile;//是否易碎品,Y/N默认为N

    private String isValid;//是否有效Y/N默认为Y

    private String picUrl;//图片URL

    private String englishName;//英文名

    private String string1;//预留字段1

    private String string2;//预留字段2

    private String string3;//预留字段3

    private String string4;//预留字段4

    private String actionType;//操作类型 1新建 2更新

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTimeERP;//更新时间yyyy-MM-dd HH:mm:ss

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeERP;//创建时间yyyy-MM-dd HH:mm:ss

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemCodeWms() {
        return itemCodeWms;
    }

    public void setItemCodeWms(String itemCodeWms) {
        this.itemCodeWms = itemCodeWms;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(int safetyStock) {
        this.safetyStock = safetyStock;
    }

    public String getSkuProperty() {
        return skuProperty;
    }

    public void setSkuProperty(String skuProperty) {
        this.skuProperty = skuProperty;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getPcs() {
        return pcs;
    }

    public void setPcs(String pcs) {
        this.pcs = pcs;
    }

    public String getStockUnit() {
        return stockUnit;
    }

    public void setStockUnit(String stockUnit) {
        this.stockUnit = stockUnit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPricingCategory() {
        return pricingCategory;
    }

    public void setPricingCategory(String pricingCategory) {
        this.pricingCategory = pricingCategory;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getTagPrice() {
        return tagPrice;
    }

    public void setTagPrice(double tagPrice) {
        this.tagPrice = tagPrice;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    public String getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public String getApprovalNumber() {
        return approvalNumber;
    }

    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPackageMaterial() {
        return packageMaterial;
    }

    public void setPackageMaterial(String packageMaterial) {
        this.packageMaterial = packageMaterial;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIsSku() {
        return isSku;
    }

    public void setIsSku(String isSku) {
        this.isSku = isSku;
    }

    public String getIsHazardous() {
        return isHazardous;
    }

    public void setIsHazardous(String isHazardous) {
        this.isHazardous = isHazardous;
    }

    public String getIsShelfLifeMgmt() {
        return isShelfLifeMgmt;
    }

    public void setIsShelfLifeMgmt(String isShelfLifeMgmt) {
        this.isShelfLifeMgmt = isShelfLifeMgmt;
    }

    public Date getProductDate() { return productDate; }

    public void setProductDate(Date productDate) { this.productDate = productDate; }

    public Date getExpireDate() { return expireDate; }

    public void setExpireDate(Date expireDate) { this.expireDate = expireDate; }

    public int getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public int getRejectLifecycle() {
        return rejectLifecycle;
    }

    public void setRejectLifecycle(int rejectLifecycle) {
        this.rejectLifecycle = rejectLifecycle;
    }

    public int getLockupLifecycle() {
        return lockupLifecycle;
    }

    public void setLockupLifecycle(int lockupLifecycle) {
        this.lockupLifecycle = lockupLifecycle;
    }

    public int getAdventLifecycle() {
        return adventLifecycle;
    }

    public void setAdventLifecycle(int adventLifecycle) {
        this.adventLifecycle = adventLifecycle;
    }

    public String getIsSNMgmt() {
        return isSNMgmt;
    }

    public void setIsSNMgmt(String isSNMgmt) {
        this.isSNMgmt = isSNMgmt;
    }

    public String getIsBatchMgmt() {
        return isBatchMgmt;
    }

    public void setIsBatchMgmt(String isBatchMgmt) {
        this.isBatchMgmt = isBatchMgmt;
    }

    public String getBatchRemark() {
        return batchRemark;
    }

    public void setBatchRemark(String batchRemark) {
        this.batchRemark = batchRemark;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getIsFragile() {
        return isFragile;
    }

    public void setIsFragile(String isFragile) {
        this.isFragile = isFragile;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }

    public String getString3() {
        return string3;
    }

    public void setString3(String string3) {
        this.string3 = string3;
    }

    public String getString4() {
        return string4;
    }

    public void setString4(String string4) {
        this.string4 = string4;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Date getUpdateTimeERP() { return updateTimeERP; }

    public void setUpdateTimeERP(Date updateTimeERP) { this.updateTimeERP = updateTimeERP; }

    public Date getCreateTimeERP() { return createTimeERP; }

    public void setCreateTimeERP(Date createTimeERP) { this.createTimeERP = createTimeERP; }
}
