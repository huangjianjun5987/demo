package com.yatang.sc.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/8/31.
 */
public class FranchiseeConditionDto implements Serializable{

    private static final long serialVersionUID = 7168174036981998140L;
    /**
     * 签收开始日期
     */
    private String completeDateStart;
    /**
     * 签收结束日期
     */
    private String completeDateEnd;
    /**
     * 支付开始日期
     */
    private String payDateStart;
    /**
     * 支付结束日期
     */
    private String payDateEnd;
    /**
     * 退款开始日期
     */
    private String refundDateStart;
    /**
     * 退款结束日期
     */
    private String refundDateEnd;
    /**
     * 加盟商id
     */
    private String franchiseeId;
    /**
     * 子公司id
     */
    private String branchCompanyId;
    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 页码
     */
    private int page;
    /**
     * 每页容量
     */
    private int pageSize;
    /**
     * 已支付
     */
    private String isPayState;
    /**
     * 已退款
     */
    private String isRefound;

    private List<String> branchCompanyIds;

    public FranchiseeConditionDto() {
    }


    public List<String> getBranchCompanyIds() {
        return branchCompanyIds;
    }

    public void setBranchCompanyIds(List<String> branchCompanyIds) {
        this.branchCompanyIds = branchCompanyIds;
    }

    public String getIsPayState() {
        return isPayState;
    }

    public void setIsPayState(String isPayState) {
        this.isPayState = isPayState;
    }

    public String getIsRefound() {
        return isRefound;
    }

    public void setIsRefound(String isRefound) {
        this.isRefound = isRefound;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getFranchiseeId() {
        return franchiseeId;
    }

    public void setFranchiseeId(String franchiseeId) {
        this.franchiseeId = franchiseeId;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCompleteDateStart() {
        return completeDateStart;
    }

    public void setCompleteDateStart(String completeDateStart) {
        this.completeDateStart = completeDateStart;
    }

    public String getCompleteDateEnd() {
        return completeDateEnd;
    }

    public void setCompleteDateEnd(String completeDateEnd) {
        this.completeDateEnd = completeDateEnd;
    }

    public String getPayDateStart() {
        return payDateStart;
    }

    public void setPayDateStart(String payDateStart) {
        this.payDateStart = payDateStart;
    }

    public String getPayDateEnd() {
        return payDateEnd;
    }

    public void setPayDateEnd(String payDateEnd) {
        this.payDateEnd = payDateEnd;
    }

    public String getRefundDateStart() {
        return refundDateStart;
    }

    public void setRefundDateStart(String refundDateStart) {
        this.refundDateStart = refundDateStart;
    }

    public String getRefundDateEnd() {
        return refundDateEnd;
    }

    public void setRefundDateEnd(String refundDateEnd) {
        this.refundDateEnd = refundDateEnd;
    }
}
