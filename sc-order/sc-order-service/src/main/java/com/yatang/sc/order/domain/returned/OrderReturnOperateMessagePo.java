package com.yatang.sc.order.domain.returned;

import java.io.Serializable;

/**
 * @描述: 退换货单操作po
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/20 13:38
 * @版本: v1.0
 */
public class OrderReturnOperateMessagePo implements Serializable {

    private static final long serialVersionUID = -8589314065159083273L;
    private String returnId;//退货单id
    private Short operateType;// 保存,取消,关闭

    private String remark;//备注

    private String userId;//操作人员id

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
