package com.yatang.sc.glink.dto.saleOrder;

import java.io.Serializable;
import java.util.List;

/**
 * @描述:销售包裹信息
 * @作者: wangcheng
 * @创建时间:2017年08月04日15:51
 */
public class SalePackageDto implements Serializable{

    private String logisticsCode;//物流公司编码
    private String logisticsName;//物流公司名称
    private String expressCode;//运单号
    private String packageCode;//包裹编码
    private double theoreticalWeight;//包裹理论重量
    private double weight;//包裹重量
    private double volume;//包裹体积
    private double length;//长
    private double width;//宽
    private double height;//高
    private String invoiceNo;//发票编号
    private String packageMaterialType;//耗材型号
    private int packageMaterialQuantity;//耗材数量
    private List<SalePackageItemDto> items;//该包裹内的商品信息

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public double getTheoreticalWeight() {
        return theoreticalWeight;
    }

    public void setTheoreticalWeight(double theoreticalWeight) {
        this.theoreticalWeight = theoreticalWeight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getPackageMaterialType() {
        return packageMaterialType;
    }

    public void setPackageMaterialType(String packageMaterialType) {
        this.packageMaterialType = packageMaterialType;
    }

    public int getPackageMaterialQuantity() {
        return packageMaterialQuantity;
    }

    public void setPackageMaterialQuantity(int packageMaterialQuantity) {
        this.packageMaterialQuantity = packageMaterialQuantity;
    }

    public List<SalePackageItemDto> getItems() {
        return items;
    }

    public void setItems(List<SalePackageItemDto> items) {
        this.items = items;
    }
}
