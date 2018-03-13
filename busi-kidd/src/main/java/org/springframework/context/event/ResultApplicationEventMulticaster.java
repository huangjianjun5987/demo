package org.springframework.context.event;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ResultApplicationEventMulticaster extends AbstractApplicationEventMulticaster {


    /**
     * Create a new ResultApplicationEventMulticaster.
     */
    public ResultApplicationEventMulticaster() {
    }

    /**
     * Create a new ResultApplicationEventMulticaster for the given BeanFactory.
     */
    public ResultApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }


    @Override
    public void multicastEvent(ApplicationEvent event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void multicastEvent(final ApplicationEvent event, ResolvableType eventType) {
        throw new UnsupportedOperationException();
    }

    public Object multicastResultEvent(final ApplicationEvent event, ResolvableType eventType) {
        List<Object> result = new ArrayList<>();
        ResolvableType type = (eventType != null ? eventType : resolveDefaultEventType(event));
        for (final ApplicationListener<?> listener : getApplicationListeners(event, type)) {
            Object object;
            try {
                object = invokeListener(listener, event);
            } catch (ResultEventNoListeningException ex) {
                continue;
            }

            if (event instanceof PayloadApplicationResultEvent) {
                PayloadApplicationResultEvent resultEvent = (PayloadApplicationResultEvent) event;
                if (resultEvent.isMulti()) {
                    result.add(object);
                    continue;
                }
            }
            result.add(object);
            break;
        }

        if (CollectionUtils.isEmpty(result)) {
            throw new ApplicationContextException("not found listener");
        }

        return result.size() == 1 ? result.get(0) : result;
    }

    private ResolvableType resolveDefaultEventType(ApplicationEvent event) {
        return ResolvableType.forInstance(event);
    }

    /**
     * Invoke the given listener with the given event.
     *
     * @param listener the ApplicationListener to invoke
     * @param event    the current event to propagate
     * @since 4.1
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Object invokeListener(ApplicationListener listener, ApplicationEvent event) {
        if (listener instanceof ApplicationListenerResultMethodAdapter) {
            return ((ApplicationListenerResultMethodAdapter) listener).processEvent(event);
        }
        throw new ApplicationContextException("not invoke listener");
    }
}
