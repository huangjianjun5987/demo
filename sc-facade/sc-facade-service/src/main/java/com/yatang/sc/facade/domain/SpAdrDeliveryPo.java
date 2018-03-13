package com.yatang.sc.facade.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpAdrDeliveryPo implements Serializable {

	private static final long	serialVersionUID	= -5985862502046097806L;

	private Integer				id;

	/** 供应商地点ID */
	private Integer				spAdrId;

	/** 仓库ID */
	private Integer				warehouseId;

	/** 仓库信息 */
	//private WarehouseLogicInfoPo		warehouse;
	private WarehouseInfoPo warehouse;
}