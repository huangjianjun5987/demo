package com.yatang.sc.operation.vo;

import java.io.Serializable;
import java.util.List;

import com.yatang.sc.validgroup.GroupOne;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @描述: 接收批量修改快捷导航状态参数
 * @作者: tankejia
 * @创建时间: 2017/7/5-10:29 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class EditQuickNavigationStatusVo implements Serializable {

    private static final long serialVersionUID = 2440108728150198409L;

    /**
     * 待修改的id
     * */
    @NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
    private List<Integer> ids;

    /**
     * 状态
     * */
    @NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
    private Integer status;

}
