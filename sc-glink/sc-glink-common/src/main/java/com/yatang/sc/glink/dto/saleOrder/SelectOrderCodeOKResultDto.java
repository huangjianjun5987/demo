package com.yatang.sc.glink.dto.saleOrder;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 获取订单确认结果返回结果DTO
 * @作者: wangcheng
 * @创建时间: 2017年7月31日20:50:50
 */
public class SelectOrderCodeOKResultDto implements Serializable{
    private int total;//总记录数
    private int pageNo;//当前页号
    private int pageSize;//每页记录数
    private List<SaleOrderRepDto> result;//当前页数据

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

    public List<SaleOrderRepDto> getResult() {
        return result;
    }

    public void setResult(List<SaleOrderRepDto> result) {
        this.result = result;
    }
}
