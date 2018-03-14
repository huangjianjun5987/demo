package com.yatang.sc.operation.vo;

import java.io.Serializable;
import java.util.Date;

import com.yatang.sc.operation.common.BaseVo;

import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @描述:
 * @类名:
 * @作者: kangdong
 * @创建时间: 2017/6/8 15:44
 * @版本: v1.0
 */
@Getter
@Setter
public class DictionaryQueryVo extends BaseVo implements Serializable {
	private static final long	serialVersionUID	= 5704794367610181935L;
	private Integer				id;											// 主键ID
	@NotBlank(message = MessageConstantUtil.NOT_EMPTY)
	private String				dictionary;								// 字典单位名称
	@NotBlank(message = MessageConstantUtil.NOT_EMPTY)
	private String				code;										// 字典编码
	private String				remark;										// 说明
	private String				keyword;									// 关键字
}
