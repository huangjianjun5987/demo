package com.yatang.sc.kidd.dto.returnrequest;

import com.yatang.sc.kidd.dto.common.KiddOrderLinesDto;

import java.io.Serializable;
import java.util.List;

/**
 * @描述:退货换货发送MQ封装类
 * @类名:KiddReturnOrderMqDto
 * @作者: lvheping
 * @创建时间: 2017/10/19 16:20
 * @版本: v1.0
 */

public class KiddReturnOrderMqDto implements Serializable {
    private static final long serialVersionUID = -2754911091854141675L;
    private String returnOrderCode;//退货单编码
    private String warehouseCode;//仓库编码
    private String orderType;//业务类型
    private String status;//状态
    private String name;//发件人姓名
    private String mobile;//发件人移动电话
    private String province;//发件人省份
    private String city;//发件人城市
    private String detailAddress;//发件人详细地址
    private List<KiddOrderLinesDto> orderLinesDtos;//商品信息

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReturnOrderCode() {
        return returnOrderCode;
    }

    public void setReturnOrderCode(String returnOrderCode) {
        this.returnOrderCode = returnOrderCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public List<KiddOrderLinesDto> getOrderLinesDtos() {
        return orderLinesDtos;
    }

    public void setOrderLinesDtos(List<KiddOrderLinesDto> orderLinesDtos) {
        this.orderLinesDtos = orderLinesDtos;
    }

    @Override
    public String toString() {
        return "KiddReturnOrderMqDto{" +
                "returnOrderCode='" + returnOrderCode + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", orderType='" + orderType + '\'' +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", orderLinesDtos=" + orderLinesDtos +
                '}';
    }
}
