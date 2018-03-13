package com.yatang.sc.facade.dto.prod.place;

import com.yatang.sc.facade.common.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 区域组DTO
 */
public class AreaGroupDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = -2749365371885211278L;


	/**
	 * 区域组编码
	 */
	private String areaGroupCode;

	/**
	 * 区域组名称
	 */
	private String areaGroupName;

	/**
	 * 区域组类型
	 */
	private String type;

	/**
	 * 区域组地点编码
	 */
	private String areaAddrCode;

	/**
	 * 区域组地点名称
	 */
	private String areaAddrName;


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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAreaAddrCode() {
		return areaAddrCode;
	}

	public void setAreaAddrCode(String areaAddrCode) {
		this.areaAddrCode = areaAddrCode;
	}

	public String getAreaAddrName() {
		return areaAddrName;
	}

	public void setAreaAddrName(String areaAddrName) {
		this.areaAddrName = areaAddrName;
	}


}
