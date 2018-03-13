package com.busi.kidd.processor.caller;

import com.busi.kidd.KiddException;
import com.busi.kidd.KiddFrameKernel;
import com.busi.kidd.KiddSetting;
import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.busi.kidd.bean.KiddCallerBean;
import com.busi.kidd.behavior.KiddBehaviorRecord;
import com.busi.kidd.behavior.RecordBean;
import com.busi.kidd.processor.KiddCallerProcessor;
import com.busi.kidd.processor.KiddCallerProcessorBean;
import com.busi.kidd.protocol.CallerProtocolBean;
import com.busi.kidd.protocol.KiddCallerProtocol;
import com.busi.kidd.security.KiddSecurity;
import com.busi.kidd.serialize.KiddDataSerialization;
import com.busi.kidd.serialize.json.JSonDataBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 同步调用逻辑处理类<br>
 * 基于md5+json+http组成
 *
 * @author yangqingsong
 */
public class SyncCallerProcessor implements KiddCallerProcessor<KiddCallerProcessorBean> {

    private KiddDataSerialization<JSonDataBean> dataSerialization;
    private KiddSetting kiddSetting;
    private KiddSecurity kiddSecurity;
    private KiddBehaviorRecord behaviorRecord;
    private KiddCallerProtocol<Map<String, String>, String> callerProtocol;
    private static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>();

    @Override
    public void init() {
        KiddFrameKernel.INSTANCE.registry(this);
    }

    @Override
    public boolean isProcess(KiddCallerBean callerBean) {
        if (!InterfaceProviderTypeEnum.GLINK.equals(callerBean.getInterfaceProviderTypeEnum())) {
            return false;
        }
        if (CallTypeEnum.SYNC_CALL.equals(callerBean.getCallType())) {
            return true;
        }
        return false;
    }

    /**
     * 执行操作
     *
     * @param wrapper
     * @return
     * @throws KiddException
     */
    public Object process(KiddCallerProcessorBean wrapper) throws KiddException {
        Object result = null;
        KiddCallerBean callerBean = wrapper.getCallerBean();

        // 记录本次处理信息
        RecordBean recordBean = new RecordBean();
        try {
            // 序列化
            JSonDataBean jsonDataBean = new JSonDataBean();
            jsonDataBean.setData(wrapper.getData());
            dataSerialization.serialize(jsonDataBean);

            // 封装传输数据,并执行调用请求
            Map<String, String> request = packRequest(callerBean, jsonDataBean);
            recordBean.setRequest(request);
            CallerProtocolBean<Map<String, String>> interfaceCallerBean = new CallerProtocolBean<Map<String, String>>();
            interfaceCallerBean.setData(request);
            interfaceCallerBean.setKiddCallerBean(callerBean);
            String responseJson = callerProtocol.call(interfaceCallerBean);
            recordBean.setResponse(responseJson);

            // 反序列化
            JSonDataBean responseJsonBean = new JSonDataBean();
            responseJsonBean.setJsonStr(responseJson);
            responseJsonBean.setDataClass(callerBean.getResultClass());
            dataSerialization.deserialize(responseJsonBean);
            result = responseJsonBean.getData();

        } catch (KiddException e) {
            recordBean.setErrorMsg(e.getMessage());
            recordBean.setStatus(RecordBean.STATUS_FAILURES);
            throw e;
        } catch (Exception e) {
            recordBean.setErrorMsg(e.getMessage());
            recordBean.setStatus(RecordBean.STATUS_FAILURES);
            throw new KiddException("执行调用时报错.", e);
        } finally {
            String interfaceName = callerBean.getUrl() + (null == callerBean.getMethod() ? StringUtils.EMPTY : callerBean.getMethod());
            recordBean.setInterfaceName(interfaceName);
            recordBean.setRecordDate(new Date());
            behaviorRecord.record(recordBean);
        }
        return result;
    }

    /**
     * 封装请求数据
     *
     * @param callerBean
     * @param dataBean
     * @return
     */
    private Map<String, String> packRequest(KiddCallerBean callerBean, JSonDataBean dataBean) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        // 请求主体
        map.put("data", dataBean.getJsonStr());
        // 方法名
        map.put("method", callerBean.getMethod());
        // 时间戳
        map.put("timestamp", getDateFormat().format(new Date()));
        // appkey
        map.put("app_key", kiddSetting.getAppKey());
        // sign
        map.put("sign", kiddSecurity.sign(map));
        return map;

    }

    public static SimpleDateFormat getDateFormat() {
        if (null == dateFormatThreadLocal.get()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormatThreadLocal.set(sdf);
            return sdf;
        }
        return dateFormatThreadLocal.get();
    }

    public KiddDataSerialization<JSonDataBean> getDataSerialization() {
        return dataSerialization;
    }

    public void setDataSerialization(KiddDataSerialization<JSonDataBean> dataSerialization) {
        this.dataSerialization = dataSerialization;
    }

    public KiddSetting getKiddSetting() {
        return kiddSetting;
    }

    public void setKiddSetting(KiddSetting kiddSetting) {
        this.kiddSetting = kiddSetting;
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

    public KiddCallerProtocol<Map<String, String>, String> getCallerProtocol() {
        return callerProtocol;
    }

    public void setCallerProtocol(KiddCallerProtocol<Map<String, String>, String> callerProtocol) {
        this.callerProtocol = callerProtocol;
    }
}
