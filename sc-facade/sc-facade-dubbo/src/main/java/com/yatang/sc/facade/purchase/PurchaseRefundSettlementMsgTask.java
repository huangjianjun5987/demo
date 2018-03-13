package com.yatang.sc.facade.purchase;

import com.yatang.sc.facade.dto.pm.PmPurchaseRefundDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseRefundWriteDubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PurchaseRefundSettlementMsgTask implements Runnable {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    private PmPurchaseRefundWriteDubboService refundWriteDubboService;
    private PmPurchaseRefundDto refundDto;

    public PurchaseRefundSettlementMsgTask(PmPurchaseRefundWriteDubboService refundWriteDubboService, PmPurchaseRefundDto refundDto) {
        this.refundWriteDubboService = refundWriteDubboService;
        this.refundDto = refundDto;
    }

    @Override
    public void run() {
        this.refundWriteDubboService.pushPurchaseRefundSettlementMsg(refundDto);
    }
}
