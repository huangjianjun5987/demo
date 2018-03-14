package com.yatang.sc.app.vo;

import java.io.Serializable;

public class CartCountVo implements Serializable {

    private static final long serialVersionUID = -4089174687053954539L;

    private Long cartCount;

    public Long getCartCount() {
        return cartCount;
    }

    public void setCartCount(Long cartCount) {
        this.cartCount = cartCount;
    }
}
