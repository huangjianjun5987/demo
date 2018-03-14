package com.yatang.sc.fulfillment.dto;

import java.io.Serializable;

/**
 * @描述: 订单退换货操作类型Dto
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/20 11:11
 * @版本: v1.0
 */
public class OrderReturnOperateMessageDto implements Serializable {
    private static final long serialVersionUID = -825672355476509728L;

    private String returnId;//退货单id
    private Short operateType;// 保存,取消,关闭

    private String remark;//备注
    private String userId;//当前用户id


    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public Short getOperateType() {
        return operateType;
    }

    public void setOperateType(Short operateType) {
        this.operateType = operateType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
