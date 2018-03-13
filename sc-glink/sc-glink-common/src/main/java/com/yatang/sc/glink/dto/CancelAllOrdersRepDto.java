package com.yatang.sc.glink.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 取消单据响应结果DTO
 * @作者: wangcheng
 * @创建时间: 2017年8月2日15:04
 */
public class CancelAllOrdersRepDto implements Serializable {

    private String total;//处理总数
    private List<CancelFailDto> fail;//失败订单
    private List<CancelSuccessDto> success;//成功订单


    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<CancelFailDto> getFail() {
        return fail;
    }

    public void setFail(List<CancelFailDto> fail) {
        this.fail = fail;
    }

    public List<CancelSuccessDto> getSuccess() {
        return success;
    }

    public void setSuccess(List<CancelSuccessDto> success) {
        this.success = success;
    }
}
