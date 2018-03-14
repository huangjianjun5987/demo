package com.yatang.sc.order.domain;

import java.io.Serializable;

public class OrderDictionary implements Serializable {
    private String propertyName;

    private String propertyValue;

    private static final long serialVersionUID = 1L;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName == null ? null : propertyName.trim();
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue == null ? null : propertyValue.trim();
    }
}