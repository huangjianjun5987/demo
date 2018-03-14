package com.yatang.sc.operation.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @描述: 校验的营业执照和组织机构码Vo
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/6/14 10:32
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class LicenseNoAndOrgCodeVo implements Serializable {
    private static final long serialVersionUID = 3544831683996252262L;
    private String registLicenceNo;//营业执照号
    private String orgCode;//组织机构代码

    private String licenceId;//营业执照的id
    private String orgCodeId;//组织机构代码的id

}
