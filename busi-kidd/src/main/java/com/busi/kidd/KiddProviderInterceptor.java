package com.busi.kidd;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.busi.kidd.annotation.KiddProvider;
import com.busi.kidd.bean.KiddProviderBean;
import com.busi.kidd.bean.KiddProviderBean.Null;
import com.busi.kidd.processor.KiddProviderProcessorBean;

/**
 * Provider拦截器(代理器)<br>
 * 对com.busi.kidd.annotation.KiddProvider做切面
 *
 * @author yangqingsong
 */
@Aspect
public class KiddProviderInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(KiddProviderInterceptor.class);
    // 存放内容
    private Map<Method, KiddProviderBean> providerBeanMap = new ConcurrentHashMap<Method, KiddProviderBean>();

    /**
     * 拦截方法,并在次类中做业务逻辑处理
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.busi.kidd.annotation.KiddProvider)")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        KiddProviderBean bean = getProviderInfo(pjp);
        if (bean instanceof Null) {
            logger.error("无法连接次方法.pjp:" + pjp);
            return pjp.proceed();
        }

        // 执行拦截操作
        KiddProviderProcessorBean requestWrapper = new KiddProviderProcessorBean();
        requestWrapper.setProviderBean(bean);
        requestWrapper.setRequest(pjp.getArgs()[0]);
        KiddFrameKernel.INSTANCE.preProcess(requestWrapper);

        // 调用方法
        Object result = pjp.proceed();

        // 执行调用拦截之后操作
        requestWrapper.setResponse(result);
        KiddFrameKernel.INSTANCE.postProcess(requestWrapper);
        return result;
    }

    /**
     * 获取KiddProviderBean
     *
     * @param pjp
     * @return
     */
    protected KiddProviderBean getProviderInfo(ProceedingJoinPoint pjp) {
        KiddProviderBean bean = null;
        Signature sg = pjp.getSignature();
        if (sg instanceof MethodSignature) {
            MethodSignature msg = (MethodSignature) sg;
            Method method = msg.getMethod();
            bean = providerBeanMap.get(method);
            if (null == bean) { // 初始化
                synchronized (method) { // 同步操作
                    if (null == bean) {
                        try {
                            // 通过原始类获取
                            Class<?> targetClass = pjp.getTarget().getClass();
                            method = targetClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
                            KiddProvider annotation = method.getAnnotation(KiddProvider.class);
                            if (null != annotation) {
                                bean = new KiddProviderBean();
                                bean.setProviderType(annotation.providerType());
                                bean.setInterfaceName(targetClass.getName() + "." + method.getName());
                            } else {
                                bean = new Null();
                                logger.error("拦截方法没有定义Provider中的基本数据.pjp:" + pjp);
                            }
                        } catch (Exception e) {
                            logger.error("获取方法上枚举定义的属性时异常." + pjp);
                            bean = new Null();
                        }
                        providerBeanMap.put(method, bean);
                    }
                }
            }
        } else {
            // 使用本方法
            try {
                Method method = this.getClass().getDeclaredMethod("getProviderInfo", pjp.getClass());
                bean = providerBeanMap.get(method);
                if (null == bean) { // 初始化
                    synchronized (method) {
                        if (null == bean) {
                            bean = new Null();
                            providerBeanMap.put(method, bean);
                        }
                    }
                }
                logger.error("根据传入的对象无法获取方法.pjp:" + pjp);
            } catch (Exception e) {
                logger.error("获取类的getProviderInfo方法报错", e);
            }
        }

        return bean;
    }
}
