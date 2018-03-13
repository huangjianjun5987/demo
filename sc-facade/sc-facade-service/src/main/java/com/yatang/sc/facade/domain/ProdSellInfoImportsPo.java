package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProdSellInfoImportsPo implements Serializable {

	private static final long	serialVersionUID	= -3611695796957678000L;
	private Long				id;											// 调价单ID(上传ID)

	private Date				createTime;									// 创建时间

	private String				createUserId;								// 创建人

	private List<ProdSellInfoImportPo>	imports;									// 商品售价导入记录表

	public boolean isAllVaild() {
		for (ProdSellInfoImportPo anImport : imports) {
			if (!Objects.equals(anImport.getHandleResult(), 1)) {
				return false;
			}
		}
		return true;
	}
}