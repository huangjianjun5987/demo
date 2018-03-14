package com.yatang.sc.operation.vo;

import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/8/31.
 */
public class FranchiseeConditionVo implements Serializable{

    private static final long serialVersionUID = 4439784179680630044L;
    /**
     * 签收开始日期
     */
    @NotNull(message = "{msg.notEmpty.message}",groups = GroupOne.class)
    private Date completeDateStart;
    /**
     * 签收结束日期
     */
    @NotNull(message = "{msg.notEmpty.message}",groups = GroupOne.class)
    private Date completeDateEnd;
    /**
     * 支付开始日期
     */
//    @NotNull(message = "{msg.notEmpty.message}",groups = GroupTwo.class)
    private Date payDateStart;
    /**
     * 支付结束日期
     */
//    @NotNull(message = "{msg.notEmpty.message}",groups = GroupTwo.class)
    private Date payDateEnd;
    /**
     * 退款开始日期
     */
    private Date refundDateStart;
    /**
     * 退款结束日期
     */
    private Date refundDateEnd;
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
    @Min(1)
    private int page;
    /**
     * 每页容量
     */
    @Min(1)
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

    public FranchiseeConditionVo() {
    }

    public List<String> getBranchCompanyIds() {
        return branchCompanyIds;
    }

    public void setBranchCompanyIds(List<String> branchCompanyIds) {
        this.branchCompanyIds = branchCompanyIds;
    }

    public int getPage() {
        return page;
    }

    public String getIsPayState() {
        return isPayState;
    }

    public void setIsPayState(String isPayState) {
        if(isPayState != null&& isPayState.equals("1")){
            this.isPayState = "YZF";
        }else{
            this.isPayState = null ;
        }

    }

    public String getIsRefound() {
        return isRefound;
    }

    public void setIsRefound(String isRefound) {
        if(isRefound != null&& isRefound.equals("1")){
            this.isRefound = "YTK";
        }else{
            this.isRefound = null;
        }
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

    public Date getCompleteDateStart() {
        return completeDateStart;
    }

    public void setCompleteDateStart(Date completeDateStart) {
        this.completeDateStart = completeDateStart;
    }

    public Date getCompleteDateEnd() {
        return completeDateEnd;
    }

    public void setCompleteDateEnd(Date completeDateEnd) {
        this.completeDateEnd = completeDateEnd;
    }

    public Date getPayDateStart() {
        return payDateStart;
    }

    public void setPayDateStart(Date payDateStart) {
        this.payDateStart = payDateStart;
    }

    public Date getPayDateEnd() {
        return payDateEnd;
    }

    public void setPayDateEnd(Date payDateEnd) {
        this.payDateEnd = payDateEnd;
    }

    public Date getRefundDateStart() {
        return refundDateStart;
    }

    public void setRefundDateStart(Date refundDateStart) {
        this.refundDateStart = refundDateStart;
    }

    public Date getRefundDateEnd() {
        return refundDateEnd;
    }

    public void setRefundDateEnd(Date refundDateEnd) {
        this.refundDateEnd = refundDateEnd;
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
}
