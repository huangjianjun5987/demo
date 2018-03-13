package com.yatang.sc.xinyi.dto.saleOrder;

import com.thoughtworks.xstream.annotations.XStreamAliasType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @描述:  收货人信息DTO
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/23 9:00
 * @版本: v1.0
 */
@Getter
@Setter
@XStreamAliasType("receiverInfo")
public class SaleOrderReceiverInfoDto implements Serializable{

    private static final long serialVersionUID = 7508059349103526852L;
    private String company;//传加盟商名称，
    private String name;//收货人姓名

    private String mobile;//收货人手机

    private String province;//收货省

    private String city;//收货城市

    private String area;//区域 区县

    private String town;//村镇 小区/街道

    private String detailAddress;//收货区详细地址






}
