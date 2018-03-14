package com.yatang.sc.operation.vo.supplier;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 修改供应商合作信息（回显）
 * @作者: tankejia
 * @创建时间: 2017/5/31-16:28 .
 * @版本: 1.0 .
 */
@Getter
@Setter
public class SupplierCooperationEditVo implements Serializable {

    private static final long  serialVersionUID = 3657702892858291262L;

    /**
     * 供应商合作信息id
     */
    @NotNull(message = "{msg.notEmpty.message}")
    private Integer             id;

    /**
     * 结算账期
     */
    @Min(value = 1, message = "{msg.largerThanZero.integer.message}")
    private Integer             settlementPeriod;

    /**
     * 结算账户类型
     * */
    @NotNull(message = "{msg.notEmpty.message}")
    private Integer             settlementAccountType;

    /**
     *返利
     * */
    @DecimalMin("0")
    @DecimalMax("100")
    private BigDecimal          rebateRate;

    /**
     *保证金
     * */
    @DecimalMin(value = "0", inclusive = false, message = "{msg.largeThanZero.message}")
    private BigDecimal          guaranteeMoney;

}
