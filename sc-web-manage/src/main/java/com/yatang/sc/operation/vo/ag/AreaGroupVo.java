package com.yatang.sc.operation.vo.ag;

import java.io.Serializable;

public class AreaGroupVo implements Serializable {

	private static final long serialVersionUID = 7395568108360110288L;

	/**
	 * 区域组编码
	 */
	private String areaGroupCode;

	/**
	 * 区域组名称
	 */
	private String areaGroupName;

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
}
