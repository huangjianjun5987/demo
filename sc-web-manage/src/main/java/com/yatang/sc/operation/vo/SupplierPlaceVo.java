package com.yatang.sc.operation.vo;

import java.io.Serializable;

import com.yatang.sc.facade.dto.supplier.SupplierAdrInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoDto;

import com.yatang.sc.operation.vo.supplier.SupplierAdrInfoVo;
import com.yatang.sc.operation.vo.supplier.SupplierInfoVo;
import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 查询供应商地点信息
 * @类名: SupplierPlaceVo
 * @作者: kangdong
 * @创建时间: 2017/7/25 09:35
 * @版本: v1.0
 */
@Getter
@Setter
public class SupplierPlaceVo implements Serializable {

	private static final long	serialVersionUID	= -3112555473175454477L;

	private SupplierInfoVo		supplierInfo;								// 供应商信息

	private SupplierAdrInfoVo	supplierAdrInfo;							// 供应商地点信息
}
