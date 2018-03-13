package com.yatang.sc.facade.dto.supplier;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 供应商地点查询组件DTO.
 * @作者: huangjianjun
 * @创建时间: 2017年7月20日-上午11:28:25 .
 */
@Getter
@Setter
public class SpSearchBoxDto implements Serializable {

	private static final long	serialVersionUID	= 5152043674291251164L;

	private String				spId;										// 供应商ID

	private String				spNo;										// 供应商编号

	private String				companyName;								// 公司名称

	private Integer				spAdrid;									// 供应商地点ID

	private String				providerNo;									// 供应商地点编号

	private String				providerName;								// 供应商地点名称

	private Integer				branchCompanyId;							// 雅堂分公司id

	/**
	 * 到货周期
	 * */
	private Integer				goodsArrivalCycle;							// 供应商地点提前期

	private Integer				payType;									// 供应商付款方式：0：网银，1：银行转账，2：现金，3：支票

	private Integer				payCondition;								// 付款条件(1:票到七天,2:票到十五天,3:票到三十天)

	private Integer				settlementPeriod;							// 供应商地点提前期



	@Override
	public String toString() {
		return "SpSearchBoxDto [spId=" + spId + ", spNo=" + spNo + ", companyName=" + companyName + ", spAdrid="
				+ spAdrid + ", providerNo=" + providerNo + ", providerName=" + providerName + "]";
	}

}
