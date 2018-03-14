package com.yatang.sc.purchase.dto;

import com.yatang.sc.order.states.CommerceItemTypes;
import com.yatang.sc.order.states.OrderFrom;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDto implements Serializable{

    private static final long serialVersionUID = -7778254400384284384L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 用户id
     */
    private String profileId;

    /**
     * 订单类型
     */
    private String orderType;
    private String orderTypeDesc;// 订单类型描述
    private short returnState;
    private String returnStateDesc;//退换货状态描述

    /**
     * 分公司ID
     */
    private String branchCompanyId;

    /**
     * 出货仓
     */
    private String branchCompanyArehouse;

    /**
     * 加盟商ID
     */
    private String franchiseeId;

    /**
     * 加盟店ID
     */
    private String franchiseeStoreId;

    /**
     * 购物车状态
     */
    private Short state;

    /**
     * 购物车状态描述
     */
    private String stateDetail;

    /**
     * 订单类型
     */
    private String orderState;

    /**
     * 配送状态
     */
    private String shippingState;

    /**
     * 支付状态
     */
    private String paymentState;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     *  完成时间
     */
    private Date completedTime;

    /**
     * 最后一次修改的时间
     */
    private Date lastModifiedTime;

    /**
     * 购物车价格信息
     */
    private OrderPriceInfoDto priceInfoDto;

    /**
     * 当前购物车附属的主购物车id
     */
    private String createdByOrderId;


    /**
     * 销售渠道（如：主站、团购、抢购）
     */
    private String salesChannel;

    /**
     * 客服id号（电话中心坐席id）
     */
    private String agentId;


    /**
     * 购物车提交时所属站点
     */
    private String siteId;


    /**
     * 购物车的描述
     */
    private String description;

    /**
     * 购物车java对象版本号
     */
    private String version;

    /**
     * 第三方订单号
     */
    private String thirdPartOrderNo;



    /**
     * 购物车商品
     */
    private List<CommerceItemDto> items;
    
    /**
     * 购物车的发票信息
     */
    private InvoiceInfoDto invoiceInfoDto;

    /**
     * 配送信息
     */
    private ShippingGroupDto shippingGroupDto;
    /**
     * 购物车支付方式列表
     */
    private List<PaymentGroupDto> paymentGroupDtos;

    private Integer countOfItem;


    private List<String> couponActivities;
    
    private boolean hasNotSuperposedPromotion;
    
    private boolean useCoupon;

    private String from = OrderFrom.DEFAULT.getValue();//订单来源
    
    public boolean isHasNotSuperposedPromotion() {
		return hasNotSuperposedPromotion;
	}

	public void setHasNotSuperposedPromotion(boolean hasNotSuperposedPromotion) {
		this.hasNotSuperposedPromotion = hasNotSuperposedPromotion;
	}
	
	public boolean isUseCoupon() {
		return useCoupon;
	}

	public void setUseCoupon(boolean useCoupon) {
		this.useCoupon = useCoupon;
	}

	public String getOrderTypeDesc() {
        return orderTypeDesc;
    }

    public void setOrderTypeDesc(String orderTypeDesc) {
        this.orderTypeDesc = orderTypeDesc;
    }

    public short getReturnState() {
        return returnState;
    }

    public void setReturnState(short returnState) {
        this.returnState = returnState;
    }

    public String getReturnStateDesc() {
        return returnStateDesc;
    }

    public void setReturnStateDesc(String returnStateDesc) {
        this.returnStateDesc = returnStateDesc;
    }

    public OrderDto(){
        this.items = new ArrayList<CommerceItemDto>();
        this.paymentGroupDtos = new ArrayList<PaymentGroupDto>();
        this.couponActivities = new ArrayList<String>();
    }

    /**
     * 根据SKUID 查找commerceItem
     * @param skuId
     * @return
     */
    public CommerceItemDto  getCommerceItemBySkuId(String skuId){
        List<CommerceItemDto> commerceItems = getItems();
        if(commerceItems == null || commerceItems.size() == 0){
            return null;
        }
        CommerceItemDto result = null;
        for (CommerceItemDto item : commerceItems){
            if(item.getSkuId().equals(skuId) && !(CommerceItemTypes.PROMOTION.equals(item.getType()))){
                result = item;
                break;
            }
        }
        return result;
    }


    public List<CommerceItemDto>  getAllCommerceItemsBySkuId(String skuId){
        List<CommerceItemDto> commerceItems = getItems();
        if(commerceItems == null || commerceItems.size() == 0){
            return null;
        }
        List<CommerceItemDto> result = new ArrayList<CommerceItemDto>();
        for (CommerceItemDto item : commerceItems){
            if(item.getSkuId().equals(skuId)){
                result.add(item);
            }
        }
        return result;
    }

    public long getTotalQuantityBySkuId(String skuId){
        List<CommerceItemDto> commerceItems = getItems();
        if(commerceItems == null || commerceItems.size() == 0){
            return 0L;
        }
        long result = 0;
        for (CommerceItemDto item : commerceItems){
            if(skuId.equals(item.getSkuId())){
                result += item.getQuantity();
            }
        }
        return result;
    }

    public long getSelectedCommerceItemQty(){
        List<CommerceItemDto> commerceItems = getItems();
        long selectQty = 0;
        if(commerceItems == null || commerceItems.size() == 0){
            return selectQty;
        }

        for (CommerceItemDto item : commerceItems){
            if(CommerceItemTypes.PROMOTION.equals(item.getType())){
                continue;
            }
            if(item.isSelected()){
                selectQty += item.getSaleQuantity();
            }
        }
        return selectQty;
    }


    public long getTotalCommerceItemQty(){
        List<CommerceItemDto> commerceItems = getItems();
        long qty = 0;
        if(commerceItems == null || commerceItems.size() == 0){
            return qty;
        }

        for (CommerceItemDto item : commerceItems){
            if(CommerceItemTypes.PROMOTION.equals(item.getType())){
                continue;
            }
            qty += item.getSaleQuantity();
        }
        return qty;
    }

    public long  getItemListCount(){
        List<CommerceItemDto> commerceItems = getItems();
        long count= 0;
        if(commerceItems == null || commerceItems.size() == 0){
            return count;
        }
        for (CommerceItemDto commerceItemDto:commerceItems){
            //赠品不算入最大商品数量
            if(CommerceItemTypes.PROMOTION.equals(commerceItemDto.getType())){
                continue;
            }
            count++;
        }
//        count= commerceItems.size();
        return count;
    }

    public long getSelectOrderCount(){
        List<CommerceItemDto> commerceItems = getItems();
        long count= 0;
        if(commerceItems == null || commerceItems.size() == 0){
            return count;
        }
        for (CommerceItemDto commerceItemDto:commerceItems){
            //赠品不算入最大商品种类
            if(CommerceItemTypes.PROMOTION.equals(commerceItemDto.getType())){
                continue;
            }
            if (commerceItemDto.isSelected()){
                count++;
            }
        }
        return count;
    }

    public void addItem(CommerceItemDto item){

        List<CommerceItemDto> commerceItems = getItems();
        if(commerceItems != null){
            commerceItems.add(item);
        }
    }

    public boolean getSelectAll(){
        List<CommerceItemDto> commerceItems = getItems();
        if(commerceItems == null || commerceItems.size()==0){
            return false;
        }
        List<CommerceItemDto> selectCommerceItems = new ArrayList<>();
        for (CommerceItemDto commerceItemDto:commerceItems){
           if (commerceItemDto.isAvailable()){
               selectCommerceItems.add(commerceItemDto);
           }
        }
        if (selectCommerceItems == null || selectCommerceItems.size()==0){
            return false;
        }

        for (CommerceItemDto commerceItemDto:selectCommerceItems){
            if (!commerceItemDto.isSelected()){
                return false;
            }
        }
        return true;
    }

    public boolean isElectronicOrder(){

        List<CommerceItemDto> commerceItems = getItems();
        if(commerceItems == null || commerceItems.size()==0){
            return false;
        }
        for (CommerceItemDto commerceItemDto : commerceItems){
            if (commerceItemDto.isSelected() && !commerceItemDto.isElectronicItem()){
                return false;
            }
        }
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getBranchCompanyArehouse() {
        return branchCompanyArehouse;
    }

    public void setBranchCompanyArehouse(String branchCompanyArehouse) {
        this.branchCompanyArehouse = branchCompanyArehouse;
    }

    public String getFranchiseeId() {
        return franchiseeId;
    }

    public void setFranchiseeId(String franchiseeId) {
        this.franchiseeId = franchiseeId;
    }

    public String getFranchiseeStoreId() {
        return franchiseeStoreId;
    }

    public void setFranchiseeStoreId(String franchiseeStoreId) {
        this.franchiseeStoreId = franchiseeStoreId;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public String getStateDetail() {
        return stateDetail;
    }

    public void setStateDetail(String stateDetail) {
        this.stateDetail = stateDetail;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public OrderPriceInfoDto getPriceInfoDto() {
        return priceInfoDto;
    }

    public void setPriceInfoDto(OrderPriceInfoDto priceInfoDto) {
        this.priceInfoDto = priceInfoDto;
    }

    public String getCreatedByOrderId() {
        return createdByOrderId;
    }

    public void setCreatedByOrderId(String createdByOrderId) {
        this.createdByOrderId = createdByOrderId;
    }

    public String getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(String salesChannel) {
        this.salesChannel = salesChannel;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<CommerceItemDto> getItems() {
        if(this.items == null){
            this.items = new ArrayList<CommerceItemDto>();
        }
        return items;
    }

    public void setItems(List<CommerceItemDto> items) {
        this.items = items;
    }

    public InvoiceInfoDto getInvoiceInfoDto() {
        return invoiceInfoDto;
    }

    public void setInvoiceInfoDto(InvoiceInfoDto invoiceInfoDto) {
        this.invoiceInfoDto = invoiceInfoDto;
    }

    public ShippingGroupDto getShippingGroupDto() {
        return shippingGroupDto;
    }

    public void setShippingGroupDto(ShippingGroupDto shippingGroupDto) {
        this.shippingGroupDto = shippingGroupDto;
    }

    public List<PaymentGroupDto> getPaymentGroupDtos() {
        if(this.paymentGroupDtos == null){
            this.paymentGroupDtos = new ArrayList<PaymentGroupDto>();
        }
        return paymentGroupDtos;
    }

    public void setPaymentGroupDtos(List<PaymentGroupDto> paymentGroupDtos) {
        this.paymentGroupDtos = paymentGroupDtos;
    }

    public Integer getCountOfItem() {
        return countOfItem;
    }

    public void setCountOfItem(Integer countOfItem) {
        this.countOfItem = countOfItem;
    }

    public List<String> getCouponActivities() {
        if(couponActivities == null){
            couponActivities = new ArrayList<String>();
        }
        return couponActivities;
    }

    public void setCouponActivities(List<String> couponActivities) {
        this.couponActivities = couponActivities;
    }

    public String getThirdPartOrderNo() {
        return thirdPartOrderNo;
    }

    public void setThirdPartOrderNo(String thirdPartOrderNo) {
        this.thirdPartOrderNo = thirdPartOrderNo;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String pFrom) {
        from = pFrom;
    }
//
//    public String getCouponActivity() {
//        return couponActivity;
//    }
//
//    public void setCouponActivity(String couponActivity) {
//        this.couponActivity = couponActivity;
//    }
}
