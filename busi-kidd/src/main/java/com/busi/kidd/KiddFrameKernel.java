package com.busi.kidd;

import java.util.ArrayList;
import java.util.List;

import com.busi.kidd.processor.KiddCallerProcessor;
import com.busi.kidd.processor.KiddCallerProcessorBean;
import com.busi.kidd.processor.KiddProviderProcessor;
import com.busi.kidd.processor.KiddProviderProcessorBean;

/**
 * 框架核心类<br>
 *
 * 
 * @author yangqingsong
 *
 */
public enum KiddFrameKernel {
	INSTANCE;
	// 存放支持的chain,基于责任链模式
	private List<KiddCallerProcessor<KiddCallerProcessorBean>> callerProcessor = new ArrayList<KiddCallerProcessor<KiddCallerProcessorBean>>();
	// 存放支持的chain,基于责任链模式
	private List<KiddProviderProcessor<KiddProviderProcessorBean>> providerProcessor = new ArrayList<KiddProviderProcessor<KiddProviderProcessorBean>>();

	private KiddFrameKernel() {
	}

	/**
	 * 注册chain信息
	 */
	public void registry(KiddCallerProcessor<KiddCallerProcessorBean> chain) {
		callerProcessor.add(chain);
	}

	/**
	 * 注册chain信息
	 */
	public void registry(KiddProviderProcessor<KiddProviderProcessorBean> chain) {
		providerProcessor.add(chain);
	}

	/**
	 * 执行任务<br>
	 * 基于责任链模式
	 * 
	 * @param kiddWrapper
	 * @return
	 * @throws KiddException
	 */
	public Object process(KiddCallerProcessorBean kiddWrapper) throws KiddException {
		Object result = null;
		for (KiddCallerProcessor<KiddCallerProcessorBean> processor : callerProcessor) {
			if (processor.isProcess(kiddWrapper.getCallerBean())) {
				result = processor.process(kiddWrapper);
				break;
			}
		}
		return result;
	}

	/**
	 * 执行任务<br>
	 * 基于责任链模式
	 * 
	 * @param kiddWrapper
	 * @return
	 * @throws KiddException
	 */
	public void preProcess(KiddProviderProcessorBean kiddWrapper) throws KiddException {
		for (KiddProviderProcessor<KiddProviderProcessorBean> processor : providerProcessor) {
			if (processor.isProcess(kiddWrapper.getProviderBean())) {
				processor.preProcess(kiddWrapper);
				break;
			}
		}
	}

	/**
	 * 执行任务<br>
	 * 基于责任链模式
	 * 
	 * @param kiddWrapper
	 * @return
	 * @throws KiddException
	 */
	public void postProcess(KiddProviderProcessorBean kiddWrapper) throws KiddException {
		for (KiddProviderProcessor<KiddProviderProcessorBean> processor : providerProcessor) {
			if (processor.isProcess(kiddWrapper.getProviderBean())) {
				processor.postProcess(kiddWrapper);
				break;
			}
		}
	}

}
