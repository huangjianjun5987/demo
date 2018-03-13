package com.yatang.sc.facade.mongo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangqi on 2018/1/10 15:55.
 * 查询对象
 */
public class CommonQueryRequest {

    private String queryJson;

    private Integer pageNum = 1;

    private Integer pageSize = 20;

    private Map<String, String> sortParam = new HashMap<>();

    public Map<String, String> getSortParam() {
        return sortParam;
    }

    public void setSortParam(Map<String, String> sortParam) {
        this.sortParam = sortParam;
    }

    public String getQueryJson() {
        return queryJson;
    }

    public void setQueryJson(String queryJson) {
        this.queryJson = queryJson;
    }

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

    @Override
    public String toString() {
        return "CommonQueryRequest{" +
                "queryJson='" + queryJson + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", sortParam=" + sortParam +
                '}';
    }
}
