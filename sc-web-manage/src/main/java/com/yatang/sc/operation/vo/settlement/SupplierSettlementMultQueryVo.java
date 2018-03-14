package com.yatang.sc.operation.vo.settlement;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 供应商结算查询条件
 * @作者: tankejia
 * @创建时间: 2017/8/30-17:38 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class SupplierSettlementMultQueryVo implements Serializable {

    private static final long serialVersionUID = -6567275959970927748L;

    /**
     * 收货日期(最小值)
     * */
    @NotNull(message = "{msg.notEmpty.message}")
    private Date receiveDateMin;

    /**
     * 收货日期（最大值）
     * */
    @NotNull(message = "{msg.notEmpty.message}")
    private Date receiveDateMax;

    /**
     * 供应商编号
     * */
    private String supplierNo;

    /**
     * 供应商地点编号
     * */
    private String supplierAddNo;

    /**
     * 账期(0:周结；1：半月结；2：月结；)
     * */
    private Integer settlementPeriod;

    /**
     * 子公司编号
     * */
    private String branchCompanyNo;

    /**
     * 采购订单编号
     * */
    private String purchaseOrderNo;

}
