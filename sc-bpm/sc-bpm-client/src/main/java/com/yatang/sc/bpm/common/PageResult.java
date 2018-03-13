package com.yatang.sc.bpm.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author tangqi
 * @create 2017-11-30 9:13
 * @desc 分页
 **/
@Getter
@Setter
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 5453532156533306416L;
    //页码
    private Integer pageNum;
    //数量
    private Integer pageSize;
    //总数
    private Long total;
    //结果集
    private List<T> data;
}
