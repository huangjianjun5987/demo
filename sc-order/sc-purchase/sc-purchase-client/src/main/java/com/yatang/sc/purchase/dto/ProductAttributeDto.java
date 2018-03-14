package com.yatang.sc.purchase.dto;

import java.io.Serializable;

/**
 * Created by xiangyonghong on 2017/8/8.
 */
public class ProductAttributeDto implements Serializable{

    private static final long serialVersionUID = 8712492820697817689L;
    private String id;
    private String name;
    private String value;

    public ProductAttributeDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
