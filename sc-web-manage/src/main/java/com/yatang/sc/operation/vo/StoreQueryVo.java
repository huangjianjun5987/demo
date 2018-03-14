package com.yatang.sc.operation.vo;

import com.yatang.sc.operation.common.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * <class description>查询门店值列表的VO
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月26日
 */
@Getter
@Setter
public class StoreQueryVo extends BaseVo {

	private static final long	serialVersionUID	= 1L;
	// 查询参数
	private String				param;

}
