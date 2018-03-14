package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 心愿单清单po
 */
public class WishListPo implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 分公司编码
     */
    private String branchCompanyId;

    /**
     * 分公司名字
     */
    private String branchCompanyName;

    /**
     * 商品国标码
     */
    private String gbCode;

    /**
     * 商品编码
     */
    private String productCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 总预订量
     */
    private Long totalQuantity;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 完结时间
     */
    private Date completeTime;

    /**
     * 状态，init:待处理， complete:已完成， close：关闭
     */
    private String status;

    /**
     * 商品图片
     */
    private String productImg;

    /**
     * sc_wish_list
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId == null ? null : branchCompanyId.trim();
    }

    public String getGbCode() {
        return gbCode;
    }

    public void setGbCode(String gbCode) {
        this.gbCode = gbCode == null ? null : gbCode.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getBranchCompanyName() {
        return branchCompanyName;
    }

    public void setBranchCompanyName(String branchCompanyName) {
        this.branchCompanyName = branchCompanyName;
    }
}