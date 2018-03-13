package com.yatang.sc.facade.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdSellInfoLogPo implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 子公司id
     */
    private String branchCompanyId;

    /**
     * 商品Id
     */
    private String productId;

    /**
     * 最低价
     */
    private BigDecimal lowestPrice;

    /**
     * 操作
     */
    private String operate;

    /**
     * 操作时间
     */
    private Date operateDate;

    /**
     * 操作用户id
     */
    private String operateUserId;

    /**
     * 操作用户名
     */
    private String operateUserName;

    /**
     * prod_sell_info_log
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
        this.branchCompanyId = branchCompanyId;
    }



    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate == null ? null : operate.trim();
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(String operateUserId) {
        this.operateUserId = operateUserId == null ? null : operateUserId.trim();
    }

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName == null ? null : operateUserName.trim();
    }
}