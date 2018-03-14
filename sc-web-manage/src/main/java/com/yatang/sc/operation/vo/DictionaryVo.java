package com.yatang.sc.operation.vo;

import java.io.Serializable;

import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @描述:
 * @类名:
 * @作者: kangdong
 * @创建时间: 2017/6/8 15:44
 * @版本: v1.0
 */
@Getter
@Setter
public class DictionaryVo implements Serializable {
    private static final long serialVersionUID = 5409872434287010108L;
    @NotNull(groups = {GroupTwo.class}, message = MessageConstantUtil.NOT_EMPTY)
    private Integer	id;			// 主键ID
    @NotEmpty(groups = {GroupOne.class, GroupTwo.class}, message = MessageConstantUtil.NOT_EMPTY)
    private String	dictionary;	// 字典单位名称
    @NotEmpty(groups = {GroupOne.class, GroupTwo.class}, message = MessageConstantUtil.NOT_EMPTY)
    private String	code;		// 字典编码
    private String	remark;		// 说明
}
