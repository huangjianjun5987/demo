package com.yatang.sc.facade.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 404广告修改dto类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/11 10:31
 * @版本: v1.0
 */
@Setter
@Getter
public class AdPlanModifyParamDto implements Serializable {
    private static final long serialVersionUID = 5627717703061683190L;

   private Integer id;//pk
    private Integer status;//状态

    private String modifyUserId;//修改者id

    private Date modifyTime;//修改时间

}
