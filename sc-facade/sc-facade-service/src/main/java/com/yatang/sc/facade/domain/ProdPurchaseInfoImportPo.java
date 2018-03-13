package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProdPurchaseInfoImportPo implements Serializable {

	private static final long	serialVersionUID	= -4019821017127408136L;
	private Long				id;											// id

	private Long				importsId;									// 上传ID

	private Long				lineNumber;									// 行号

	private Integer				handleResult;								// 处理结果:0:错误;1:已验证;2:已提交

	private String				handleInformation;							// 处理信息

	private String				productId;									// 商品ID(商品编码)

	private String				productInformation;							// 商品信息

	private BigDecimal			newestPrice;								// 最新进价

	private String				spId;										// 供应商id

	private Integer				spAdrId;									// 供应商地点id

	private Long				priceId;									// 采购表id

	private String				spNo;										// 供应商编号

	private String				spAdrNo;									// 供应商地点编码

	private String				productCode;								// 商品编码
}