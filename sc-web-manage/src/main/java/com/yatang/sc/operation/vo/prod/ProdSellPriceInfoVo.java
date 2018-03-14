package com.yatang.sc.operation.vo.prod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdSellPriceInfoVo implements Serializable {
	private Long						id;							// 销售区间价id

	private String						productId;					// 商品id

	private BigDecimal					lowestPrice;				// 销售区间最低价

	private Date						createTime;					// 创建时间

	private Date						modifyTime;					// 停用或伪删除时间

	private Integer						status;						// 状态0为失效状态1为正常使用状态

	private String						branchCompanyId;			// 分公司id

	private String						branchCompanyName;			// 分公司名字

	private BigDecimal					suggestPrice;				// 建议零售价

	private Integer						deliveryDay;				// 承诺最迟发货时间（天）

	private Integer						salesInsideNumber;			// 销售内装数

	private Integer						minNumber;					// 最小销售单位

	private Integer						deleteStatus;				// 0为未删除状态1为删除状态

	private String						modifyUserId;				// 修改或删除操作人员id

	private String                      modifyUserName;             // 修改人姓名    yinyuxin

	private String						createUserId;				// 创建价格人员id

	private String                      createUserName;             // 创建价格人员姓名     yinyuxin

	private List<ProdSellSectionPriceVo>	sellSectionPrices;		// 区间价格信息

	private ProdSellPriceInfoVo 		 sellPricesInReview;        // 审核中的价格信息  yinyuxin(不持久化)

	private BigDecimal                  purchasePrice;              // 采购价（不持久化） yinyuxin

	private Integer 					maxNumber;                  // 最大销售量

	private Integer 					preHarvestPinStatus;        // 是否先采后销   0 ：否   1： 是  默认：否

	private String						auditUserId;				// 审核人ID

	private Date						auditTime;					// 审核时间

	private Integer						auditStatus;				// 审核状态:1:已提交;2:已审核;3:已拒绝(默认2)

	private String						auditUserName;				// 审核人姓名

	private Integer 					sellFullCase;               // 是否整箱销售：0-否；1-是(只有销售关系详情接口时有效)    yinyuxin

	private String 						fullCaseUnit;               // 整箱单位  (只有销售关系详情接口时有效)       yinyuxin

	private Integer						firstCreated;				// 第一次创建使用:1:是;0:否(默认0)

	private static final long			serialVersionUID	= 1L;

}