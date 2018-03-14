package com.yatang.sc.app.vo.orderreturned;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: App
 * @类名: ReturnRequestQueryParamVo
 * @作者: kangdong
 * @创建时间: 2017/10/17 15:11
 * @版本: v1.0
 */
@Getter
@Setter
public class ReturnRequestQueryParamVo implements Serializable {

	private static final long serialVersionUID = -5352683957090010632L;
	//private String profileId;

	private String state;

	private Integer pageNum;

	private Integer pageSize;
}
