package com.yatang.sc.order.states;

/**
 * Created by qiugang on 7/24/2017.
 */
public enum OrderTotalStates {
    PENDING_PAYMENT((short)10,"待付款"),
    PENDING_PROCESS((short)20,"已支付"),
    PENDING_DELIVERY((short)30,"待配送"),
    PENDING_RECEIVING((short)40,"待收货"),
    COMPLETED((short)50,"已完成"),
    CANCELLED((short)60,"已取消"),
    PENDING_REFUND((short)70,"退款中"),
    REFUND_SUCCESS((short)80,"已退款"),
    SPLIT_ORDER((short)90,"已拆单"),
    UNKNOWN((short)0,"未知状态"),
    ;
    private final short stateValue;
    private final String description;

    OrderTotalStates(short stateValue, String description) {
        this.stateValue = stateValue;
        this.description = description;
    }

    public short getStateValue() {
        return stateValue;
    }

    public String getDescription() {
        return description;
    }

    public static  OrderTotalStates  getStateByValue(short stateValue) {

        for (OrderTotalStates state : OrderTotalStates.values()) {
            if (state.getStateValue() == stateValue) {
                return state;
            }
        }
        return UNKNOWN;
    }
}
