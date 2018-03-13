package com.busi.kidd.processor.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.kidd.KiddException;
import com.busi.kidd.KiddFrameKernel;
import com.busi.kidd.annotation.ProviderTypeEnum;
import com.busi.kidd.bean.KiddProviderBean;
import com.busi.kidd.behavior.KiddBehaviorRecord;
import com.busi.kidd.behavior.RecordBean;
import com.busi.kidd.processor.KiddProviderProcessor;
import com.busi.kidd.processor.KiddProviderProcessorBean;
import com.busi.kidd.security.KiddSecurity;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认实现类
 *
 * @author yangqingsong
 */
public class StandardProviderProcessor implements KiddProviderProcessor<KiddProviderProcessorBean> {
    private static final Logger logger = LoggerFactory.getLogger(StandardProviderProcessor.class);
    private KiddSecurity kiddSecurity;
    private KiddBehaviorRecord behaviorRecord;
    // 有效的时间范围,默认300s
    private int legalValidtimeSection = 300;

    public int getLegalValidTimeSection() {
        return legalValidtimeSection;
    }

    public void setLegalValidTimeSection(int legalValidtimeSection) {
        this.legalValidtimeSection = legalValidtimeSection;
    }

    public KiddSecurity getKiddSecurity() {
        return kiddSecurity;
    }

    public void setKiddSecurity(KiddSecurity kiddSecurity) {
        this.kiddSecurity = kiddSecurity;
    }

    public KiddBehaviorRecord getBehaviorRecord() {
        return behaviorRecord;
    }

    public void setBehaviorRecord(KiddBehaviorRecord behaviorRecord) {
        this.behaviorRecord = behaviorRecord;
    }

    @Override
    public boolean isProcess(KiddProviderBean providerBean) {
        if (ProviderTypeEnum.MD5.equals(providerBean.getProviderType())) {
            return true;
        }
        return false;
    }

    @Override
    public void init() {
        KiddFrameKernel.INSTANCE.registry(this);
    }

    /**
     * 执行数字签名安全验证
     */
    @Override
    public void preProcess(KiddProviderProcessorBean wrapper) throws KiddException {
        boolean recordFlag = true;
        String errorMsg = null;
        try {
            // 判断签名正确性
            String requestjson = JSON.toJSONString(wrapper.getRequest());
            JSONObject json = JSON.parseObject(requestjson);
            Map<String, String> map = new HashMap<String, String>();
            if (MapUtils.isEmpty(json)) {
                throw new KiddException("请求参数为空.");
            } else {
                for (String key : json.keySet()) {
                    if (null == json.get(key)) {
                        continue;
                    }
                    map.put(key, json.get(key).toString());
                }
            }
            if (MapUtils.isEmpty(map)) {
                throw new KiddException("请求参数为空.");
            }
            String sign = kiddSecurity.sign(map);
            String inputSign = map.get("sign");
            logger.info("获取的签名:{}和yt签名:{}",inputSign, sign );
            if (!StringUtils.equals(sign, inputSign)) {
                throw new KiddException("数字签名校验失败.");
            }

            recordFlag = false;
        } catch (KiddException e) {
            errorMsg = e.getMessage();
            throw e;
        } catch (Exception e) {
            errorMsg = e.getMessage();
            throw new KiddException("前置处理是异常.", e);
        } finally {
            if (recordFlag) { // 记录日志
                record(wrapper, RecordBean.STATUS_FAILURES, errorMsg);
            }
        }
    }

    /**
     * 只做行为记录
     */
    @Override
    public void postProcess(KiddProviderProcessorBean wrapper) {
        try {
            // 记录本次处理信息
            record(wrapper, RecordBean.STATUS_SUCCESS, null);
        } catch (Exception e) {
            logger.error("postProcess时报错.", e);
        }
    }

    /**
     * 记录履历
     *
     * @param wrapper
     * @param status
     * @param errorMsg
     */
    protected void record(KiddProviderProcessorBean wrapper, String status, String errorMsg) {
        RecordBean recordBean = new RecordBean();
        // 记录信息
        recordBean.setInterfaceName(wrapper.getProviderBean().getInterfaceName());
        recordBean.setRequest(wrapper.getRequest());
        recordBean.setResponse(wrapper.getResponse());
        recordBean.setRecordDate(new Date());
        recordBean.setStatus(status);
        recordBean.setErrorMsg(errorMsg);
        behaviorRecord.record(recordBean);
    }
}
