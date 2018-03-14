package com.yatang.sc.timedtask;

import com.yatang.sc.common.lock.RedisDistributedLockFactory;
import com.yatang.sc.order.OrderInventoryHelper;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.OrderItemsService;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.service.ReserveOrderInventoryHelper;
import org.apache.commons.collections.CollectionUtils;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liusongjie on 2017/7/27.
 */
@Component("autoCheckOrderInventoryScheduler")
public class AutoCheckOrderInventoryScheduler implements Scheduler {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderService orderService;

    @Autowired
    OrderLogService orderLogService;

    @Autowired
    OrderInventoryHelper orderInventoryHelper;

    @Autowired
    OrderMessageSender orderMessageSender;

    @Autowired
    CommerceItemService commerceItemService;

    @Autowired
    OrderItemsService orderItemsService;

    @Autowired
    private ReserveOrderInventoryHelper mInventoryHelper;

    @Autowired
    private RedisDistributedLockFactory mRedisDistributedLockFactory;

    public void execute() {
        List<Order> shippingStatePendingOrder = orderService.getOrdersForCheckInventory();
        if (CollectionUtils.isEmpty(shippingStatePendingOrder)) {
            return;
        }

        for (Order order : shippingStatePendingOrder) {
            log.info("批处理检查库存：{}", order.getId());
            String lockPath = "/order_fulfill_lock_" + order.getId();
            RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
            try {
                rLock.lock();
                mInventoryHelper.reserve(order, false, "定时检查任务");
            } catch (Exception pE) {
                log.error("预留库存异常:",pE);
            } finally {
                rLock.unlock();
            }
        }
    }
}
