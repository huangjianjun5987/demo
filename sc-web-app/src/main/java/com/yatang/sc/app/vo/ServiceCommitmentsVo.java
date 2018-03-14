package com.yatang.sc.app.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @描述: 服务承诺.
 * @作者: yangshuang
 */
@Setter
@Getter
@ToString
public class ServiceCommitmentsVo implements Serializable {
    private static final long serialVersionUID = 1011848929285314352L;
    private Integer id;//pk

    private String promiseContent;//承诺内容

    private Integer sort;//排序

    private Integer status;//起停用状态:0,禁用,1启用
}