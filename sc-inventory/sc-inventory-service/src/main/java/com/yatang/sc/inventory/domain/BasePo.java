package com.yatang.sc.inventory.domain;

import java.io.Serializable;

/**
 * @描述: 分页的的基类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/29 下午3:42
 * @版本: v1.0
 */
public class BasePo implements Serializable{

    private static final long serialVersionUID = 7882344823662407196L;
    private Integer				pageNum=1;									// 当前页
    private Integer				pageSize=15;									// 每页显示记录数

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
