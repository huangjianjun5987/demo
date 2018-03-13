package com.yatang.sc.inventory.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class BathCheckInventoryDto implements Serializable {

    private String loc;
    private Set<String> enoughStock;
    private Set<String> outOfStock;

    public String getLoc() {
        return loc;
    }

    public void setLoc(String pLoc) {
        loc = pLoc;
    }

    public Set<String> getEnoughStock() {
        if (enoughStock == null) {
            enoughStock = new HashSet<>();
        }
        return enoughStock;
    }

    public void setEnoughStock(Set<String> pEnoughStock) {
        enoughStock = pEnoughStock;
    }

    public Set<String> getOutOfStock() {
        if (outOfStock == null) {
            outOfStock = new HashSet<>();
        }
        return outOfStock;
    }

    public void setOutOfStock(Set<String> pOutOfStock) {
        outOfStock = pOutOfStock;
    }
}
