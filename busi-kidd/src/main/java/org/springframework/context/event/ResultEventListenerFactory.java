package org.springframework.context.event;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;

public class ResultEventListenerFactory implements EventListenerFactory, Ordered {

    private int order = LOWEST_PRECEDENCE;

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean supportsMethod(Method method) {
        return true;
    }

    @Override
    public ApplicationListener<?> createApplicationListener(String beanName, Class<?> type, Method method) {
        return new ApplicationListenerResultMethodAdapter(beanName, type, method);
    }
}
