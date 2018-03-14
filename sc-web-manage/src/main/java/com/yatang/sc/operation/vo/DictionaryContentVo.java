package com.yatang.sc.operation.vo;

import java.io.Serializable;

import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @描述:数据字典维护的字典内容
 * @类名:DictionaryContentVo
 * @作者: kangdong
 * @创建时间: 2017/6/9 9:59
 * @版本: v1.0
 */
@Getter
@Setter
public class DictionaryContentVo implements Serializable {
	private static final long	serialVersionUID	= -6568313288619900510L;
	@NotNull(groups = { DefaultGroup.class, GroupTwo.class }, message = MessageConstantUtil.NOT_EMPTY)
	private Integer				id;
	@NotNull(groups = { GroupOne.class }, message = MessageConstantUtil.NOT_EMPTY)
	private Integer				dictionaryId;
	@NotEmpty(groups = { GroupOne.class, GroupTwo.class }, message = MessageConstantUtil.NOT_EMPTY)
	private String				contentName;
	private String				contentCode;
	@NotNull(groups = { DefaultGroup.class }, message = MessageConstantUtil.NOT_EMPTY)
	private Integer				state;
}
