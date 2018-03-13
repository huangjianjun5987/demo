package com.yatang.sc.inventory.domain;

import java.io.Serializable;
import java.util.Date;

public class TranData implements Serializable {
    private Long id;

    private String item;

    private String groups;

    private String dept;

    private String classs;

    private String subclass;

    private String location;

    private Date tranDate;

    private String tranCode;

    private Long units;

    private Double totalCost;

    private Double totalRetail;

    private String refNo1;

    private String refNo2;

    private String refNo3;

    private String attr1;

    private String attr2;

    private String vatRate;

    private String productCode;//商品Code

    private Double taxes;//税额


    private static final long serialVersionUID = 1L;

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

    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }
}