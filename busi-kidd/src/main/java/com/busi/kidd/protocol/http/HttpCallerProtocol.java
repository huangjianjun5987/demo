package com.busi.kidd.protocol.http;

import com.busi.kidd.bean.KiddCallerBean;
import com.busi.kidd.protocol.CallerProtocolBean;
import com.busi.kidd.protocol.KiddCallerProtocol;
import okhttp3.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Http类型<br>
 *
 * @author yangqingsong
 *
 */
public class HttpCallerProtocol implements KiddCallerProtocol<Map<String, String>, String> {
    private static final Logger logger = LoggerFactory.getLogger(HttpCallerProtocol.class);

    private final static OkHttpClient client = new OkHttpClient();

    /**
     * 执行调用逻辑
     */
    @Override
    public String call(CallerProtocolBean<Map<String, String>> interfaceCallerBean) {

        KiddCallerBean callerBean = interfaceCallerBean.getKiddCallerBean();
        Map<String, String> data = interfaceCallerBean.getData();
        if(MapUtils.isEmpty(data)){
            return null;
        }

        FormBody.Builder formBuilder = new FormBody.Builder();
        for(String key:data.keySet()){
            if(StringUtils.isEmpty(key)){
                continue;
            }
            formBuilder.add(key,data.get(key));
        }
        FormBody formBody=formBuilder.build();

        Request request = new Request.Builder()
                .url(callerBean.getUrl())
                .post(formBody).build();

        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            logger.debug("请求成功:{}", result);
            return result;
        } catch (IOException e) {
            logger.error("请求失败:", e);
        }

        return null;
    }
}