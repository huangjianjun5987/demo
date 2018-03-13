package com.yatang.sc.facade.domain.pm;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>@description:收货单详细信息</p>
 *
 * @author yangshuang
 * @version v1.0
 * @date 2018/1/10 11:14
 */
@Getter
@Setter
public class PmPurchaseReceiptDetailPo implements Serializable {
    private static final long serialVersionUID = -866318400273746823L;

    /**
     * 收货单基础信息
     */
    private PmPurchaseReceiptPo pmPurchaseReceipt;

    /**
     * 收货单明细
     */
    private List<PmPurchaseReceiptItemsPo> receiptPruducts;


}
