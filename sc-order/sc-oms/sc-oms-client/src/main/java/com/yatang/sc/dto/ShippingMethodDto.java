package com.yatang.sc.dto;

import java.io.Serializable;

/**
 * Created by xiangyonghong on 2017/7/31.
 */
public class ShippingMethodDto implements Serializable{

    private String id;
    private String name;

    public ShippingMethodDto() {
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
}
