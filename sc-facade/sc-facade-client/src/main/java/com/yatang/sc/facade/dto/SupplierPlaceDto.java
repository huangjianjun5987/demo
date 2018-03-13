package com.yatang.sc.facade.dto;

import java.io.Serializable;

import com.yatang.sc.facade.dto.supplier.SupplierAdrInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 查询供应商地点信息
 * @类名: SupplierPlaceDto
 * @作者: kangdong
 * @创建时间: 2017/7/25 09:35
 * @版本: v1.0
 */
@Getter
@Setter
public class SupplierPlaceDto implements Serializable {

	private static final long	serialVersionUID	= 6244213723980298545L;

	private SupplierInfoDto		supplierInfo;							// 供应商信息

	private SupplierAdrInfoDto	supplierAdrInfo;							// 供应商地点信息
}
