package com.yatang.sc.app.vo.orderreturned;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/11/7 9:22
 * @版本: v1.0
 */

public class OrderReturnRequestItemVo implements Serializable {

    private static final long serialVersionUID = 8496702986985729076L;
    @Min(value = 1, message = "{msg.min.message}")
    @NotNull(message = "{msg.notEmpty.message}")
    private Long returnQuantity;//发生了退换货的数量

    @NotBlank(message = "{msg.notEmpty.message}")
    private String productId;//产品id

    public Long getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(Long returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
