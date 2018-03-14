package com.yatang.sc.operation.vo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.operation.common.BaseVo;
import com.yatang.sc.web.jackson.HtmlFileUrlDeserializer;
import com.yatang.sc.web.jackson.HtmlFileUrlSerializer;
import com.yatang.sc.web.jackson.ImageUrlDeserializer;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:用作接收查询静态页列表参数
 * @类名:StaticPageQueyVo
 * @作者: lvheping
 * @创建时间: 2017/6/8 16:22
 * @版本: v1.0
 */
@Getter
@Setter
public class StaticPageQueyVo extends BaseVo implements Serializable {
	private static final long	serialVersionUID	= -7597848819114230653L;
	private int					id;											// 静态页ID
	private String				name;										// 静态页名称
	@JsonSerialize(using = HtmlFileUrlSerializer.class)
	@JsonDeserialize(using = HtmlFileUrlDeserializer.class)
	private String				linkUrl;									// 链接地址
}
