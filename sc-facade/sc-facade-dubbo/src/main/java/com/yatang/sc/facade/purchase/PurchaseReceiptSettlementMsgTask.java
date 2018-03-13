package com.yatang.sc.facade.purchase;

import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseReceiptWriteDubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PurchaseReceiptSettlementMsgTask implements Runnable {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    private PmPurchaseReceiptWriteDubboService receiptWriteDubboService;
    private PmPurchaseReceiptDto receiptDto;

    public PurchaseReceiptSettlementMsgTask(PmPurchaseReceiptWriteDubboService receiptWriteDubboService, PmPurchaseReceiptDto receiptDto) {
        this.receiptWriteDubboService = receiptWriteDubboService;
        this.receiptDto = receiptDto;
    }

    @Override
    public void run() {
        this.receiptWriteDubboService.pushPurchaseReceiptSettlementMsg(receiptDto);
    }
}
