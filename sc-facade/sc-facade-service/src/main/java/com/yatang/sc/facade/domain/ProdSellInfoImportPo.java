package com.yatang.sc.facade.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class ProdSellInfoImportPo implements Serializable {

	private static final long	serialVersionUID	= -5295088765796840268L;
	private Long				id;											// id

	private Long				importsId;									// 上传ID

	private Long				lineNumber;									// 行号

	private Integer				handleResult;								// 处理结果:0:错误;1:已验证;2:已提交

	private String				handleInformation;							// 处理信息

	private String				productId;									// 商品ID

	private String				productInformation;							// 商品信息

	private BigDecimal			newestPrice;								// 最新售价

	private Integer				startNumber;								// 区间起始数量

	private Integer				endNumber;									// 区间结束数量

	private String				branchCompanyId;							// 子公司id

	private Long				priceId;									// 售价表id

	private String				productCode;								// 商品编码

	private String				section;									// 导入区间数据

}