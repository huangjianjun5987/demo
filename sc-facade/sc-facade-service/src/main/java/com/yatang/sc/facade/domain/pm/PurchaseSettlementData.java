package com.yatang.sc.facade.domain.pm;

import com.busi.mq.message.MQMsg;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 采购结算数据（财务中心结算用）
 * @作者: tankejia
 * @创建时间: 2017/12/7-14:43 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class PurchaseSettlementData implements Serializable, MQMsg {

    private static final long serialVersionUID = 1209488454169803430L;

    /**
     * id（取采购订单或采购退货单主键，再加上前缀CGDD(采购订单)、CGTH(采购退货)）
     * */
    private String      id;

    /**
     * 分公司id
     * */
    private String      subCompId;

    /**
     * 分公司名称
     * */
    private String      subCompName;

    /**
     * 地点编号（仓库或门店编号）
     * */
    private String      localeNo;

    /**
     * 地点名称（仓库或门店名称）
     * */
    private String      localeName;

    /**
     * 交货单号（采购时供应商的出库/送货单号，退货时提货单号）
     * */
    private String      deliveryVoucherNo;

    /**
     * 仓库业务单号（入库单号(=收货单号)或出库单号(=采购退货单号)）
     * */
    private String      whVoucherNo;

    /**
     * 业务单号（采购订单号或采购退货单号）
     * */
    private String      poNo;

    /**
     * 物流仓库记录号
     * */
    private String      logWhRecNo;

    /**
     * 订单类型编码
     * */
    private String      orderTypeNo;

    /**
     * 订单类型（普通采购单、促销采购单、赠品采购单、采购退货单）
     * */
    private String      orderType;

    /**
     * 出入库类型编码
     * */
    private String      WhInTypeNo;

    /**
     * 出入库类型名称（采购入库、采购退货出库）
     * */
    private String      whInType;

    /**
     * 出入库日期
     * */
    private String      whInDate;

    /**
     * 供应商地点编码
     * */
    private String      vendorNo;

    /**
     * 供应商地点名称
     * */
    private String      vendorName;

    private List<PurchaseActualItem> purchaseActualItems;

}
