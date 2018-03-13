package com.yatang.sc.facade.scheduler;

import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptParamPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptPo;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseReceiptWriteDubboService;
import com.yatang.sc.facade.purchase.PurchaseReceiptSettlementMsgTask;
import com.yatang.sc.facade.service.PmPurchaseReceiptService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @描述: 全量推送采购收货结算数据任务
 * @作者: tankejia
 * @创建时间: 2017/12/26-17:16 .
 * @版本: 1.0 .
 */
@JobHander(value="autoSendPurReceiptSettlementScheduler")
@Component("autoSendPurReceiptSettlementScheduler")
public class AutoSendPurReceiptSettlementScheduler extends IJobHandler {

    @Autowired
    private PmPurchaseReceiptService pmPurchaseReceiptService;

    @Autowired
    private PmPurchaseReceiptWriteDubboService receiptWriteDubboService;

    @Value("${purReceiptSettlement.pageSize}")
    private Long pageSize;

    @Value("${purReceiptSettlement.enable}")
    private boolean enable;

    @Value("${purReceiptSettlement.threadNum}")
    private int threadNum;

    @Override
    public ReturnT<String> execute(String... params) throws Exception {
        XxlJobLogger.log("autoSendPurReceiptSettlementScheduler 采购收货结算推送线程开始启动。");

        try{
            if(!enable){
                return new ReturnT("当前配置不允许启动采购收货结算推送线程。");
            }

            PmPurchaseReceiptParamPo paramPo = new PmPurchaseReceiptParamPo();
            // 查询已收货的记录数
            Long totalCount = pmPurchaseReceiptService.getAllPurchaseReceiptCount(2);
            XxlJobLogger.log("autoSendPurReceiptSettlementScheduler 一共{0}条记录.",totalCount);
            if(totalCount <= pageSize){
                totalCount = pageSize;
            }
            Long count = totalCount/pageSize;
            ExecutorService threadExecutor = Executors.newFixedThreadPool(threadNum);

            for (int i=0; i<=count; i++) {
                paramPo.setPageNum(i+1);
                paramPo.setPageSize(pageSize.intValue());
                paramPo.setStatus(2);
                PageInfo<PmPurchaseReceiptPo> poPageInfo = pmPurchaseReceiptService.queryReceipts(paramPo);

                if (null != poPageInfo && !CollectionUtils.isEmpty(poPageInfo.getList())) {
                    List<PmPurchaseReceiptDto> receiptDtos = BeanConvertUtils.convertList(poPageInfo.getList(), PmPurchaseReceiptDto.class);
                    for (PmPurchaseReceiptDto receiptDto : receiptDtos) {
                        threadExecutor.execute(new PurchaseReceiptSettlementMsgTask(receiptWriteDubboService, receiptDto));
                    }
                }

            }

            XxlJobLogger.log("autoSendPurReceiptSettlementScheduler 采购收货结算推送线程启动成功。");
        }catch (Exception e){
            XxlJobLogger.log("autoSendPurReceiptSettlementScheduler 采购收货结算推送线程启动失败。");
            XxlJobLogger.log(e.getMessage(),e);
        }

        return new ReturnT("采购收货结算推送线程开始启动。");
    }
}
