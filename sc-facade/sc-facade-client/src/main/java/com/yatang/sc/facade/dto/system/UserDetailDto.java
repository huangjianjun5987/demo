package com.yatang.sc.facade.dto.system;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.yatang.sc.facade.common.BaseDto;

/**
 * @描述: 用户Dto.
 * @作者: huangjianjun
 * @创建时间: 2017年6月9日 下午1:59:04 .
 */
@Getter
@Setter
public class UserDetailDto extends BaseDto implements Serializable {


	private static final long	serialVersionUID	= 6207371808946302450L;

	private Long				id;

	private String				userName;

	private String 				spId;											//供应商ID
	
	private String 				spName;											//供应商名称
	
	private Integer 			type;											//用户类型((1:管理员2:供应商))

	private Boolean				locked;

	private String				fullName;
	
	private String				spNo;
	
	private Integer				grade;

	private String	 			address;
	
	private Date				settledTime;
	
	private Date				createTime;

	private Date				updateTime;
	
	private String				createUser;
	
	private String				updateUser;

	@Override
	public String toString() {
		return "UserDetailDto [id=" + id + ", userName=" + userName + ", spId=" + spId + ", type=" + type + ", locked="
				+ locked + ", fullName=" + fullName + ", spNo=" + spNo + ", grade=" + grade + ", address=" + address
				+ ", settledTime=" + settledTime + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}

}