package com.yatang.sc.facade.dto.prod;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProdSellInfoImportsDto implements Serializable {


	private static final long serialVersionUID = 3097967566325249744L;
	private Long				id;											// 调价单ID(上传ID)

	private Date				createTime;									// 创建时间

	private String				createUserId;								// 创建人

	private List<ProdSellInfoImportDto>	imports;									// 商品售价导入记录表
}