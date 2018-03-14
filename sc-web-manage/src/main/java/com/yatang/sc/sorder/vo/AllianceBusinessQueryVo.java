package com.yatang.sc.sorder.vo;

import java.io.Serializable;

/**
 * Created by liusongjie on 2017/8/4.
 */
public class AllianceBusinessQueryVo implements Serializable {

    private static final long serialVersionUID = -7844399091349138314L;

    /**
     *  查询参数，用于模糊匹配加盟商ID和加盟商名字
     */
    private String queryParam;

    /**
     *  当前页
     */
    private String pageNum;

    /**
     *  当前页
     */
    private String pageSize;

    public String getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(String queryParam) {
        this.queryParam = queryParam;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
