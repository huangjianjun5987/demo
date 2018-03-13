package com.yatang.sc.facade.dto.supplier;

import java.io.Serializable;

import com.yatang.sc.facade.dto.WarehouseInfoDto;
import com.yatang.sc.facade.dto.WarehouseLogicDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpAdrDeliveryDto implements Serializable {

	private static final long	serialVersionUID	= -5985862502046097806L;

	private Integer				id;

	/** 供应商地点ID */
	//@JsonIgnore
	private Integer				spAdrId;

	/** 仓库ID */
	//@JsonIgnore
	private Integer				warehouseId;

	/** 仓库信息 */
	//@JsonIgnore
	//private WarehouseLogicDto 	warehouse;
	private WarehouseInfoDto warehouse;
}