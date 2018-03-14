package com.yatang.sc.app.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @描述:
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/7/18 9:46
 * @版本:v1.0
 */
public class OrderDetailVo implements Serializable {


    private static final long serialVersionUID = -3880134357187055623L;

    private String consigneeName;                        //收货人

    private String cellphone;                              //收货人手机号

    private String province;          //省

    private String city;          //市

    private String district;         //区

    private String detailAddress;                         //详细地址

    List<CommerceItemAppVo> items;                //购物车商品

    private double rawSubtotal;                     //订单中商品未执行任何折扣的净额

    private double shipping;                          //订单运费

    private double discountAmount;                   //优惠金额

    private double total;                           //订单支付总额

    private String id;                              //订单编号

    private Date creationTime;                      //下单时间

    private List<String>  paymentMethods;             //支付方式

    private List<Date> payDates;                             //付款时间

    private Date actualShipDate;                       //实际发货时间

    private String shippingMethod;                       //当前配送方式文字描述

    private String shippingDetail;                          //配送方式详情

    private String invoiceType;           //发票类型

    private String invoiceTitle;        //发票抬头

    private String companyName;  //公司名称

    private double userDiscountAmount;  //会员折扣

    private double couponDiscountAmount;   //优惠券折扣

    private Short state;  //购物车状态


    private String orderType;    //订单类型

    private String orderTypeDesc;// 订单类型描述

    private short returnState;    //退货状态

    private String returnStateDesc;//退换货状态描述

    private String shippingModes;//配送模式

    private boolean valid = true;  //默认在有效期

    private Long leftNoPayTime; //订单未支付的剩余时间(单位：分钟)

    public String getShippingModes() {
        return shippingModes;
    }

    public void setShippingModes(String shippingModes) {
        this.shippingModes = shippingModes;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public List<CommerceItemAppVo> getItems() {
        return items;
    }

    public void setItems(List<CommerceItemAppVo> items) {
        this.items = items;
    }

    public double getRawSubtotal() {
        return rawSubtotal;
    }

    public void setRawSubtotal(double rawSubtotal) {
        this.rawSubtotal = rawSubtotal;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public List<String> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<String> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<Date> getPayDates() {
        return payDates;
    }

    public void setPayDates(List<Date> payDates) {
        this.payDates = payDates;
    }

    public Date getActualShipDate() {
        return actualShipDate;
    }

    public void setActualShipDate(Date actualShipDate) {
        this.actualShipDate = actualShipDate;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getShippingDetail() {
        return shippingDetail;
    }

    public void setShippingDetail(String shippingDetail) {
        this.shippingDetail = shippingDetail;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getUserDiscountAmount() {
        return userDiscountAmount;
    }

    public void setUserDiscountAmount(double userDiscountAmount) {
        this.userDiscountAmount = userDiscountAmount;
    }

    public double getCouponDiscountAmount() {
        return couponDiscountAmount;
    }

    public void setCouponDiscountAmount(double couponDiscountAmount) {
        this.couponDiscountAmount = couponDiscountAmount;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public Long getLeftNoPayTime() { return leftNoPayTime; }

    public void setLeftNoPayTime(Long leftNoPayTime) { this.leftNoPayTime = leftNoPayTime; }
}
