package com.yatang.sc.juban.dto.saleOrder;


import java.io.Serializable;
import java.util.List;

import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderConfirmOrderInfoDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderJYCKDeliveryDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderJYCKPackageDto;

/**
 * 发货单确认dto
 */
public class JubanSaleOrderJYCKConfirmRequestDto implements Serializable{

    private static final long serialVersionUID = -7256313531059772642L;

    private JubanSaleOrderJYCKDeliveryDto deliveryOrder;
    private List<JubanSaleOrderJYCKPackageDto> packages;//物流信息

    private List<JubanSaleOrderConfirmOrderInfoDto> orderLines;//明细信息

    public JubanSaleOrderJYCKDeliveryDto getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(JubanSaleOrderJYCKDeliveryDto deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public List<JubanSaleOrderJYCKPackageDto> getPackages() {
        return packages;
    }

    public void setPackages(List<JubanSaleOrderJYCKPackageDto> packages) {
        this.packages = packages;
    }

    public List<JubanSaleOrderConfirmOrderInfoDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<JubanSaleOrderConfirmOrderInfoDto> orderLines) {
        this.orderLines = orderLines;
    }
}
