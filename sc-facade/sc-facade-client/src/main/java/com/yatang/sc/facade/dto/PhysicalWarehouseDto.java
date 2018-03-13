package com.yatang.sc.facade.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhysicalWarehouseDto implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private Integer				id;

	private String				warehouseCode;

	private String				thirdWarehouseCode;

	private String				warehouseName;

	private String				warehouseService;

	private String				provinceCode;

	private String				province;

	private String				cityCode;

	private String				city;

	private String				countyCode;

	private String				county;

	private String				address;

	private String				contactPerson;

	private String				contactMode;

	private Date				createTime;

	private Date				modifyTime;

	private String				createUser;

	private String				modifyUser;

}
