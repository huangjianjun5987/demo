package com.yatang.sc.operation.vo.prod.place;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import java.io.Serializable;



public class AreaGroupVo implements Serializable {

	private static final long serialVersionUID = -7023313283973700983L;


	/**
	 * 区域组编码
	 */
	private String areaGroupCode;

	/**
	 * 区域组名称
	 */
	@NotBlank(message = "{msg.notEmpty.message}")
	@Length(max=15,message = "{msg.length.message}")
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

