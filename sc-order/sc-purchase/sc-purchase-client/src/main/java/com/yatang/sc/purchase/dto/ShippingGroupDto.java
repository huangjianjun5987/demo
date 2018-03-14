package com.yatang.sc.purchase.dto;

import lombok.Data;
/*import org.springframework.format.annotation.DateTimeFormat;*/

import java.io.Serializable;
import java.util.Date;

@Data
public class ShippingGroupDto implements Serializable{
    private static final long serialVersionUID = -8956189458970947389L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 实际发货时间
     */
    private Date actualShipDate;

    /**
     * 配送方式描述
     */
    private String description;

    /**
     * 运费的价格信息
     */
    private ShippingPriceInfoDto shippingPriceInfoDto;

    /**
     * 指定发货时间
     */
    /*@DateTimeFormat( pattern = "yyyy-MM-dd" )*/
    private Date shipOnDate;

    /**
     * 当前配送方式的java对象全路径
     */
    private String type;

    /**
     * 当前配送方式文字描述
     */
    private String shippingMethod;

    /**
     * 特殊说明
     */
    private String specialInstructions;

    /**
     * 配送方式的状态说明
     */
    private Short state;

    /**
     * 配送方式详情
     */
    private String stateDetail;

    /**
     * 配送方式提交时间
     */
    private Date submitDate;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 邮编
     */
    private String postcode;

    /**
     * 收货人
     */
    private String consigneeName;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 手机或者固定电话
     */
    private String cellphone;

    /**
     * 运单号
     */
    private String shippingNo;

    private String shippingModes;//配送模式

    private String spName;//配送方名称用于查询结果显示

    private String remarks;//备注/异议

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getShippingModes() {
        return shippingModes;
    }

    public void setShippingModes(String shippingModes) {
        this.shippingModes = shippingModes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getActualShipDate() {
        return actualShipDate;
    }

    public void setActualShipDate(Date actualShipDate) {
        this.actualShipDate = actualShipDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ShippingPriceInfoDto getShippingPriceInfoDto() {
        return shippingPriceInfoDto;
    }

    public void setShippingPriceInfoDto(ShippingPriceInfoDto shippingPriceInfoDto) {
        this.shippingPriceInfoDto = shippingPriceInfoDto;
    }

    public Date getShipOnDate() {
        return shipOnDate;
    }

    public void setShipOnDate(Date shipOnDate) {
        this.shipOnDate = shipOnDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
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

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String shippingNo) {
        this.shippingNo = shippingNo;
    }
}
