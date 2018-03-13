package com.yatang.sc.facade.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述:
 * @类名:
 * @作者: xiangyonghong
 * @创建时间: 2017/6/9 20:57
 * @版本: v1.0
 */
@Data
public class CategoryIconDto implements Serializable {

	private static final long	serialVersionUID	= 112888863261472566L;
	private String				id;

	private String				iconUrl;

	private Date				createTime;

}
