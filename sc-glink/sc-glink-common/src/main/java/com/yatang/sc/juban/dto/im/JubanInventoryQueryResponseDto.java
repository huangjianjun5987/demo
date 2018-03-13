package com.yatang.sc.juban.dto.im;

import java.io.Serializable;
import java.util.List;

public class JubanInventoryQueryResponseDto extends com.yatang.sc.xinyi.dto.ResponseDto implements Serializable {
    private static final long serialVersionUID = 8640129640864194551L;
    private List<JubanInventoryQueryItemDto> items;//商品信息

    public List<JubanInventoryQueryItemDto> getItems() {
        return items;
    }

    public void setItems(List<JubanInventoryQueryItemDto> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "InventoryQueryResponseDto{" +
                "items=" + items +
                '}';
    }
}
