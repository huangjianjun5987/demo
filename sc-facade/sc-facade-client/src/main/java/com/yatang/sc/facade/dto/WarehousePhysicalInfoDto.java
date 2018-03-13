package com.yatang.sc.facade.dto;

import java.io.Serializable;
import java.util.Date;

public class WarehousePhysicalInfoDto implements Serializable {

	private static final long	serialVersionUID	= 1L;
	private Integer				id;

	private String				warehouseCode;

	private String				thirdWarehouseCode;

	private String				warehouseName;

	private String				warehouseService;

	private String				provinceCode;

	private String				province;

	private String				cityCode;

	private String				city;

	private String				countyCode;

	private String				county;

	private String				address;

	private String				contactPerson;

	private String				contactMode;

	private Date				createTime;

	private Date				modifyTime;

	private String				createUser;

	private String				modifyUser;



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getWarehouseCode() {
		return warehouseCode;
	}



	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}



	public String getThirdWarehouseCode() {
		return thirdWarehouseCode;
	}



	public void setThirdWarehouseCode(String thirdWarehouseCode) {
		this.thirdWarehouseCode = thirdWarehouseCode;
	}



	public String getWarehouseName() {
		return warehouseName;
	}



	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}



	public String getWarehouseService() {
		return warehouseService;
	}



	public void setWarehouseService(String warehouseService) {
		this.warehouseService = warehouseService;
	}



	public String getProvinceCode() {
		return provinceCode;
	}



	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}



	public String getProvince() {
		return province;
	}



	public void setProvince(String province) {
		this.province = province;
	}



	public String getCityCode() {
		return cityCode;
	}



	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getCountyCode() {
		return countyCode;
	}



	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}



	public String getCounty() {
		return county;
	}



	public void setCounty(String county) {
		this.county = county;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getContactPerson() {
		return contactPerson;
	}



	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}



	public String getContactMode() {
		return contactMode;
	}



	public void setContactMode(String contactMode) {
		this.contactMode = contactMode;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public Date getModifyTime() {
		return modifyTime;
	}



	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}



	public String getCreateUser() {
		return createUser;
	}



	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}



	public String getModifyUser() {
		return modifyUser;
	}



	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

}
