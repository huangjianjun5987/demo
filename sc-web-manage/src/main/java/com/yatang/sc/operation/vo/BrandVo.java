package com.yatang.sc.operation.vo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.web.jackson.ImageUrlSerializer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @描述: 商品品牌vo
 * @作者: yinyuxin
 * @创建时间: 2017年7月20日09:31:23
 * @版本: 1.0 .
 */
@Setter
@Getter
@ToString
public class BrandVo implements Serializable {

	private static final long serialVersionUID = 5135501522570035316L;

	// 品牌ID
	private String id;

	// 品牌名称
	private String name;

	// 品牌标签
	private String brandLabel;

	// 品牌logo链接

	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String logoUrl;
}
