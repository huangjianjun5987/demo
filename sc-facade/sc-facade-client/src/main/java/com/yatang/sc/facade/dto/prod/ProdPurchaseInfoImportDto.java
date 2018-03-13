package com.yatang.sc.facade.dto.prod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProdPurchaseInfoImportDto implements Serializable {


	private static final long serialVersionUID = -5229516857319232739L;

	private Long				id;											// id

	private Long				importsId;									// 上传ID

	private Long				lineNumber;									// 行号

	private Integer				handleResult;								// 处理结果:0:错误;1:已验证;2:已提交

	private String				handleInformation;							// 处理信息

	private String				productId;									// 商品ID

	private String				productCode;									// 商品编码

	private String				productInformation;							// 商品信息

	private BigDecimal			newestPrice;								// 最新进价

	private String				spId;										// 供应商id

	private String				spNo;										// 供应商编码

	private String				spName;										// 供应商名称

	private String				spAdrId;									// 供应商地点id

	private String				spAdrNo;									// 供应商地点编码

	private String				spAdrName;									// 供应商地点名称

	private Date				uploadDate;									// 上传日期

	private Long				priceId;									// 采购表id
}