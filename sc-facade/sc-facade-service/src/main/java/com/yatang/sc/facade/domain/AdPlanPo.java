package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 404广告方案
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/6/8 10:04
 * @版本: v1.0
 */
@Getter
@Setter
public class AdPlanPo implements Serializable {
	private static final long	serialVersionUID	= 4178573908540677872L;
	private Integer				id;										// 主键

	private String				planName;									// 方案名

	private String				picName1;									// 图片1名称

	private String				picUrl1;									// 图片1保存路径

	private String				linkUrl1;									// 链接地址1

	private String				picName2;									// 图片2名称

	private String				linkUrl2;									// //链接地址2

	private String				picUrl2;									// 图片2保存路径

	private Date				createTime;									// 创建时间

	private Integer				status;										// 启用状态(1:启用,0:停用)


	private Date modifyTime;//修改时间

	private String modifyUserId;//修改者id

	private String createUserId;//创建者id

}