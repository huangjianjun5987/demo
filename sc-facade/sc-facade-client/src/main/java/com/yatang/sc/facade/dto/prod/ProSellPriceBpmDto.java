package com.yatang.sc.facade.dto.prod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @描述:销售流程所需字段封装类
 * @类名:ProSellPriceBpmDto
 * @作者: lvheping
 * @创建时间: 2017/12/26 16:56
 * @版本: v1.0
 */

public class ProSellPriceBpmDto implements Serializable {
    private static final long serialVersionUID = 2031461940096638199L;
    private Long						id;							// 销售区间价id

    private String						productId;					// 商品id

    private Date						modifyTime;				// 变更时间

    private String						branchCompanyId;				// 分公司的id

    private String						branchCompanyName;				// 分公司名称

	private String				productCode;								// 商品编码
    
	private String				productName;								// 商品名称

    private Integer						auditStatus;				// 审核状态:1:已提交;2:已审核;3:已拒绝

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getBranchCompanyName() {
        return branchCompanyName;
    }

    public void setBranchCompanyName(String branchCompanyName) {
        this.branchCompanyName = branchCompanyName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Override
    public String toString() {
        return "ProSellPriceBpmDto{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", modifyTime=" + modifyTime +
                ", branchCompanyId='" + branchCompanyId + '\'' +
                ", branchCompanyName='" + branchCompanyName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", auditStatus=" + auditStatus +
                '}';
    }
}
