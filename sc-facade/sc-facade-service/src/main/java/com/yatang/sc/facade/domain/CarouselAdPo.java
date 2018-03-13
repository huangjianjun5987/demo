package com.yatang.sc.facade.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 轮播广告信息
 * @作者: tankejia
 * @创建时间: 2017/6/8-15:22 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class CarouselAdPo implements Serializable {

	private static final long	serialVersionUID	= 95325875617908229L;

	/**
	 * 编号
	 * */
	private Integer				id;

	/**
	 * 排序
	 * */
	private Integer				sorting;

	/**
	 * 链接类型，1：详情链接，2：分类链接，3：列表链接，4：页面链接，5：外部链接，6：活动链接
	 * */
	private Integer				linkType;

	/**
	 * 商品编号
	 * */
	private String				goodsId;

	/**
	 * 链接地址
	 * */
	private String				linkAddress;

	/**
	 * 图片地址
	 * */
	private String				picAddress;

	/**
	 * 状态（0：停用，1：已启用）
	 * */
	private Integer				status;

	/**
	 * 创建人
	 * */
	private String				createPerson;

	/**
	 * 修改人
	 * */
	private String				updatePerson;

	/**
	 * 创建时间
	 * */
	private Date				gmtCreate;

	/**
	 * 所属areaID
	 */
	private String				areaId;

	/**
	 * 关键字
	 */
	private String				linkKeyword;

	/**
	 * 分类ID
	 */
	private String				linkId;
}
