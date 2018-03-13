package com.yatang.sc.facade.dto.prod;

import java.io.Serializable;
import java.util.List;

import com.yatang.sc.facade.common.BaseDto;

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
public class ProdSellPriceQueryParamDto extends BaseDto implements Serializable {
	private static final long	serialVersionUID	= 5715262877187624740L;
	private String  productId;									// 商品id
	private String  branchCompanyId;								// 分公司id
	private String  branchCompanyName;								// 分公司名字
	private Integer status;										// 价格状态0为失效状态1为正常使用状态
	private Long    id;											// 定价id

	private Long 	auditStatus;                                // 审核状态:1:已提交;2:已审核;3:已拒绝 yinyuxin
	/**
	 * 权限修改：子公司账号也可以有多个子公司的权限:yinyuxin
	 */
	private List<String> branchCompanyIds;

}
