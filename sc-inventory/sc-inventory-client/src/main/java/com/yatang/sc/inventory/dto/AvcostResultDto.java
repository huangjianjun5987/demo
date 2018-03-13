package com.yatang.sc.inventory.dto;

import java.io.Serializable;

public class AvcostResultDto implements Serializable{

    private static final long serialVersionUID = -6895842566336620850L;
    private String itemId;

    private String loc;

    private Double avCost;

    private Double unitCost;

    public Double getAvCost() {
        return avCost;
    }

    public void setAvCost(Double avCost) {
        this.avCost = avCost;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

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
