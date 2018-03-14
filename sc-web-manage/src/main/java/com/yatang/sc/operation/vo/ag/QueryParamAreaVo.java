package com.yatang.sc.operation.vo.ag;



import com.yatang.sc.operation.common.BaseVo;
import com.yatang.sc.validgroup.GroupOne;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @描述: 区域组参数对象
 * @作者: zhudanning
 * @创建时间: 2018/1/11-15:03 .
 * @版本: 1.0 .
 */
public class QueryParamAreaVo extends BaseVo implements Serializable {


	private static final long serialVersionUID = -866668458562075994L;



	/**
	 * 门店的Id
	 */
	private String idOrName;


	/**
	 * 子公司的Id
	 */
	@NotBlank(groups = GroupOne.class)
	private String branchCompanyId;


	/**
	 * 区域组编码
	 */
	private String areaGroupId;

	/**
	 * 区域组的编码和名称
	 */
	private String areaGroupIdOrName;


	/** 省份ID */
	private String	provinceId;

	/** 城市ID */
	private String	cityId;

	/** 区域ID */
	private String	districtId;

	/**表示门店是否已划分分组 true:已分组  false 未分组*/
	@NotNull(groups = GroupOne.class)
	private Boolean existsAreaGroup;


	public String getAreaGroupIdOrName() {
		return areaGroupIdOrName;
	}

	public void setAreaGroupIdOrName(String areaGroupIdOrName) {
		this.areaGroupIdOrName = areaGroupIdOrName;
	}

	public String getIdOrName() {
		return idOrName;
	}

	public void setIdOrName(String idOrName) {
		this.idOrName = idOrName;
	}

	public Boolean getExistsAreaGroup() {
		return existsAreaGroup;
	}

	public void setExistsAreaGroup(Boolean existsAreaGroup) {
		this.existsAreaGroup = existsAreaGroup;
	}

	public String getBranchCompanyId() {
		return branchCompanyId;
	}

	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}


	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getAreaGroupId() { return areaGroupId; }

	public void setAreaGroupId(String areaGroupId) { this.areaGroupId = areaGroupId; }
}
