package com.yatang.sc.glink.dto;

import java.io.Serializable;

/**
 * @描述: 取消单据响应成功订单
 * @作者: wangcheng
 * @创建时间:2017年08月02日15:07
 */
public class CancelSuccessDto implements Serializable{
    private String orderCode;//订单号
    private String projectCode;//项目编号

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
}
