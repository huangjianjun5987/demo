package com.yatang.sc.purchase.dto;



import com.yatang.xc.mbd.biz.prod.dubboservice.dto.InternationalCodeDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;



public class OMSCommerceItemDto implements Serializable{
    private static final long serialVersionUID = -8276032490036392316L;
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
    private OMSItemPriceInfoDto itemPrice;

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

    // 国际码信息
    private List<InternationalCodeDto> internationalCodes;

    /**
     * 可用库存
     */
    private Long availableStock;

    /**
     * 是否异常商品
     */
    private Boolean abnormalGoods  = false;

    /**
     * 异常原因
     */
    private String abnormalResonse;

    /**
     * 每箱多少瓶  就是销售内装数
     */
    private Integer unitQuantity;
    /**
     * 是否整箱销售：0-否；1-是
     */
    private Integer  sellFullCase;

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

    public OMSItemPriceInfoDto getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(OMSItemPriceInfoDto itemPrice) {
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

    public List<InternationalCodeDto> getInternationalCodes() {
        return internationalCodes;
    }

    public void setInternationalCodes(List<InternationalCodeDto> internationalCodes) {
        this.internationalCodes = internationalCodes;
    }

    public Long getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Long availableStock) {
        this.availableStock = availableStock;
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

    public Integer getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(Integer pUnitQuantity) {
        unitQuantity = pUnitQuantity;
    }

    public Integer getSellFullCase() {
        return sellFullCase;
    }

    public void setSellFullCase(Integer pSellFullCase) {
        sellFullCase = pSellFullCase;
    }
}
