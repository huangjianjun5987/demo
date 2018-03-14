package com.yatang.sc.timedtask;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.processor.OrderIndexMsgTask;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.service.OrderIndexHelper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiangyonghong on 2017/9/28.
 *
 *
 */
@JobHander(value="autoSendOrderIndexScheduler")
@Component("autoSendOrderIndexScheduler")
public class AutoSendOrderIndexScheduler extends IJobHandler {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderMessageSender orderMessageSender;

    @Autowired
    OrderIndexHelper orderIndexHelper;

    @Value("${orderIndex.pageSize}")
    private long pageSize;

    @Value("${orderIndex.enable}")
    private boolean enable;

    @Value("${orderIndex.threadNum}")
    private int threadNum;

//    public void execute() {
//        if(!enable){
//            return ;
//        }
//        log.info("autoSendOrderIndexScheduler 开始执行！");
//
//        long orderCount = orderService.getAllOrderCount();
//        long page = orderCount/pageSize;
//        log.info("autoSendOrderIndexScheduler 一共{}条记录.",orderCount);
//        Map<String,Object> condition = new HashMap<String,Object>();
//        ExecutorService threadExecutor = Executors.newFixedThreadPool(threadNum);
//
//        for (int i=0; i<=page; i++){
//            condition.put("start",i*pageSize);
//            condition.put("end",pageSize);
//            List<String> orderIds = orderService.getAllOrderId(condition);
//            threadExecutor.execute(new OrderIndexMsgThread(orderIds,orderIndexHelper));
//        }
//        log.info("autoSendOrderIndexScheduler 执行结束！");
//    }


    @Override
    public ReturnT<String> execute(String... strings){
        XxlJobLogger.log("autoSendOrderIndexScheduler 订单索引推送线程开始启动。");
        try{
            if(!enable){
                return new ReturnT("当前配置不允许启动订单索引推送线程。");
            }

            long orderCount = orderService.getAllOrderCount();
            if(orderCount <= pageSize){
                orderCount = pageSize;
            }
            long page = orderCount/pageSize;
            XxlJobLogger.log("autoSendOrderIndexScheduler 一共{0}条记录.",orderCount);
            Map<String,Object> condition = new HashMap<String,Object>();
            ExecutorService threadExecutor = Executors.newFixedThreadPool(threadNum);

            for (int i=0; i<=page; i++){
                condition.put("start",i*pageSize);
                condition.put("end",pageSize);
                List<String> orderIds = orderService.getAllOrderId(condition);
                if (!CollectionUtils.isEmpty(orderIds)){
                    for(String orderId : orderIds){
                        threadExecutor.execute(new OrderIndexMsgTask(orderId,orderIndexHelper));
                    }
                }

            }
            XxlJobLogger.log("autoSendOrderIndexScheduler 订单索引推送线程启动成功。");
        }catch (Exception e){
            XxlJobLogger.log("autoSendOrderIndexScheduler 订单索引推送线程启动失败。");
            XxlJobLogger.log(e.getMessage(),e);
        }

        return new ReturnT("订单索引推送线程启动成功。");
    }
}
