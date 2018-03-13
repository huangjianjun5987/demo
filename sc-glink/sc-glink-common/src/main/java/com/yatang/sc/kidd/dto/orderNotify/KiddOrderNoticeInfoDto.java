package com.yatang.sc.kidd.dto.orderNotify;

import com.yatang.sc.glink.dto.saleOrder.SaleCargoDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述: kidd订单通知
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/26 10:36
 * @版本: v1.0
 */
@Getter
@Setter
public class KiddOrderNoticeInfoDto implements Serializable {

    private static final long serialVersionUID = -6129443983648760472L;

    private String orderCode;//单据编号

    private String orderId;//单据编号 仓储系统单据号

    private String orderType;//单据类型

    private String currentStatus;//订单状态  ACCEPT=仓库接单,PICK=捡货,CHECK = 复核,PACKAGE= 打包,SIGN=签收,REJECT=接单失败(极少使用),EXCEPTION=订单发货失败（极少使用）


    private String driverName;      //司机名称 glink信息

    private String remark;           //备注 glink信息

    private String driverPhone;     //司机电话 glink信息

    private String deliveryTime;    //预计送达时间 glink信息


    //心怡物流信息

    private String logisticsCode;//物流公司编码

    private String logisticsName;//物流公司名称

    private String expressCode;//运单号


    //**际链物流信息
    private List<SaleCargoDto> cargoDtos = new ArrayList<>();





}
