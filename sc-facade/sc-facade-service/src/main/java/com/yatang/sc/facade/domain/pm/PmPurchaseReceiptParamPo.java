package com.yatang.sc.facade.domain.pm;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 用于封装查询参数的vo
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年7月29日
 */
@Getter
@Setter
public class PmPurchaseReceiptParamPo implements Serializable {

	private static final long serialVersionUID = 8112565809497294852L;

	/**
	 * 收货单号
	 */
	private String purchaseReceiptNo;

	/**
	 * 采购单号
	 */
	private String purchaseOrderNo;

	/**
	 * 收货日期(开始)
	 */
	private Date receivedTimeStart;

	/**
	 * 收货日期(结束)
	 */
	private Date receivedTimeEnd;

	/**
	 * 子公司ID
	 */
	private String branchCompanyId;

	/**
	 * 状态:0:待下发，1：已下发，2:已收货，3:已取消，4：异常
	 */
	private Integer status;

	/**
	 * 采购单类型:0:普通采购单;1:赠品采购;2:促销釆购
	 */
	private Integer purchaseOrderType;

	/**
	 * 供应商编码
	 */
	private String spNo;

	/**
	 * 供应商地点编码
	 */
	private String spAdrNo;

	/**
	 * 地点类型:0:仓库;1:门店
	 */
	private Integer adrType;

	/**
	 * 地点类型编码
	 */
	private String adrTypeCode;

	/**
	 * 开始审核日期
	 */
	private Date startAuditTime;

	/**
	 * 结束审核日期
	 */
	private Date endAuditTime;

	private Integer pageSize = 10;

	private Integer pageNum = 1;

	/**
	 *  经营模式:0:经销;1:代销
	 * */
	private Integer				businessMode;

	/**
	 * 地点类型编码(此字段专为一个子公司可能对应多个逻辑仓库时的统采查询)
	 */
	private List<String> adrTypeCodes;
}
