package com.yatang.sc.order.split;

import com.yatang.sc.order.split.enums.SubOrderType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SplitOrderSubGroupDto implements Serializable {
    private List<SplitOrderItemDto> items;
    private SubOrderType subOrderType;
    private String prodPlaceId;//商品地点关系ID

    public static SplitOrderSubGroupDto newInstance(SubOrderType subOrderType) {
        SplitOrderSubGroupDto splitOrderSubGroupDto = new SplitOrderSubGroupDto(subOrderType);
        return splitOrderSubGroupDto;
    }

    public static SplitOrderSubGroupDto newInstance(SubOrderType subOrderType, String prodPlaceId) {
        SplitOrderSubGroupDto splitOrderSubGroupDto = new SplitOrderSubGroupDto(subOrderType, prodPlaceId);
        return splitOrderSubGroupDto;
    }

    public SplitOrderSubGroupDto() {
        items = new ArrayList<>();
        this.subOrderType = SubOrderType.YT_SHIPPING;
    }

    public SplitOrderSubGroupDto(SubOrderType subOrderType, String prodPlaceId) {
        items = new ArrayList<>();
        this.subOrderType = subOrderType;
        this.prodPlaceId = prodPlaceId;
    }

    public SplitOrderSubGroupDto(SubOrderType subOrderType) {
        items = new ArrayList<>();
        this.subOrderType = subOrderType;
    }

    public List<SplitOrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<SplitOrderItemDto> pItems) {
        items = pItems;
    }

    public SubOrderType getSubOrderType() {
        return subOrderType;
    }

    public void setSubOrderType(SubOrderType pSubOrderType) {
        subOrderType = pSubOrderType;
    }

    public String getProdPlaceId() {
        return prodPlaceId;
    }

    public void setProdPlaceId(String pProdPlaceId) {
        prodPlaceId = pProdPlaceId;
    }
}
