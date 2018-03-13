package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 接收批量修改快捷导航状态参数
 * @作者: tankejia
 * @创建时间: 2017/7/5-10:29 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class EditQuickNavigationStatusPo implements Serializable {

	private static final long	serialVersionUID	= 7710140193830681246L;

	/**
	 * 待修改的id
	 * */
	private List<Integer>		ids;

	/**
	 * 状态
	 * */
	private Integer				status;

}
