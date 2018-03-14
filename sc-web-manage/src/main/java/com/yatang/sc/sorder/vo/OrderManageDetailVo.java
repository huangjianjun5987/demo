package com.yatang.sc.sorder.vo;

import java.io.Serializable;
import java.util.Date;

public class OrderManageDetailVo implements Serializable{

    private static final long serialVersionUID = -8614109938944018786L;

    private String id;    //订单编号

    private String createdByOrderId;  //父订单编号

    private String orderType;   //订单类型

    private Short state;      //状态

    private String stateDetail;   //订单状态描述

    private String branchCompanyId; //子公司code

    private String profileId; //用户id

    private String description; //订单备注

    private Date completedTime;   //完成时间

    private Short paymentState; //当前支付方式的状态

    private String paymentStateDetail;  //支付状态描述

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
     * 手机
     */
    private String cellphone;





 }