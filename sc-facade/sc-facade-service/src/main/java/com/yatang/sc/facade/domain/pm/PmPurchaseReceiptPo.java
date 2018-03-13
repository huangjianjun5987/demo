package com.yatang.sc.facade.domain.pm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 收货单po
 *
 * @author: yinyuxin
 * @version: 1.0, 2017年7月26日
 */
@Setter
@Getter
@ToString
public class PmPurchaseReceiptPo implements Serializable {

    private static final long serialVersionUID = -3263445886015824392L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 收货单号
     */
    private String purchaseReceiptNo;

    /**
     * 采购单id
     */
    private String purchaseOrderId;

    /**
     * ASN编号
     */
    private String asn;

    /**
     * 预计到货日期,系统自动默认，值为当前日期.注：预留与供应商系统对接预发货通知
     */
    private Date estimatedReceivedDate;

    /**
     * 收货日期,系统自动默认，值为创建收货单时间
     */
    private Date receivedTime;

    /**
     * 子公司ID
     */
    private String branchCompanyId;

    /**
     * 状态（0:待下发，1：已下发，2:已收货，3:已取消，4：异常）
     */
    private Integer status;
    // 异常原因
    private String exceptionReason;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 修改人
     */
    private String modifyUserId;


    /**
     * 销售订单id
     */
    private String saleOrderId;


    /**
     * 小票url
     */
    private String ticketUrl;


}