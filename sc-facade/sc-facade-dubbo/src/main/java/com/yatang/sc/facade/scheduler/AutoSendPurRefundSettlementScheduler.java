package com.yatang.sc.facade.scheduler;

import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import com.yatang.sc.facade.domain.pm.PmPurchaseRefundExtPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseRefundPo;
import com.yatang.sc.facade.dto.pm.PmPurchaseRefundDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseRefundWriteDubboService;
import com.yatang.sc.facade.purchase.PurchaseRefundSettlementMsgTask;
import com.yatang.sc.facade.service.PmpurchaseRefundService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @描述: 全量推送采购退货结算数据任务
 * @作者: tankejia
 * @创建时间: 2017/12/26-17:17 .
 * @版本: 1.0 .
 */
@JobHander(value="autoSendPurRefundSettlementScheduler")
@Component("autoSendPurRefundSettlementScheduler")
public class AutoSendPurRefundSettlementScheduler extends IJobHandler {

    @Autowired
    private PmpurchaseRefundService pmpurchaseRefundService;

    @Autowired
    private PmPurchaseRefundWriteDubboService refundWriteDubboService;

    @Value("${purReceiptSettlement.pageSize}")
    private Long pageSize;

    @Value("${purReceiptSettlement.enable}")
    private boolean enable;

    @Value("${purReceiptSettlement.threadNum}")
    private int threadNum;

    @Override
    public ReturnT<String> execute(String... params) throws Exception {

        XxlJobLogger.log("autoSendPurRefundSettlementScheduler 采购退货结算推送线程开始启动。");

        try{
            if(!enable){
                return new ReturnT("当前配置不允许启动采购退货结算推送线程。");
            }

            PmPurchaseRefundExtPo paramPo = new PmPurchaseRefundExtPo();
            // 查询已退货的记录数
            Long totalCount = pmpurchaseRefundService.getAllPurchaseRefundCount(5);
            XxlJobLogger.log("autoSendPurRefundSettlementScheduler 一共{0}条记录.",totalCount);
            if(totalCount <= pageSize){
                totalCount = pageSize;
            }
            Long count = totalCount/pageSize;
            ExecutorService threadExecutor = Executors.newFixedThreadPool(threadNum);

            for (int i=0; i<=count; i++) {
                paramPo.setPageNum(i+1);
                paramPo.setPageSize(pageSize.intValue());
                paramPo.setStatus(5);
                // 降序排序
                paramPo.setOrderType(1);
                // 根据退货单号
                paramPo.setOrderItem(0);
                PageInfo<PmPurchaseRefundPo> poPageInfo = pmpurchaseRefundService.queryPmPurchaseRefund(paramPo);

                if (null != poPageInfo && ! CollectionUtils.isEmpty(poPageInfo.getList())) {
                    List<PmPurchaseRefundDto> refundDtos = BeanConvertUtils.convertList(poPageInfo.getList(), PmPurchaseRefundDto.class);
                    for (PmPurchaseRefundDto refundDto : refundDtos) {
                        threadExecutor.execute(new PurchaseRefundSettlementMsgTask(refundWriteDubboService, refundDto));
                    }
                }

            }

            XxlJobLogger.log("autoSendPurRefundSettlementScheduler 采购退货结算推送线程启动成功。");
        }catch (Exception e){
            XxlJobLogger.log("autoSendPurRefundSettlementScheduler 采购退货结算推送线程启动失败。");
            XxlJobLogger.log(e.getMessage(),e);
        }

        return new ReturnT("采购退货结算推送线程开始启动。");
    }
}
