package com.yatang.sc.operation.vo.pm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PmPurchaseOrderVo implements Serializable {


	private static final long	serialVersionUID	= 1690686279999759683L;

	@NotNull(groups = GroupOne.class, message = "{msg.notEmpty.message}")
	@Range(min = 1, max = Integer.MAX_VALUE, message = "{msg.range.message}", groups = GroupOne.class)
	private Long				id;											// pk

	private String				purchaseOrderNo;							// 采购单号

//	@NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
	private String				spId;										// 供应商ID

//	@NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
	private String				spNo;										// 供应商编码

//	@NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
	private String				spName;										// 供应商名称

	@NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
	private String				spAdrId;									// 供应商地点ID

//	@NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
	private String				spAdrNo;									// 供应商地点编码

//	@NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
	private String				spAdrName;									// 供应商地点名称

	@NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
	private Date				estimatedDeliveryDate;						// 预计送货日期

//	@NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
	private Integer				settlementPeriod;							// 账期

	@NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
	@Range(min =0,max = 3,message = "{msg.range.message}")
	private Integer				payType;									// 供应商付款方式：0：网银，1：银行转账，2：现金，3：支票

	private Integer				payCondition;									// 付款条件(1:票到七天,2:票到十五天,3:票到三十天)

	@NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
	@Range(min = 0,max = 1,message = "{msg.range.message}", groups = {GroupOne.class, DefaultGroup.class})
	private Integer				adrType;									// 地点类型:0:仓库;1:门店

	@NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
	private String				adrTypeCode;								// 地点类型编码

//	@NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
	private String				adrTypeName;								// 地点类型名称


	private String				secondCategoryId;			// 大类分类id


	private String				secondCategoryName;			// 大类分类名称


	private String				currencyCode;								// 货币种类代码,CNY

//	@NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
	private String				branchCompanyId;							// 子公司ID

	@NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
	@Range(min = 0,max = 2,message = "{msg.range.message}")
	private Integer				purchaseOrderType;							// 类型:0:普通采购单

	@NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
	@Range(min = 0,max = 1,message ="{msg.range.message}", groups = {GroupOne.class, DefaultGroup.class})
	private Integer				status;										// 状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:已关闭;5:已取消*18-1-8zby新增状态5。*

	private String				failedReason;								// 失败原因

	private String				auditUserId;								// 审核人ID

	private Date				createTime;									// 创建时间

	private Date				modifyTime;									// 修改时间

	private String				createUserId;								// 创建人

	private String				modifyUserId;								// 修改人

	private Date				auditTime;									// 审核日期

/*	@NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})*/
	private String				adrName;					// 门店和仓库详细地址（收货地址）

	private String				phone;						// 联系电话


	private String 				barCodeUrl;//条码地址

	private BigDecimal 			totalTaxAmount;//总税额

	private BigDecimal 			totalWithoutTaxAmount;//不含税采购金额

	private BigDecimal 			totalAmount;//总采购金额，含税'


	@NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
	@Range(min = 0,max = 1,message = "{msg.range.message}",groups = {GroupOne.class, DefaultGroup.class})
	private Integer 			businessMode; // 经营模式:0:经销;1:代销;2:寄售 *18-1-8zby新增状态2。*
	private Integer 			spAcceptStatus;							// *18-1-8zby新增字段。*供应商接单状态:0未接单;1:已接单



}