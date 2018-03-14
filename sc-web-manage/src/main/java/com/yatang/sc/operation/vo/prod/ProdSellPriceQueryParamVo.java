package com.yatang.sc.operation.vo.prod;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.yatang.sc.facade.common.BaseDto;
import com.yatang.sc.operation.common.BaseVo;
import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:商品区域销售价格查询参数
 * @类名:GoodsSellPriceQueryParam
 * @作者: lvheping
 * @创建时间: 2017/6/12 20:47
 * @版本: v1.0
 */
@Getter
@Setter
public class ProdSellPriceQueryParamVo extends BaseVo implements Serializable {
	private static final long	serialVersionUID	= 5715262877187624740L;
	@NotNull(groups = { GroupOne.class, DefaultGroup.class }, message = "{msg.notEmpty.message}")
	private String				productId;									// 商品id
	private String				branchCompanyId;							// 分公司id
	private String				branchCompanyName;							// 分公司名字
	private Integer				status;										// 价格状态0为失效状态1为正常使用状态
	@NotNull(groups = GroupOne.class, message = "{msg.notEmpty.message}")
	private Long				id;											// 定价id
	private Long 				auditStatus;                                // 审核状态:1:已提交;2:已审核;3:已拒绝 yinyuxin
}
