package com.yatang.sc.inventory.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiangyonghong on 2017/7/31.
 */
public class TranDataDto implements Serializable{

    private long id;

    private String item;
    private String group;
    private String groups;

    private String dept;

    private String classs;

    private String subclass;

    private String location;

    private Date tranDate;

    private String tranCode;

    private long units;

    private double totalCost;

    private double totalRetail;

    private String refNo1;

    private String refNo2;

    private String refNo3;

    private String attr1;

    private String attr2;

    private String vatRate;

    private Double taxes;//税额

    private String productCode;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getClasss() {
		return classs;
	}

	public void setClasss(String classs) {
		this.classs = classs;
	}

	public String getSubclass() {
		return subclass;
	}

	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
		this.tranCode = tranCode;
	}

	public long getUnits() {
		return units;
	}

	public void setUnits(long units) {
		this.units = units;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getTotalRetail() {
		return totalRetail;
	}

	public void setTotalRetail(double totalRetail) {
		this.totalRetail = totalRetail;
	}

	public String getRefNo1() {
		return refNo1;
	}

	public void setRefNo1(String refNo1) {
		this.refNo1 = refNo1;
	}

	public String getRefNo2() {
		return refNo2;
	}

	public void setRefNo2(String refNo2) {
		this.refNo2 = refNo2;
	}

	public String getRefNo3() {
		return refNo3;
	}

	public void setRefNo3(String refNo3) {
		this.refNo3 = refNo3;
	}

	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	public String getVatRate() {
		return vatRate;
	}

	public void setVatRate(String vatRate) {
		this.vatRate = vatRate;
	}

	public Double getTaxes() {
		return taxes;
	}

	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

   
}
