package com.yatang.sc.app.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.facade.enums.AdType;
import com.yatang.sc.app.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.app.web.jackson.ImageUrlSerializer;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 首页配置楼层广告
 * @作者: yipeng
 * @创建时间: 2017年06月08日16:07:41
 * @版本: 1.0 .
 */
@Getter
@Setter
public class HomeItemAdVo implements java.io.Serializable {

	private static final long	serialVersionUID	= -7550039390911841722L;

	private String				id;
	/**
	 * 区域ID
	 */
	private String				areaId;
	/**
	 * 广告位名称
	 */
	private String				name;
	/**
	 * 标题
	 */
	private String				title;
	/**
	 * 副标题
	 */
	private String				subTitle;
	/**
	 * 商品编号
	 */
	private String				productNo;
	/**
	 * 链接类型，1：详情链接，2：分类链接，3：列表链接，4：页面链接，5：外部链接，6：活动链接
	 */
	private Integer				urlType;
	/**
	 * 跳转链接
	 */
	private String				url;
	/**
	 * 广告图片
	 */
	@JsonSerialize(using = ImageUrlSerializer.class)
	@JsonDeserialize(using = ImageUrlDeserializer.class)
	private String				icon;
	/**
	 * 广告类型
	 */
	private AdType				adType;
	/**
	 * 关键字
	 */
	private String				linkKeyword;

	/**
	 * 分类ID
	 */
	private String				linkId;

}