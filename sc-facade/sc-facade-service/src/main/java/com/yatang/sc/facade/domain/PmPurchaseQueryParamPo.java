package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yatang.sc.facade.common.BasePo;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:封装传入查询采购列表参数
 * @类名:PmPurchaseQueryParamPo
 * @作者: lvheping
 * @创建时间: 2017/7/26 11:11
 * @版本: v1.0
 */
@Getter
@Setter
public class PmPurchaseQueryParamPo extends BasePo implements Serializable {
	private static final long serialVersionUID = 6272894458858271954L;
	private String				purchaseOrderNo;							// 采购单号
	private Integer				adrType;									// 地点类型:0:仓库;1:门店

	private String				adrTypeCode;								// 地点类型编码

	private Integer				purchaseOrderType;							// 采购单类型:0:普通采购单;1:赠品采购;2:促销釆购
	private String				secondCategoryId;							// 一级分类id
	private String				spNo;										// 供应商编码

	private String				spAdrId;									// 供应商地点ID
	private Integer				status;										// 状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:已关闭;5:已取消*18-1-8zby新增状态5。*
	private Date				startCreateTime;							// 开始创建时间
	private Date				endCreateTime;								// 结束创建时间
	private Date				startAuditTime;								// 开始审核日期
	private Date				endAuditTime;								// 结束审核日期
    private String				branchCompanyId;								// 当前登录用户id
	private Integer				businessMode;								// 经营模式:0:经销;1:代销;2:寄售 *18-1-8zby新增状态2。*
	private List<String>        adrTypeCodes;								// 地点类型编码(此字段专为一个子公司可能对应多个逻辑仓库时的统采查询)
	private Integer 			spAcceptStatus;							// *18-1-8zby新增字段。*供应商接单状态:0未接单;1:已接单 
}
