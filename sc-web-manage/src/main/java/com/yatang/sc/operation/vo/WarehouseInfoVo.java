package com.yatang.sc.operation.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * <class description> 仓库信息展示VO
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月18日
 */
@Getter
@Setter
public class WarehouseInfoVo implements Serializable {
	private static final long	serialVersionUID	= 1L;
	// 逻辑仓库ID
	private Integer				warehouseLogicId;
	// 逻辑仓库编码
	private String				warehouseLogicCode;
	// 逻辑仓库名称
	private String				warehouseLogicName;
	// 仓库服务方
	private String				warehouseService;
	// 送货仓联系人
	private String				contactPerson;
	// 送货仓联系方式
	private String				contactMode;
	// 送货仓区域信息：xx省xx市xx区
	private String				region;
	// 送货仓详细地址
	private String				address;

}
