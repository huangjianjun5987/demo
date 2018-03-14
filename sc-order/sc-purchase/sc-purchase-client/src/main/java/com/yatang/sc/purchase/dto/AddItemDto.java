package com.yatang.sc.purchase.dto;

import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by qiugang on 7/8/2017.
 */
public class AddItemDto implements Serializable {

    private static final long serialVersionUID = -8123927233651625349L;
    private String productId;

    private String skuId;

    private long quantity;

    private Integer unitQuantity;

    private long saleQuantity;

    /**
     * 是否整箱销售：0-否；1-是
     */
    private Integer  sellFullCase ;

    private Map<String, String> attrs;

    private ProductIndexDto product;

    private ProdSellPriceInfoDto sellPriceInfo;

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

    public Integer getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(Integer unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public long getSaleQuantity() {
        return saleQuantity;
    }

    public void setSaleQuantity(long saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    public Integer getSellFullCase() {
        return sellFullCase;
    }

    public void setSellFullCase(Integer sellFullCase) {
        this.sellFullCase = sellFullCase;
    }

    public ProductIndexDto getProduct() {
        return product;
    }

    public void setProduct(ProductIndexDto product) {
        this.product = product;
    }

    public ProdSellPriceInfoDto getSellPriceInfo() {
        return sellPriceInfo;
    }

    public void setSellPriceInfo(ProdSellPriceInfoDto sellPriceInfo) {
        this.sellPriceInfo = sellPriceInfo;
    }
}
