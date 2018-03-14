package com.yatang.sc.app.purchase.action;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.app.action.AppBaseAction;
import com.yatang.sc.app.vo.payment.PrePayRequestVO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/sc/pay")
public class PaymentAction extends AppBaseAction {
    private Logger mLogger = LoggerFactory.getLogger(PaymentAction.class);
    private static final OkHttpClient client = new OkHttpClient();

    /**
     * 查询订单明细
     *
     * @param pPrePayVO
     * @return
     */
    @RequestMapping(value = "/prePay", method = RequestMethod.POST)
    public Response preInfo(@org.springframework.web.bind.annotation.RequestBody PrePayRequestVO pPrePayVO) {

        MediaType json = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(json, JSON.toJSONString(pPrePayVO));
        Request request = new Request.Builder()
                .addHeader("charset", "ISO8859-1")
                .url("http://localhost:8081/sc/pay/prePay")
                .post(requestBody)
                .build();

        try {
            okhttp3.Response okResponse = client.newCall(request).execute();
            if (okResponse.isSuccessful()) {
                return JSON.parseObject(okResponse.body().bytes(), Response.class);
            }
        } catch (IOException pE) {
            mLogger.error("获取预支付信息异常", pE);
        }
        return new Response();
    }
}
