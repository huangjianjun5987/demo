package com.yatang.sc.app.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 商品分类vo(app)
 * @作者: yinyuxin
 * @创建时间: 2017年7月8日16:15:53
 * @版本: 1.0 .
 */
@Setter
@Getter
public class ShippingGroupVo implements Serializable{

	private String province;

	private String city;

	private String district;

	private String detailAddress;

	private String consigneeName;

	private String cellphone;
}
