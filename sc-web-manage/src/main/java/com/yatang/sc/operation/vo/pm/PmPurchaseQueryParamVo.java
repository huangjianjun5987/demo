package com.yatang.sc.operation.vo.pm;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yatang.sc.operation.common.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:
 * @类名:
 * @作者: lvheping
 * @创建时间: 2017/7/26 11:11
 * @版本: v1.0
 */
@Getter
@Setter
public class PmPurchaseQueryParamVo extends BaseVo implements Serializable {
	private static final long	serialVersionUID	= -7914050911760114834L;
	private String				purchaseOrderNo;							// 采购单号
	private Integer				adrType;									// 地点类型:0:仓库;1:门店

	private String				adrTypeCode;								// 地点类型编码

	private Integer				purchaseOrderType;							// 采购单类型:0:普通采购单;1:赠品采购;2:促销釆购
	private String				secondCategoryId;							// 一级分类id
	private String				spNo;										// 供应商编码

	private String				spAdrId;									// 供应商地点ID
	private Integer				status;										// 状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:已关闭;5:已取消 *18-1-8zby新增状态5。*
	private Date				startCreateTime;							// 开始创建时间
	private Date				endCreateTime;								// 结束创建时间
	private Date				startAuditTime;								// 开始审核日期
	private Date				endAuditTime;								// 结束审核日期
    private String				branchCompanyId;							// 当前子公司id
	private Integer				businessMode;								// 经营模式:0:经销;1:代销;2:寄售 *18-1-8zby新增状态2。*
	private Integer 			spAcceptStatus;							// *18-1-8zby新增字段。*供应商接单状态:0未接单;1:已接单 
	public void setStartCreateTime(String startCreateTime) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(startCreateTime)));
			Date minDate = format.parse(format1);
			this.startCreateTime = minDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setEndCreateTime(String endCreateTime) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(endCreateTime)));
			Date minDate = format.parse(format1);
			this.endCreateTime = minDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setStartAuditTime(String startAuditTime) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(startAuditTime)));
			Date minDate = format.parse(format1);
			this.startAuditTime = minDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setEndAuditTime(String endAuditTime) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(endAuditTime)));
			Date minDate = format.parse(format1);
			this.endAuditTime = minDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
