package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.UpdateReceiptYCStatusDto;
import com.yatang.sc.facade.dto.UpdateReceiptYQXStatusDto;
import com.yatang.sc.facade.dto.UpdateReceiptYXFStatusDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDetailDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDto;
import com.yatang.sc.facade.dto.pm.PmUploadTicketParamDto;
import com.yatang.sc.facade.dto.pm.PurchaseOrderConfirmDto;

public interface PmPurchaseReceiptWriteDubboService {

    /**
     * 根据采购单id新增一条收货单
     *
     * @param orderCode 采购单单号
     * @return
     */
    public Response<Boolean> createReceipt(String orderCode);


    /**
     * <method description>推送采购收货单到MQ
     *
     * @param purchaseReceiptNo 采购定单单号
     * @return
     */
    public Response<Boolean> pushPurchaseReceiptToMQ(String purchaseOrderNo);


    /**
     * <method description>更新收货单状态为已下发
     *
     * @param purchaseReceiptNo
     * @return
     * @author zhoubaiyun
     */
    public Response<Boolean> updateYXFStatus(UpdateReceiptYXFStatusDto updateReceiptYXFStatusDto);


    /**
     * <method description>更新收货单状态为已收货(仓库与第三方交互收货)
     *
     * @param purchaseOrderConfirmDto
     * @return
     * @author zhoubaiyun
     */
    public Response<Boolean> updateYSHStatus(PurchaseOrderConfirmDto purchaseOrderConfirmDto);


    /**
     * <method description>更新收货单状态为已取消
     *
     * @param purchaseReceiptNo
     * @return
     * @author zhoubaiyun
     */
    public Response<Boolean> updateYQXStatus(UpdateReceiptYQXStatusDto updateReceiptYQXStatusDto);


    /**
     * <method description>更新收货单状态为异常
     *
     * @param purchaseReceiptNo
     * @return
     * @author zhoubaiyun
     */
    public Response<Boolean> updateYCStatus(UpdateReceiptYCStatusDto updateReceiptYCStatusDto);


    /**
     * @param receiptDto
     * @Description: 推送采购收货结算数据
     * @author tankejia
     * @date 2017/12/27- 11:46
     */
    Response<Void> pushPurchaseReceiptSettlementMsg(PmPurchaseReceiptDto receiptDto);


    /**
     * 录入收货单的asn
     *
     * @param detailDto
     * @return
     * @author yangshuang
     */
    Response<Void> createPurchaseReceiptASN(PmPurchaseReceiptDetailDto detailDto);


    /**
     * 更新拆分的收货单信息
     *
     * @param purchaseReceiptId 收货单id
     * @param saleOrderId       销售订单id
     * @return Void
     * @author yangshuang
     */
    Response<Void> updateSplitPurchaseReceipt(Long purchaseReceiptId, String saleOrderId);


    /**
     * 更新小票(上传或者删除)
     *
     * @param ticketParamDto 销售订单id
     * @return Void
     * @author yangshuang
     */
    Response<Void> updatePmTicket(PmUploadTicketParamDto ticketParamDto);
}
