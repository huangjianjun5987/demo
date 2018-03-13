package org.springframework.context.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * Interface that encapsulates event publication functionality.
 * Serves as super-interface for {@link ApplicationContext}.
 *
 * @author Juergen Hoeller
 * @author Stephane Nicoll
 * @author yipeng
 * @see ApplicationContext
 * @see ApplicationEventPublisherAware
 * @see org.springframework.context.ApplicationEvent
 * @see org.springframework.context.event.EventPublicationInterceptor
 * @since 1.1.1
 */
public interface ResultEventPublisher {

    /**
     * 单结果事件监听，直接返回监听结果
     *
     * @param event
     * @return
     */
    Object publishEvent(Object event);

    /**
     * 存在多个结果事件监听，返回的对象是List，里面包含每个监听返回的结果
     *
     * @param event
     * @param isMulti
     * @return
     */
    Object publishEvent(Object event, boolean isMulti);
}
