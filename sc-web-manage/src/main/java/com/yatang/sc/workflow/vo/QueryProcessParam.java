package com.yatang.sc.workflow.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author tangqi
 * @create 2017-12-04 13:59
 * @desc 查询流程参数对象
 **/
@Getter
@Setter
public class QueryProcessParam {
    @NotNull(message = "{msg.notEmpty.message}")
    private String processType;
    private Map<String, Object> map;
}
