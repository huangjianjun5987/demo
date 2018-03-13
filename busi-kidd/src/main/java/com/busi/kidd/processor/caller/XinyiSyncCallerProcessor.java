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
import com.busi.kidd.serialize.xml.XmlDataBean;
import okhttp3.HttpUrl;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 同步调用逻辑处理类<br>
 * 基于md5+json+http组成
 *
 * @author yangqingsong
 */
public class XinyiSyncCallerProcessor implements KiddCallerProcessor<KiddCallerProcessorBean> {

    private KiddDataSerialization<XmlDataBean> dataSerialization;
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
        if (!InterfaceProviderTypeEnum.XINYI.equals(callerBean.getInterfaceProviderTypeEnum())) {
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
        Map<String, String> request = Collections.emptyMap();
        try {
            // 序列化
            XmlDataBean xmlDataBean = new XmlDataBean();
            xmlDataBean.setRootAlias("request");
            xmlDataBean.setData(wrapper.getData());
            dataSerialization.serialize(xmlDataBean);

            // 封装传输数据,并执行调用请求
            request = packRequest(callerBean, xmlDataBean);
            recordBean.setRequest(request);
            CallerProtocolBean<Map<String, String>> interfaceCallerBean = new CallerProtocolBean<Map<String, String>>();
            interfaceCallerBean.setData(request);
            interfaceCallerBean.setKiddCallerBean(callerBean);
            String responseJson = callerProtocol.call(interfaceCallerBean);
            recordBean.setResponse(responseJson);

            // 反序列化
            XmlDataBean responseJsonBean = new XmlDataBean();
            responseJsonBean.setRootAlias("response");
            responseJsonBean.setXmlStr(responseJson);
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
            HttpUrl.Builder urlBuilder = HttpUrl.parse(callerBean.getUrl()).newBuilder();
            for (String key : request.keySet()) {
                if ("xinyi_data".equals(key)) {
                    continue;
                }
                urlBuilder.addQueryParameter(key, request.get(key));
            }
            recordBean.setInterfaceName(urlBuilder.build().toString());
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
    private Map<String, String> packRequest(KiddCallerBean callerBean, XmlDataBean dataBean) throws Exception {
        Map<String, String> map = new HashMap<>();

        map.put("method", callerBean.getMethod());
        map.put("timestamp", getDateFormat().format(new Date()));
        map.put("format", "xml");
        map.put("app_key", kiddSetting.getAppKey());
        map.put("v", "1.0");
        map.put("sign_method", "md5");
        map.put("customerId", kiddSetting.getCustomerId());
        map.put("xinyi_data", dataBean.getXmlStr());
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

    public KiddDataSerialization<XmlDataBean> getDataSerialization() {
        return dataSerialization;
    }

    public void setDataSerialization(KiddDataSerialization<XmlDataBean> dataSerialization) {
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
