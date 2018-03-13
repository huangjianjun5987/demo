package com.yatang.sc.xinyi.dto.saleOrder;


import java.io.Serializable;
import java.util.List;

/**
 * 发货单确认dto
 */
public class SaleOrderJYCKConfirmRequestDto implements Serializable{

    private static final long serialVersionUID = -7256313531059772642L;

    private SaleOrderJYCKDeliveryDto deliveryOrder;
    private List<SaleOrderJYCKPackageDto> packages;//物流信息

    private List<SaleOrderConfirmOrderInfoDto> orderLines;//明细信息

    public SaleOrderJYCKDeliveryDto getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(SaleOrderJYCKDeliveryDto deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public List<SaleOrderJYCKPackageDto> getPackages() {
        return packages;
    }

    public void setPackages(List<SaleOrderJYCKPackageDto> packages) {
        this.packages = packages;
    }

    public List<SaleOrderConfirmOrderInfoDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<SaleOrderConfirmOrderInfoDto> orderLines) {
        this.orderLines = orderLines;
    }
}
