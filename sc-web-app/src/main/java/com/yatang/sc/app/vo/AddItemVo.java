package com.yatang.sc.app.vo;

import com.yatang.sc.app.web.validgroup.DefaultGroup;
import com.yatang.sc.app.web.validgroup.GroupOne;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by qiugang on 7/8/2017.
 */

public class AddItemVo implements Serializable {

    private static final long serialVersionUID = -8865796256714419076L;

    private String productId;

    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private String skuId;

    private long quantity;

    private Map<String, String> attrs;

    /**
     * 是否整箱销售：0-否；1-是
     */
    private Integer  sellFullCase = 0 ;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Map<String, String> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, String> attrs) {
        this.attrs = attrs;
    }

    public Integer getSellFullCase() {
        return sellFullCase;
    }

    public void setSellFullCase(Integer sellFullCase) {
        if (sellFullCase == null){
            sellFullCase = 0;
        }
        this.sellFullCase = sellFullCase;
    }
}
