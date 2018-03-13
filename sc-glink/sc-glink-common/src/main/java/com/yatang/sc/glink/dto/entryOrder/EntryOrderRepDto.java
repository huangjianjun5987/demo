package com.yatang.sc.glink.dto.entryOrder;

import java.io.Serializable;
import java.util.List;

/**
 * create by chensiqiang
 * time 2017/8/1 10:46
 * 入库订单查询结果响应DTO
 */
public class EntryOrderRepDto implements Serializable{

    private int total;      //总记录数
    private int pageNo;     //页码
    private int pageSize;   //每页显示记录数
    private List<EntryOrderRepResultDto> result;    //响应结果

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<EntryOrderRepResultDto> getResult() {
        return result;
    }

    public void setResult(List<EntryOrderRepResultDto> result) {
        this.result = result;
    }
}
