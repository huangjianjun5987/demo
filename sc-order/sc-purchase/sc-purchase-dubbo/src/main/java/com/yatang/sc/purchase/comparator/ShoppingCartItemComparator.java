package com.yatang.sc.purchase.comparator;

import com.yatang.sc.purchase.dto.CommerceItemDto;

import java.util.Comparator;

public  class ShoppingCartItemComparator implements Comparator<CommerceItemDto> {

    @Override
    public int compare(CommerceItemDto o1, CommerceItemDto o2) {
        if (o1 == null || o2 == null) {
            return -1;
        }
        if (o1.getTimestamp() == o2.getTimestamp()) {
            return 0;
        } else if (o1.getTimestamp() > o2.getTimestamp()) {
            return -1;
        }
        return 1;
    }
}