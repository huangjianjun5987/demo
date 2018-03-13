package com.yatang.sc.facade.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @描述: 图片信息
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/8 14:26
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class ImageDto implements Serializable {
	private static final long	serialVersionUID	= 3415214542027396904L;
	private String				id;
	private String				url;
	private String				imageType;
	private String				entityId;

	private Integer				sortOrder;
}
