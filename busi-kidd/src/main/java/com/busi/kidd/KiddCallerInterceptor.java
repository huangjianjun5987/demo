package com.busi.kidd;

import com.busi.kidd.annotation.KiddCaller;
import com.busi.kidd.bean.KiddCallerBean;
import com.busi.kidd.bean.KiddCallerBean.Null;
import com.busi.kidd.processor.KiddCallerProcessorBean;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Caller拦截器(代理器)<br>
 * 对com.busi.kidd.annotation.Caller做切面
 *
 * @author yangqingsong
 *
 */
@Aspect
public class KiddCallerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(KiddCallerInterceptor.class);
	// 存放内容
	private Map<Method, KiddCallerBean> callerBeanMap = new ConcurrentHashMap<Method, KiddCallerBean>();

	private KiddSetting kiddSetting;
	private KiddSetting kiddSettingOfXinyi;
	private KiddSetting kiddSettingOfJuban;
	/**
	 * 拦截方法,并在次类中做业务逻辑处理
	 *
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.busi.kidd.annotation.KiddCaller)")
	public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
		KiddCallerBean bean = getCallerInfo(pjp);
		if (bean instanceof Null) {
			logger.error("无法连接次方法.pjp:" + pjp);
			return pjp.proceed();
		}

		// 执行拦截操作
		KiddCallerProcessorBean requestWrapper = new KiddCallerProcessorBean();
		requestWrapper.setCallerBean(bean);
		requestWrapper.setData(pjp.getArgs()[0]);
		Object result = KiddFrameKernel.INSTANCE.process(requestWrapper);
		return result;
	}

	/**
	 * 获取callerbean
	 *
	 * @param pjp
	 * @return
	 */
	protected KiddCallerBean getCallerInfo(ProceedingJoinPoint pjp) {
		KiddCallerBean bean = null;
		Signature sg = pjp.getSignature();
		if (sg instanceof MethodSignature) {
			MethodSignature msg = (MethodSignature) sg;
			Method method = msg.getMethod();
			bean = callerBeanMap.get(method);
			if (null == bean) { // 初始化
				synchronized (method) { // 同步操作
					if (null == bean) {
						try {
							// 通过原始类获取
							Class<?> targetClass = pjp.getTarget().getClass();
							method = targetClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
							KiddCaller annotation = method.getAnnotation(KiddCaller.class);
							if (null != annotation) {
								bean = new KiddCallerBean();
								bean.setInterfaceProviderTypeEnum(annotation.providerType());
								bean.setCallType(annotation.callType());
								if(StringUtils.isEmpty(annotation.url())){
									switch (bean.getInterfaceProviderTypeEnum()) {
										case GLINK:
											bean.setUrl(kiddSetting.getURL());
											break;
										case XINYI:
											bean.setUrl(kiddSettingOfXinyi.getURL());
											break;
										case JUBAN:
											bean.setUrl(kiddSettingOfJuban.getURL());
											break;
                                        default:
                                            bean.setUrl(kiddSetting.getURL());
                                            break;
									}

								}else {
									bean.setUrl(annotation.url());
								}
								bean.setMethod(annotation.method());
								bean.setReCallCount(annotation.reCallCount());
								// bean.setAsyncReceiptWaitTime(annotation.asyncReceiptWaitTime());
								// bean.setCallbackMethod(annotation.callbackMethod());
								bean.setResultClass(method.getGenericReturnType());
							} else {
								bean = new Null();
								logger.error("拦截方法没有定义Caller中的基本数据.pjp:" + pjp);
							}
						} catch (Exception e) {
							logger.error("获取方法上枚举定义的属性时异常." + pjp);
							bean = new Null();
						}
						callerBeanMap.put(method, bean);
					}
				}
			}
		} else {
			// 使用本方法
			try {
				Method method = this.getClass().getDeclaredMethod("getCallerInfo", pjp.getClass());
				bean = callerBeanMap.get(method);
				if (null == bean) { // 初始化
					synchronized (method) {
						if (null == bean) {
							bean = new Null();
							callerBeanMap.put(method, bean);
						}
					}
				}
				logger.error("根据传入的对象无法获取方法.pjp:" + pjp);
			} catch (Exception e) {
				logger.error("获取类的getCallerInfo方法报错", e);
			}
		}

		return bean;
	}

	public KiddSetting getKiddSetting() {
		return kiddSetting;
	}

	public void setKiddSetting(KiddSetting kiddSetting) {
		this.kiddSetting = kiddSetting;
	}

	public KiddSetting getKiddSettingOfXinyi() {
		return kiddSettingOfXinyi;
	}

	public void setKiddSettingOfXinyi(KiddSetting kiddSettingOfXinyi) {
		this.kiddSettingOfXinyi = kiddSettingOfXinyi;
	}

	public KiddSetting getKiddSettingOfJuban() {
		return kiddSettingOfJuban;
	}

	public void setKiddSettingOfJuban(KiddSetting kiddSettingOfJuban) {
		this.kiddSettingOfJuban = kiddSettingOfJuban;
	}
}

