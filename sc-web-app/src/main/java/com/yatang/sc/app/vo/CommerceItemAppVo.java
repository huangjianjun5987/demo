package com.yatang.sc.app.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yatang.sc.app.web.jackson.ImageUrlDeserializer;
import com.yatang.sc.app.web.jackson.ImageUrlSerializer;
import com.yatang.sc.common.utils.PricingUtil;
import com.yatang.sc.order.states.CommerceItemTypes;
import com.yatang.sc.purchase.dto.CommerceItemDto;
import com.yatang.sc.purchase.dto.ItemPriceInfoDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO 临时方案
 */
public class CommerceItemAppVo implements Serializable {
    /**
     * 主键id
     */
    private String id;
    /**
     * 产品id
     */
    private String productId;
    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    @JsonSerialize(using = ImageUrlSerializer.class)
    @JsonDeserialize(using = ImageUrlDeserializer.class)
    private String productImg;


    /**
     * 属性(颜色、尺寸等)
     */
    private Map<String, String> properties;

    /**
     *
     */
    private String skuId;

    /**
     * 根类目id
     */
    private String catalogId;

    /**
     * 下单时的数量
     */
    private long quantity;

    /**
     * 购物车商品状态
     */
    private short state;

    /**
     * 状态描述
     */
    private String stateDetail;

    /**
     * 购物车商品级别的价格信息
     */
    private ItemPriceInfoDto itemPrice;

    /**
     * 发生了退换货的数量
     */
    private long returnQuantity;

    /**
     * 是否选中
     */
    private boolean selected;

    /**
     * 购物车商品java对象全路径
     */
    private String type;

    /**
     * 配送的数量
     */
    private long shippedQuantity;

    /**
     * 签收数量
     */
    private long completedQuantity;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     *二级分类
     */
    private String secondLevelCategoryName;

    /**
     *三级分类
     */
    private String thirdLevelCategoryName;

    /**
     * 商品编码
     */
    private String productCode;


    /**
     * 是否支持该地区
     */
    private boolean available = true;

    /**
     * 销售单位的数量 eg:几箱
     */
    private Long saleQuantity ;

    /**
     * 每箱多少瓶  就是销售内装数
     */
    private Integer unitQuantity;

    /**
     * 是否整箱销售：0-否；1-是
     */
    private Integer  sellFullCase;

    private String itemPriceInfo;

    /**
     * 库存是否充足
     */
    private boolean stockEnough;


    /**
     * 是否异常商品
     */
    private Boolean abnormalGoods  = false;

    /**
     * 异常原因
     */
    private String abnormalResonse;

    /**
     * 时间戳
     */
    private long timestamp;


    private List<CommerceItemDto> subItems = new ArrayList<>();

    private String priceModel;

    public String getPriceModel() {
        return priceModel;
    }

    public void setPriceModel(String priceModel) {
        this.priceModel = priceModel;
    }

    public List<CommerceItemDto> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<CommerceItemDto> subItems) {
        this.subItems = subItems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public String getStateDetail() {
        return stateDetail;
    }

    public void setStateDetail(String stateDetail) {
        this.stateDetail = stateDetail;
    }

    public ItemPriceInfoDto getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(ItemPriceInfoDto itemPrice) {
        this.itemPrice = itemPrice;
    }

    public long getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(long returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getShippedQuantity() {
        return shippedQuantity;
    }

    public void setShippedQuantity(long shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
    }

    public long getCompletedQuantity() {
        return completedQuantity;
    }

    public void setCompletedQuantity(long completedQuantity) {
        this.completedQuantity = completedQuantity;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getSecondLevelCategoryName() {
        return secondLevelCategoryName;
    }

    public void setSecondLevelCategoryName(String secondLevelCategoryName) {
        this.secondLevelCategoryName = secondLevelCategoryName;
    }

    public String getThirdLevelCategoryName() {
        return thirdLevelCategoryName;
    }

    public void setThirdLevelCategoryName(String thirdLevelCategoryName) {
        this.thirdLevelCategoryName = thirdLevelCategoryName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Long getSaleQuantity() {
        if (this.saleQuantity == null){
            return 0L;
        }
        return saleQuantity;
    }

    public void setSaleQuantity(Long saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    public Integer getUnitQuantity() {
        if (unitQuantity == null) {
            return 1;
        }
        return unitQuantity;
    }

    public void setUnitQuantity(Integer unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public Integer getSellFullCase() {
        return sellFullCase;
    }

    public void setSellFullCase(Integer sellFullCase) {
        this.sellFullCase = sellFullCase;
    }

    public double getSaleUnitPrice(){
        if(itemPrice == null){
            return 0;
        }
        if(sellFullCase != null && sellFullCase == 1){
            return PricingUtil.roundPrice(itemPrice.getSalePrice()*unitQuantity);
        }
        return itemPrice.getSalePrice();

    }

    public double getListUnitPrice(){
        if(itemPrice == null){
            return 0;
        }
        if(sellFullCase != null && sellFullCase == 1){
            return PricingUtil.roundPrice(itemPrice.getListPrice()*unitQuantity);
        }
        return itemPrice.getListPrice();

    }

    public Boolean getAbnormalGoods() {
        return abnormalGoods;
    }

    public void setAbnormalGoods(Boolean pAbnormalGoods) {
        abnormalGoods = pAbnormalGoods;
    }

    public String getAbnormalResonse() {
        return abnormalResonse;
    }

    public void setAbnormalResonse(String pAbnormalResonse) {
        abnormalResonse = pAbnormalResonse;
    }

    public String getItemPriceInfo() {
        return itemPriceInfo;
    }

    public void setItemPriceInfo(String pItemPriceInfo) {
        itemPriceInfo = pItemPriceInfo;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long pTimestamp) {
        timestamp = pTimestamp;
    }

    public boolean isStockEnough() {
        return stockEnough;
    }

    public void setStockEnough(boolean pStockEnough) {
        stockEnough = pStockEnough;
    }

    public boolean isElectronicItem(){
        if(CommerceItemTypes.COUPON.equals(type)){
            return true;
        }
        return false;
    }
}
