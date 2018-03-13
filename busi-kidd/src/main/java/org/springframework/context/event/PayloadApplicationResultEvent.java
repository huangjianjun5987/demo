package org.springframework.context.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;
import org.springframework.util.Assert;

@SuppressWarnings("serial")
public class PayloadApplicationResultEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {

    private final T payload;
    private final boolean isMulti;


    public PayloadApplicationResultEvent(Object source, T payload) {
        super(source);
        Assert.notNull(payload, "Payload must not be null");
        this.payload = payload;
        this.isMulti = false;
    }

    public PayloadApplicationResultEvent(Object source, T payload, boolean isMulti) {
        super(source);
        Assert.notNull(payload, "Payload must not be null");
        this.payload = payload;
        this.isMulti = isMulti;
    }


    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(getPayload()));
    }

    /**
     * Return the payload of the event.
     */
    public T getPayload() {
        return this.payload;
    }

    public boolean isMulti() {
        return isMulti;
    }
}
