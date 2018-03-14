package com.yatang.sc.operation.vo.supplier;


import com.yatang.sc.operation.common.BaseVo;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupThree;
import com.yatang.sc.validgroup.GroupTwo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @描述: 区域组查询条件
 * @作者: zhudanning
 * @创建时间: 2018/1/11-15:03 .
 * @版本: 1.0 .
 */
public class ParamAreaGroupVo extends BaseVo implements Serializable{

	private static final long serialVersionUID = 7773752766086721260L;

	/**
	 * 区域组编码
	 */
	@NotBlank(groups = {GroupTwo.class, GroupThree.class})
	private String areaGroupCode;

	/**
	 * 区域组名称
	 */
	@NotBlank(groups = {GroupOne.class},message = "{msg.notEmpty.message}")
	@Length(groups = {GroupOne.class},max=15,message = "{msg.length.message}")
	private String areaGroupName;

	/**
	 * 门店Id
	 */
	@NotBlank(groups = {GroupTwo.class})
	private String storeId;




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

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
}
