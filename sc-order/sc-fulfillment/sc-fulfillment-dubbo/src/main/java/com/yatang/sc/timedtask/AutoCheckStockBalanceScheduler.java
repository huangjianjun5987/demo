package com.yatang.sc.timedtask;

/**
 * Created by liusongjie on 2017/7/28.
 *
 * 9、	由“订单库存处理服务”定时检查订单状态 为“已审核”，库存状态为“待处理”的销售订单进行业务处理。
 * 校验订单出库仓中，订单内商品货量是否满足订单需求:
 * (1) 如果在库可用数量（现有量-销售保留量-调拨保留量 – RTV保留量）充足，
 * 更新销售订单行中的“订单确认数量”为订单行数量，变更库存状态为“未传送”，
 * 调用库存管理接口“销售保留”，更新库存表中“销售保留量”= “销售保留量” + “订单确认数量”，防止抢货；
 * (2) 如果在库可用量不足，且订单为“先采后销”模式，则更新销售订单行中的“订单确认数量”为 在库可用数量，
 * 并变更库存状态为“未传送”；如果订单为 “先销后采”模式，则更新销售订单行中的“订单确认数量”为 0，
 * 且变更订单状态为“采购未到货”；调用库存管理接口“销售保留”，更新库存表中“销售保留量”= “销售保留量” + “订单确认数量”；
 */
public class AutoCheckStockBalanceScheduler implements Scheduler {

    @Override
    public void execute() {

    }
}
