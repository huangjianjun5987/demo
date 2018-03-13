package com.yatang.sc.facade.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述: 供应商简约信息
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/5/26 15:04
 * @版本: v1.0
 */
@Getter
@Setter
public class SupplierBasicPo implements Serializable {

	private static final long	serialVersionUID	= 7882700831787335044L;

	private String				providerId;								// 供应商id主表id

	private String				providerName;								// //
																			// 供应商公司名称
}
