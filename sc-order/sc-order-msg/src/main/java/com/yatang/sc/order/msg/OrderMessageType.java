package com.yatang.sc.order.msg;

import com.busi.common.utils.StringUtils;

/**
 * Created by qiugang on 7/25/2017.
 */
public enum OrderMessageType {
    SubmitOrder("SubmitOrder"),
    AutoApproveOrder("AutoApproveOrder"),
    SendWMSDeliveryMessage("SendWMSDeliveryMessage"),
    SendWMSDeliveryMessageCheck("SendWMSDeliveryMessageCheck"),
    DeliveryElectronicOrder("DeliveryElectronicOrder"),
    ApprovedOrder("ApprovedOrder"),
    CompleteOrder("CompleteOrder"),
    ApprovedOrder_Manual("ApprovedOrderManual"),
    ORDER_PAY_SUCCESS("OrderPaySuccess"),
    ORDER_PAY_SUCCESS_CONFIRM("OrderPaySuccessConfirm"),
    CANCELED_ORDER("CanceledOrder"),
    REFUND_COMPLETED("RefundCompleted"),//退款已完成
    GLINK_ORDER_STATUS_NOFITY("GLinkOrderStatusNotify"),
    SPLIT_ORDER_BY_INVENTORY("SplitOrderByInventory"),
    SPLIT_ORDER_BY_INVENTORY_ONLY_UPDATE_STATE("SplitOrderByInventoryOnlyUpdateState"),
    MANUAL_SPLIT_ORDER("ManualSplitOrder"),
    ORDER_INDEX_MSG("ORDER_INDEX_MSG"),
    KIDD_ORDER_STATUS_NOTIFY("KiddOrderStatusNotify"),
    Reserve_Order_Inventory("reserveOrderInventory"),
    After_Reserved_Order_Inventory("afterReservedOrderInventory"),
    Reserve_Order_Inventory_SPLIT("reserveOrderInventorySplit"),
    KIDD_RETURNED_ORDER_NOTIFY("kiddReturnedOrderNotify"),
    PROVIDER_ORDER_STATUE_NOTIFY("Provider_Order_Status_Notify"),
    UNKNOWN("UNKNOWN");
    ;


    OrderMessageType(String type) {
        this.type = type;
    }

    public boolean equals(OrderMessageType type) {
        return this.type.equals(type.getType());
    }

    private String type;

    public String getType() {
        return this.type;
    }

    public static OrderMessageType parse(String msgVal) {
        if (StringUtils.isEmpty(msgVal)) {
            return UNKNOWN;
        }
        for (OrderMessageType type : OrderMessageType.values()) {
            if (type.type.equalsIgnoreCase(msgVal)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
