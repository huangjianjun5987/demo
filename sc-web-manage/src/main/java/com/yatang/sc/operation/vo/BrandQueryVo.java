package com.yatang.sc.operation.vo;

import java.io.Serializable;

import com.yatang.sc.operation.common.BaseVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * @描述: 商品品牌接收查询参数vo
 * @作者: yinyuxin
 * @创建时间: 2017年7月20日09:31:23
 * @版本: 1.0 .
 */
@Setter
@Getter
@ToString
public class BrandQueryVo extends BaseVo implements Serializable{

	private static final long serialVersionUID = -3007743365655605841L;

	//品牌ID
	private String              id;
		
	//品牌名称
	private String				name;

	//采购单单号
	private String              purchaseOrderNo;


}
