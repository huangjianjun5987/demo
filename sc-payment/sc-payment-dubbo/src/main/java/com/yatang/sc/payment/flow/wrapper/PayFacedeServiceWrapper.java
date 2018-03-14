package com.yatang.sc.payment.flow.wrapper;

import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.flow.PayFacedeService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class PayFacedeServiceWrapper implements ApplicationContextAware {

    private ApplicationContext mApplicationContext;

    public PayFacedeService wrap(PayType pPayType) {
        switch (pPayType) {
            case weixin: {
                return mApplicationContext.getBean("weiXinPayFacedeService", PayFacedeService.class);
            }
            case alipay: {
                return mApplicationContext.getBean("aliPayPayFacedeService", PayFacedeService.class);
            }
            case ytpay: {
                return mApplicationContext.getBean("ytPayFacedeService", PayFacedeService.class);
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

    @Override
    public void setApplicationContext(ApplicationContext pApplicationContext) throws BeansException {
        this.mApplicationContext = pApplicationContext;
    }
}
