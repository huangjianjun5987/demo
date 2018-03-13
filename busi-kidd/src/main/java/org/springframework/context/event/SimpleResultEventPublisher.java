package org.springframework.context.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;

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
 * @see EventPublicationInterceptor
 * @since 1.1.1
 */
public class SimpleResultEventPublisher implements ResultEventPublisher {

    private final Log logger = LogFactory.getLog(getClass());

    private ResultApplicationEventMulticaster resultApplicationEventMulticaster;

    public ResultApplicationEventMulticaster getResultApplicationEventMulticaster() {
        return resultApplicationEventMulticaster;
    }

    public void setResultApplicationEventMulticaster(ResultApplicationEventMulticaster resultApplicationEventMulticaster) {
        this.resultApplicationEventMulticaster = resultApplicationEventMulticaster;
    }

    /**
     * Publish the given event to all listeners.
     *
     * @param event the event to publish (may be an {@link ApplicationEvent}
     *              or a payload object to be turned into a {@link PayloadApplicationResultEvent})
     * @since 4.2
     */
    public Object publishEvent(Object event) {
        return publishEvent(event, false);
    }

    public Object publishEvent(Object event, boolean isMulti) {
        Assert.notNull(event, "Event must not be null");
        if (logger.isTraceEnabled()) {
            logger.trace("Publishing event: " + event);
        }

        // Decorate event as an ApplicationEvent if necessary
        ApplicationEvent applicationEvent = new PayloadApplicationResultEvent<>(this, event, isMulti);
        ResolvableType eventType = ((PayloadApplicationResultEvent) applicationEvent).getResolvableType();

        return resultApplicationEventMulticaster.multicastResultEvent(applicationEvent, eventType);
    }
}
