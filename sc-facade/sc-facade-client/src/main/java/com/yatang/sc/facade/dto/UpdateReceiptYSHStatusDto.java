package com.yatang.sc.facade.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * <class description>
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年8月7日
 */
@Getter
@Setter
public class UpdateReceiptYSHStatusDto implements Serializable {

	private static final long	serialVersionUID	= 1L;

	// 收货单编号
	private String				purchaseReceiptNo;
}
