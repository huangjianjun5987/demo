package com.yatang.sc.dto;

import com.yatang.sc.common.BaseDto;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 促销列表查询条件
 * @作者: tankejia
 * @创建时间: 2017/9/4-15:03 .
 * @版本: 1.0 .
 */
public class PromoQueryConditionDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -5302753402738015879L;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
