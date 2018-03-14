package com.yatang.sc.operation.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * <class description>查询仓库信息的简单PO
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月17日
 */
@Getter
@Setter
public class WarehouseLogicSimpleResultVo implements Serializable {
	private static final long	serialVersionUID	= 1L;
	// 仓库ID
	private Integer				id;
	// 仓库编码
	private String				warehouseCode;
	// 仓库名称
	private String				warehouseName;
}
