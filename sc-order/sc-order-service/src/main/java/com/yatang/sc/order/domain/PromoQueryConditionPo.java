package com.yatang.sc.order.domain;

import com.yatang.sc.common.BasePo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 促销列表查询条件
 * @作者: tankejia
 * @创建时间: 2017/9/4-15:03 .
 * @版本: 1.0 .
 */
public class PromoQueryConditionPo extends BasePo implements Serializable {

    private static final long serialVersionUID = -6760159398966937543L;

    /**
     * 活动ID
     * */
    private String				id;

    /**
     * 名称
     * */
    private String              promotionName;

    /**
     * 分公司id
     * */
    private String              branchCompanyId;

    /**
     * 开始时间
     * */
    private Date                startDate;

    /**
     * 结束时间
     * */
    private Date                endDate;

    /**
     * 状态
     * */
    private String              status;

    /**
     * 促销类型：0为促销，1为优惠券
     */
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String pId) {
        id = pId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String pPromotionName) {
        promotionName = pPromotionName;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String pBranchCompanyId) {
        branchCompanyId = pBranchCompanyId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date pStartDate) {
        startDate = pStartDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date pEndDate) {
        endDate = pEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String pStatus) {
        status = pStatus;
    }
}
