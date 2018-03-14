package com.yatang.sc.operation.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author tangqi
 * @create 2017-11-17 9:58
 * @desc
 **/
@Getter
@Setter
public class QueryHomePageAdVo {

    @NotNull(message = "{msg.notEmpty.message}")
    private String companyId;
    @NotNull(message = "{msg.notEmpty.message}")
    private Integer homePageType;//1:global,0:custom
}
