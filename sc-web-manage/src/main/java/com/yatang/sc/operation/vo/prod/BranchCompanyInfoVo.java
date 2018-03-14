package com.yatang.sc.operation.vo.prod;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述:
 * @类名:
 * @作者: lvheping
 * @创建时间: 2017/7/24 16:23
 * @版本: v1.0
 */
@Setter
@Getter
public class BranchCompanyInfoVo implements Serializable {
	private String	id;		// 子公司的id
	private String	name;	// 子公司的名字
}
