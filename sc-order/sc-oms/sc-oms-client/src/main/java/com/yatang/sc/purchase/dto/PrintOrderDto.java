package com.yatang.sc.purchase.dto;


import java.io.Serializable;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/17.
 */
public class PrintOrderDto implements Serializable{

    private String numberUrl;//条形码图片地址

    private String id;//序号
    private String profileId;//用户id
    private String userName;//用户帐号
    private String description;//订单备注
    private String customRemark;//买家留言
    private String submitTime;//下单时间

    private String transNum;//交易号
    private String payDate;//支付时间
    private String paymentMethod;//支付方式

    private String detailAddress;//配送地址
    private String cellphone;//电话
    private String consigneeName;//收货人
    private String shippingMethod;//配送方式
    private String shippingNo;//运单号

    private Double amount;//商品总价
    private Double shipping;//运费
    private Double discountAmount;//优惠金额
    private Double total;//总金额

    private List<PrintOrderProductDto> productlist;

    public PrintOrderDto() {
    }

    public String getNumberUrl() {
        return numberUrl;
    }

    public void setNumberUrl(String numberUrl) {
        this.numberUrl = numberUrl;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomRemark() {
        return customRemark;
    }

    public void setCustomRemark(String customRemark) {
        this.customRemark = customRemark;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String shippingNo) {
        this.shippingNo = shippingNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getShipping() {
        return shipping;
    }

    public void setShipping(Double shipping) {
        this.shipping = shipping;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<PrintOrderProductDto> getProductlist() {
        return productlist;
    }

    public void setProductlist(List<PrintOrderProductDto> productlist) {
        this.productlist = productlist;
    }
}
