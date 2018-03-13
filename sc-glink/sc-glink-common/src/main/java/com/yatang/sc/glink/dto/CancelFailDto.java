package com.yatang.sc.glink.dto;

import java.io.Serializable;

/**
 * @描述: 取消单据响应失败订单
 * @作者: wangcheng
 * @创建时间:2017年8月2日15:04
 */
public class CancelFailDto  implements Serializable {
    private String orderCode;//订单号
    private String projectCode;//项目编号
    private String errorCode;//错误代码
    private String errorMsg;//错误信息

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

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
