package com.yatang.sc.sorder.vo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/31.
 */
public class WebQueryOrderConditionVo implements Serializable{

    /**
     * 订单编号
     */
    private String id;

    /**
     * 父订单编号
     */
    private String createdByOrderId;

    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 订单状态
     */
    private String orderState;
    /**
     * 支付状态
     */
    private String paymentState;
    /**
     * 物流状态
     */
    private String shippingState;
    /**
     * 加盟商ID
     */
    private String franchiseeId;
    private String branchCompanyId;
    /**
     * 所属子公司
     */
    private List<String> branchCompanyIds;
    /**
     * 收货人电话
     */
    private String cellphone;
    /**
     * 订单提交开始时间
     */
    private String submitStartTime;
    /**
     * 订单提交结束时间
     */
    private String submitEndTime;

    /**
     * 电商订单编号
     */
    private String thirdPartOrderNo;

    /**
     * 页码
     */
    @NotNull
    private int page;
    /**
     * 每页容量
     */
    @NotNull
    private int pageSize;

    private String productName;//订单包含的商品名称
    private String productId;//订单包含的商品productId
    private String franchiseeStoreId;//门店id
    private int containParent;//是否包含父订单
    private String transNum;//参考号/交易号

    /********************************供应商直送业务新增的参数 yinyuxin *****************************/

    private String shippingModes;//配送模式 unified:统配 provider:直送
    private String spName;//配送方名称

    /******************************************************************************************/

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum;
    }

    public String getFranchiseeStoreId() {
        return franchiseeStoreId;
    }

    public void setFranchiseeStoreId(String franchiseeStoreId) {
        this.franchiseeStoreId = franchiseeStoreId;
    }

    public int getContainParent() {
        return containParent;
    }

    public void setContainParent(int containParent) {
        this.containParent = containParent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedByOrderId() {
        return createdByOrderId;
    }

    public void setCreatedByOrderId(String pCreatedByOrderId) {
        createdByOrderId = pCreatedByOrderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    public String getFranchiseeId() {
        return franchiseeId;
    }

    public void setFranchiseeId(String franchiseeId) {
        this.franchiseeId = franchiseeId;
    }

    public List<String> getBranchCompanyIds() {
        return branchCompanyIds;
    }

    public void setBranchCompanyIds(List<String> branchCompanyIds) {
        this.branchCompanyIds = branchCompanyIds;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getSubmitStartTime() {
        return submitStartTime;
    }

    public void setSubmitStartTime(String submitStartTime) {
        this.submitStartTime = submitStartTime;
    }

    public String getSubmitEndTime() {
        return submitEndTime;
    }

    public void setSubmitEndTime(String submitEndTime) {
        this.submitEndTime = submitEndTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getThirdPartOrderNo() {
        return thirdPartOrderNo;
    }

    public void setThirdPartOrderNo(String thirdPartOrderNo) {
        this.thirdPartOrderNo = thirdPartOrderNo;
    }

    public String getShippingModes() {
        return shippingModes;
    }

    public void setShippingModes(String shippingModes) {
        this.shippingModes = shippingModes;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }
}
