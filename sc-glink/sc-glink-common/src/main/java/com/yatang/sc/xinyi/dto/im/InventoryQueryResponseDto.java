package com.yatang.sc.xinyi.dto.im;

import java.io.Serializable;
import java.util.List;

public class InventoryQueryResponseDto extends com.yatang.sc.xinyi.dto.ResponseDto implements Serializable {
    private static final long serialVersionUID = 8640129640864194551L;
    private List<InventoryQueryItemDto> items;//商品信息

    public List<InventoryQueryItemDto> getItems() {
        return items;
    }

    public void setItems(List<InventoryQueryItemDto> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "InventoryQueryResponseDto{" +
                "items=" + items +
                '}';
    }
}
