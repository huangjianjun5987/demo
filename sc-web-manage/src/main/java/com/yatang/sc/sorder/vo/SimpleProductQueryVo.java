package com.yatang.sc.sorder.vo;

import java.io.Serializable;

public class SimpleProductQueryVo implements Serializable {

    private static final long serialVersionUID = -8218737499782211878L;
    /** 查询关键字：商品编号和商品名称 */
    private String	teamText;

    /** 采购信息（采购关系）：经营子公司id-状态 */
    private String	supplierInfo;

    //传拼装好的分公司id-1
    private String salesInfo;

    private int		pageNum		= 1;
    private int		pageSize	= 10;

    public String getTeamText() {
        return teamText;
    }

    public void setTeamText(String teamText) {
        this.teamText = teamText;
    }

    public String getSupplierInfo() {
        return supplierInfo;
    }

    public void setSupplierInfo(String supplierInfo) {
        this.supplierInfo = supplierInfo;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSalesInfo() {
        return salesInfo;
    }

    public void setSalesInfo(String salesInfo) {
        this.salesInfo = salesInfo;
    }
}
