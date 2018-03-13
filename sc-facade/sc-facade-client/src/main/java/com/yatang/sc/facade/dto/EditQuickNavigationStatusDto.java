package com.yatang.sc.facade.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 接收批量修改快捷导航状态参数
 * @作者: tankejia
 * @创建时间: 2017/7/5-10:29 .
 * @版本: 1.0 .
 */
@Data
public class EditQuickNavigationStatusDto implements Serializable {

	private static final long	serialVersionUID	= -951583687867409982L;

	/**
	 * 待修改的id
	 * */
	private List<Integer>		ids;

	/**
	 * 状态
	 * */
	private Integer				status;

}
