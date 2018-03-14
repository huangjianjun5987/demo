package com.yatang.sc.app.vo.orderreturned;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * @描述: 订单退换货操作类型Vo
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/20 11:11
 * @版本: v1.0
 */
public class OrderReturnOperateMessageVo implements Serializable {
    private static final long serialVersionUID = -825672355476509728L;

    @NotBlank(message = "{msg.notEmpty.message}")
    private String returnId;//退货单id

    @Range(message = "{msg.notEmpty.message}", min = 1, max = 4)
    private Short operateType;// 保存,取消,关闭

    private String remark;//备注


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
}
