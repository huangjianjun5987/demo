package com.yatang.sc.inventory.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * @描述: 事务的dto
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/5 18:43
 * @版本: v1.0
 */
public class TTranDataDto implements Serializable {
    private static final long serialVersionUID = 4209412863139737146L;
    private Long id;//pk

    private String item;//商品编码

    private String groups;//部类

    private String dept;//大类

    private String classs;//中类

    private String subclass;//小类

    private String location;//仓库编码

    private Date tranDate;//交易日期

    private String tranCode;//事务

    private Long units;//交易数量

    private Double totalCost;//总成本 交易数量*业务单据中商品平均成本

    private Double totalRetail;//销售总额

    private String refNo1;//针对采购 采购订单号

    private String refNo2;//针对采购 入库单号

    private String refNo3;

    private String attr1;

    private String attr2;

    private String vatRate;//记录采购入库记录进项税，销售出库记录销项税

    private String productCode;//商品code

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item == null ? null : item.trim();
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups == null ? null : groups.trim();
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept == null ? null : dept.trim();
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs == null ? null : classs.trim();
    }

    public String getSubclass() {
        return subclass;
    }

    public void setSubclass(String subclass) {
        this.subclass = subclass == null ? null : subclass.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode == null ? null : tranCode.trim();
    }

    public Long getUnits() {
        return units;
    }

    public void setUnits(Long units) {
        this.units = units;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getTotalRetail() {
        return totalRetail;
    }

    public void setTotalRetail(Double totalRetail) {
        this.totalRetail = totalRetail;
    }

    public String getRefNo1() {
        return refNo1;
    }

    public void setRefNo1(String refNo1) {
        this.refNo1 = refNo1 == null ? null : refNo1.trim();
    }

    public String getRefNo2() {
        return refNo2;
    }

    public void setRefNo2(String refNo2) {
        this.refNo2 = refNo2 == null ? null : refNo2.trim();
    }

    public String getRefNo3() {
        return refNo3;
    }

    public void setRefNo3(String refNo3) {
        this.refNo3 = refNo3 == null ? null : refNo3.trim();
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1 == null ? null : attr1.trim();
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2 == null ? null : attr2.trim();
    }

    public String getVatRate() {
        return vatRate;
    }

    public void setVatRate(String vatRate) {
        this.vatRate = vatRate == null ? null : vatRate.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        return "TTranDataDto{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", groups='" + groups + '\'' +
                ", dept='" + dept + '\'' +
                ", classs='" + classs + '\'' +
                ", subclass='" + subclass + '\'' +
                ", location='" + location + '\'' +
                ", tranDate=" + tranDate +
                ", tranCode='" + tranCode + '\'' +
                ", units=" + units +
                ", totalCost=" + totalCost +
                ", totalRetail=" + totalRetail +
                ", refNo1='" + refNo1 + '\'' +
                ", refNo2='" + refNo2 + '\'' +
                ", refNo3='" + refNo3 + '\'' +
                ", attr1='" + attr1 + '\'' +
                ", attr2='" + attr2 + '\'' +
                ", vatRate='" + vatRate + '\'' +
                ", productCode='" + productCode + '\'' +
                '}';
    }
}