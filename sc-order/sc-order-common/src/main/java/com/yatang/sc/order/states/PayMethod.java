package com.yatang.sc.order.states;

public enum  PayMethod {

    PYT_ONLINE("onlinePayment","在线支付"),
    UNKNOWN("",""),
    ;
    private final String stateValue;
    private final String description;

    PayMethod(String stateValue, String description) {
        this.stateValue = stateValue;
        this.description = description;
    }

    public String getStateValue() {
        return stateValue;
    }

    public String getDescription() {
        return description;
    }

    public static  PayMethod  getStateByValue(String stateValue) {

        for (PayMethod state : PayMethod.values()) {
            if (state.getStateValue().equals(stateValue)) {
                return state;
            }
        }
        return UNKNOWN;
    }
}
