package com.yatang.sc.operation.vo.prod.place;

import com.yatang.sc.operation.common.BaseVo;


import java.io.Serializable;

public class AreaStoreVo extends BaseVo implements Serializable {

	private static final long serialVersionUID = -1684328655889091362L;


	/**
	 * 区域组编码
	 */
	private String areaGroupCode;

	/**
	 * 门店编码
	 */
	private String storeId;

	/**
	 * 门店名称
	 */
	private String storeName;

	/**
	 * 所属子公司名称
	 */
	private String branchCompanyName;
	/**
	 * 门店所属城市的名名称
	 */
	private String cityName;

	/**
	 * 门店所属区的名称
	 */
	private String districtName;

	/**
	 * 门店所属省份的名字
	 */
	private String provinceName;

	public String getAreaGroupCode() {
		return areaGroupCode;
	}

	public void setAreaGroupCode(String areaGroupCode) {
		this.areaGroupCode = areaGroupCode;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getBranchCompanyName() {
		return branchCompanyName;
	}

	public void setBranchCompanyName(String branchCompanyName) {
		this.branchCompanyName = branchCompanyName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
}
