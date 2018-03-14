package com.yatang.sc.operation.vo.supplier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:供应商入驻列表信息
 * @类名:QueryProviderEnterlistVo
 * @作者: lvheping
 * @创建时间: 2017/5/20 16:21
 * @版本: v1.0
 */
@Getter
@Setter
public class QueryProviderEnterlistVo implements Serializable {
	private static final long	serialVersionUID	= 3367455652298741329L;
	private String				spRegNo;									// 供应商注册号
	private String				companyName;								// 公司名称
	private String				name;										// 联系人
	private String				phone;										// 联系人手机
	private String				email;										// 联系人邮箱
	private BigDecimal  		rebaterate;									// 返利
	private String				spNo;										// 供应商编号
	private Integer				settlementAccountType;						// 结算账户类型
	private Integer				settlementPeriod;							// 结算账期
	private Date				settledDate;								// 入驻时间
	private Date				settledRequestTime;							// 入驻申请时间
	private BigDecimal			guaranteeMoney;								// 保证金余额
	private Integer				status;										// 审核状态

}
