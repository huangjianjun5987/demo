package com.yatang.sc.payment.flow.wrapper;

import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.flow.RefundFacedeService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class RefundFacedeServiceWrapper implements ApplicationContextAware {
    private ApplicationContext mApplicationContext;

    public RefundFacedeService wrap(PayType pPayType) {
        switch (pPayType) {
            case alipay: {
                return mApplicationContext.getBean("alipayRefundFacedeService", RefundFacedeService.class);
            }
            case weixin: {
                return mApplicationContext.getBean("weixinRefundFacedeService", RefundFacedeService.class);
            }
            case ytpay: {
                return mApplicationContext.getBean("ytRefundFacedeService", RefundFacedeService.class);
            }
            case jd: {
            }
            case cmb: {
            }
            default: {
                throw new IllegalArgumentException("未知支付类型");
            }
        }
    }

    public RefundFacedeService tryWrap(PayType pPayType) {
        switch (pPayType) {
            case alipay: {
                return mApplicationContext.getBean("alipayRefundFacedeService", RefundFacedeService.class);
            }
            case weixin: {
                return mApplicationContext.getBean("weixinRefundFacedeService", RefundFacedeService.class);
            }
            case ytpay: {
                return mApplicationContext.getBean("ytRefundFacedeService", RefundFacedeService.class);
            }
            case jd: {
            }
            case cmb: {
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext pApplicationContext) throws BeansException {
        mApplicationContext = pApplicationContext;
    }
}
