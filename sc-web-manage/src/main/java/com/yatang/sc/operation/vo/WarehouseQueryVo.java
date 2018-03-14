package com.yatang.sc.operation.vo;

import java.io.Serializable;

import com.yatang.sc.operation.common.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * <class description> 条件查询VO
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月20日
 */
@Getter
@Setter
public class WarehouseQueryVo extends BaseVo implements Serializable {

	private static final long	serialVersionUID	= 1L;
	// 查询条件
	private String				param;
}
