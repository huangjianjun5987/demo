package com.yatang.sc.order.domain;

import com.busi.common.utils.StringUtils;
import com.yatang.sc.order.states.ShippingModes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Getter@Setter@NoArgsConstructor
public class ShippingGroup implements Serializable {

    private String id;

    private Date actualShipDate;

    private String description;

    private Long shippingPriceInfo;

    private Date shipOnDate;

    private String type;

    private String shippingMethod;

    private String specialInstructions;

    private Short state;

    private String stateDetail;

    private Date submitDate;

    private String province;

    private String city;

    private String district;

    private String detailAddress;

    private String postcode;

    private String consigneeName;

    private String cellphone;

    private String shippingNo;

    private Date estimatedArrivalDate;

    private String deliveryer;

    private String deliveryerPhone;

    private String shippingModes;//配送模式

    private String singedCertImg;//签收凭证

    private String remarks;//签收备注/异议

    private List<ShippingPriceAdj> shippingPriceAdjList;

    /**
     * 运费的价格信息
     */
    private ShippingPrice shippingPriceInfoDto;

    private static final long serialVersionUID = 1L;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getId() {
        return id;
    }

    public void setId(String pId) {
        id = pId;
    }

    public Date getActualShipDate() {
        return actualShipDate;
    }

    public void setActualShipDate(Date pActualShipDate) {
        actualShipDate = pActualShipDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public Long getShippingPriceInfo() {
        return shippingPriceInfo;
    }

    public void setShippingPriceInfo(Long pShippingPriceInfo) {
        shippingPriceInfo = pShippingPriceInfo;
    }

    public Date getShipOnDate() {
        return shipOnDate;
    }

    public void setShipOnDate(Date pShipOnDate) {
        shipOnDate = pShipOnDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String pType) {
        type = pType;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String pShippingMethod) {
        shippingMethod = pShippingMethod;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String pSpecialInstructions) {
        specialInstructions = pSpecialInstructions;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short pState) {
        state = pState;
    }

    public String getStateDetail() {
        return stateDetail;
    }

    public void setStateDetail(String pStateDetail) {
        stateDetail = pStateDetail;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date pSubmitDate) {
        submitDate = pSubmitDate;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String pProvince) {
        province = pProvince;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String pCity) {
        city = pCity;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String pDistrict) {
        district = pDistrict;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String pDetailAddress) {
        detailAddress = pDetailAddress;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String pPostcode) {
        postcode = pPostcode;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String pConsigneeName) {
        consigneeName = pConsigneeName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String pCellphone) {
        cellphone = pCellphone;
    }

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String pShippingNo) {
        shippingNo = pShippingNo;
    }

    public Date getEstimatedArrivalDate() {
        return estimatedArrivalDate;
    }

    public void setEstimatedArrivalDate(Date pEstimatedArrivalDate) {
        estimatedArrivalDate = pEstimatedArrivalDate;
    }

    public String getDeliveryer() {
        return deliveryer;
    }

    public void setDeliveryer(String pDeliveryer) {
        deliveryer = pDeliveryer;
    }

    public String getDeliveryerPhone() {
        return deliveryerPhone;
    }

    public void setDeliveryerPhone(String pDeliveryerPhone) {
        deliveryerPhone = pDeliveryerPhone;
    }

    public String getShippingModes() {
        if (StringUtils.isEmpty(shippingModes)) {
            shippingModes = ShippingModes.UNIFIED_SHIPPING_MODE.getCode();
        }
        return shippingModes;
    }

    public void setShippingModes(String pShippingModes) {
        shippingModes = pShippingModes;
    }

    public List<ShippingPriceAdj> getShippingPriceAdjList() {
        return shippingPriceAdjList;
    }

    public String getSingedCertImg() {
        return singedCertImg;
    }

    public void setSingedCertImg(String pSingedCertImg) {
        singedCertImg = pSingedCertImg;
    }

    public void setShippingPriceAdjList(List<ShippingPriceAdj> pShippingPriceAdjList) {
        shippingPriceAdjList = pShippingPriceAdjList;
    }

    public ShippingPrice getShippingPriceInfoDto() {
        return shippingPriceInfoDto;
    }

    public void setShippingPriceInfoDto(ShippingPrice pShippingPriceInfoDto) {
        shippingPriceInfoDto = pShippingPriceInfoDto;
    }
}