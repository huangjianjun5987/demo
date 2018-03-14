package com.yatang.sc.operation.common;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @描述:接收分页的参数
 * @类名:BaseVo
 * @作者: lvheping
 * @创建时间: 2017/6/12 11:35
 * @版本: v1.0
 */
@Setter
@Getter
public class BaseVo implements Serializable {
	private static final long	serialVersionUID	= -2103438815299432990L;

	@NotNull(message = "{msg.notEmpty.message}")
	@Min(value = 1,message = "{msg.min.message}")
	private Integer				pageNum=1;									// 当前页

	@NotNull(message = "{msg.notEmpty.message}")
	@Min(value = 1,message = "{msg.min.message}")
	private Integer				pageSize=15;									// 每页显示记录数
}
