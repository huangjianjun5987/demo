package com.busi.kidd.protocol.http;

import com.busi.kidd.bean.KiddCallerBean;
import com.busi.kidd.protocol.CallerProtocolBean;
import com.busi.kidd.protocol.KiddCallerProtocol;
import okhttp3.*;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Http类型<br>
 *
 * @author yipeng
 */
public class XinyiHttpCallerProtocol implements KiddCallerProtocol<Map<String, String>, String> {
    private static final Logger logger = LoggerFactory.getLogger(XinyiHttpCallerProtocol.class);

    private final static OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).build();

    /**
     * 执行调用逻辑
     */
    @Override
    public String call(CallerProtocolBean<Map<String, String>> interfaceCallerBean) {

        KiddCallerBean callerBean = interfaceCallerBean.getKiddCallerBean();
        Map<String, String> data = interfaceCallerBean.getData();
        if (MapUtils.isEmpty(data)) {
            return null;
        }

        HttpUrl.Builder urlBuilder = HttpUrl.parse(callerBean.getUrl()).newBuilder();
        for (String key : data.keySet()) {
            if ("xinyi_data".equals(key)) {
                continue;
            }
            urlBuilder.addQueryParameter(key, data.get(key));
        }

        RequestBody body = RequestBody.create(MediaType.parse(""), data.get("xinyi_data"));

        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .post(body).build();


        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            logger.debug("请求成功:{}", result);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
