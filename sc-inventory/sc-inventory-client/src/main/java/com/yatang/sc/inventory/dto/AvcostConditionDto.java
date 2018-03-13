package com.yatang.sc.inventory.dto;

import java.io.Serializable;

public class AvcostConditionDto implements Serializable{
    private static final long serialVersionUID = 2284494406777080931L;

    private String itemId;

    private String loc;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
