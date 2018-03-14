package com.yatang.sc.operation.vo.ag;

import com.yatang.sc.validgroup.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @描述: 区域组参数对象
 * @作者: zhudanning
 * @创建时间: 2018/1/11-15:03 .
 * @版本: 1.0 .
 */
public class WriteParamAreaVo implements Serializable{


	private static final long serialVersionUID = 2274589802734325197L;

	/**
	 * 区域组编码
	 */
	@NotBlank(groups = {GroupThree.class,GroupFour.class,GroupTwo.class})
	private String areaGroupCode;

	/**
	 * 区域组名称
	 */
	@NotBlank(groups = {GroupOne.class,GroupFour.class,GroupTwo.class},message = "{msg.notEmpty.message}")
	@Length(groups = {GroupOne.class},max=15,message = "{msg.length.message}")
	private String areaGroupName;

	/**
	 * 门店Id
	 */
	@NotBlank(groups = {GroupTwo.class,DefaultGroup.class})
	private String storeIds;

	/**
	 * 子公司Id
	 */
	@NotBlank(groups = {GroupOne.class,GroupFour.class})
	private String branchCompanyId;

	/**
	 * 子公司的名称
	 */
	@NotBlank(groups = {GroupOne.class})
	private String branchCompanyName;


	/** 省份ID */
	private String				provinceId;

	/** 城市ID */
	private String				cityId;

	/** 区域ID */
	private String				districtId;

	/** 门店名称或者ID */
	private String				idOrName;

	public String getAreaGroupCode() {
		return areaGroupCode;
	}

	public void setAreaGroupCode(String areaGroupCode) {
		this.areaGroupCode = areaGroupCode;
	}

	public String getAreaGroupName() {
		return areaGroupName;
	}

	public void setAreaGroupName(String areaGroupName) {
		this.areaGroupName = areaGroupName;
	}

	public String getStoreIds() {
		return storeIds;
	}

	public void setStoreIds(String storeId) {
		this.storeIds = storeId;
	}

	public String getBranchCompanyId() {
		return branchCompanyId;
	}

	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}

	public String getBranchCompanyName() {
		return branchCompanyName;
	}

	public void setBranchCompanyName(String branchCompanyName) {
		this.branchCompanyName = branchCompanyName;
	}

	public String getProvinceId() { return provinceId; }

	public void setProvinceId(String provinceId) { this.provinceId = provinceId; }

	public String getCityId() { return cityId; }

	public void setCityId(String cityId) { this.cityId = cityId; }

	public String getDistrictId() { return districtId; }

	public void setDistrictId(String districtId) { this.districtId = districtId; }

	public String getIdOrName() { return idOrName; }

	public void setIdOrName(String idOrName) { this.idOrName = idOrName; }
}
