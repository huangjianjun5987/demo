package com.yatang.sc.operation.vo.supplier;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @描述: 供应商基本信息.银行账户和纳税人识别号校验
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/6/14 10:40
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class SupplierCheckVo implements Serializable{
    private static final long serialVersionUID = 934188276619410579L;

    private String taxId;//税务主键id

    private String taxpayerNo;//税务人识别号

    private SupplierBasicInfoVo supplierBasicInfo;//供应商基本信息

    private String bankAccount;//银行账户

    private String bankId;
}
