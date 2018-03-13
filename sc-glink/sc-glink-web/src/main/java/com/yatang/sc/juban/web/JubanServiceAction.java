package com.yatang.sc.juban.web;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.kidd.KiddException;
import com.busi.kidd.security.md5.MD5Security;
import com.busi.kidd.serialize.KiddDataSerialization;
import com.busi.kidd.serialize.xml.XmlDataBean;
import com.busi.kidd.serialize.xml.XmlDataSerialization;
import com.google.common.base.Throwables;
import com.yatang.sc.juban.dto.JubanConstants;
import com.yatang.sc.kidd.dto.ProviderRequestDto;
import com.yatang.sc.kidd.service.KiddFacadeService;
import com.yatang.sc.xinyi.dto.ResponseDto;

import okhttp3.HttpUrl;

/**
 * @描述:桔瓣回调服务接口
 * @类名:JubanServiceAction
 * @作者: lvheping
 * @创建时间: 2017/11/24 14:58
 * @版本: v1.0
 */

@Controller
public class JubanServiceAction {
    private static final Logger log = LoggerFactory.getLogger( JubanServiceAction.class );
    private static final Charset DEFAULT_CHARSET_NAME = Charset.forName("UTF-8");
    private static final String DEFAULT_URL = "http://118.112.177.36:58080/sc-glink-web/service/juban";

    @Autowired
    private KiddFacadeService kiddFacadeService;
    @Autowired
    private XmlDataSerialization xmlDataSerialization;
    @Resource(name = "juban.md5Security")
    private MD5Security md5Security;
    @Resource(name = "xmlSerialization")
    private KiddDataSerialization<XmlDataBean> dataSerialization;

    @RequestMapping(value = "/service/juban")
    public void gateway(HttpServletRequest request, HttpServletResponse response) throws IOException, KiddException {
        ResponseDto dto = new ResponseDto();
        try {
            Map<String, String> map = check(request);

            String method = map.get("method");
            String body = map.get("xinyi_data");

            // 反序列化请求对象
            XmlDataBean dataBean = new XmlDataBean();
            dataBean.setRootAlias("request");
            dataBean.setXmlStr(body);
            dataBean.setDataClass(JubanConstants.provders.get(method));
            xmlDataSerialization.deserialize(dataBean);

            // 发送请求处理
            ProviderRequestDto providerDto = new ProviderRequestDto();
            providerDto.setApiMethod(method);
            providerDto.setPayload(dataBean.getData());

            log.info("juban service geteway dataBean:{},providerDto:{}", dataBean.getDataClass(), JSON.toJSON(providerDto));
            Response kiddResponse = (Response) kiddFacadeService.send(providerDto);
            log.info("juban service geteway response:{}", JSON.toJSON(kiddResponse));

            dto.setCode(kiddResponse.getCode());
            dto.setFlag(kiddResponse.isSuccess() ? ResponseDto.SUCCESS : ResponseDto.FAILURE);
            dto.setMessage(kiddResponse.getErrorMessage());

        } catch (Exception e) {
            dto.setCode("002");
            dto.setFlag(ResponseDto.FAILURE);
            dto.setMessage(Throwables.getRootCause(e).getMessage());

            log.error("juban service geteway error:" + JSON.toJSON(dto), e);

        }
        out(dto, response);
    }

    private void out(ResponseDto dto, HttpServletResponse response) throws IOException, KiddException {
        XmlDataBean dataBean = new XmlDataBean();
        dataBean.setRootAlias("response");
        dataBean.setData(dto);
        dataSerialization.serialize(dataBean);
        response.setContentType(MediaType.APPLICATION_XML_VALUE);
        response.getOutputStream().write(dataBean.getXmlStr().getBytes(DEFAULT_CHARSET_NAME));
        response.getOutputStream().flush();
    }

    private Map<String, String> check(HttpServletRequest request) throws KiddException, IOException {
        Map<String, String> map = new HashMap<>();
        try {
            String body = new String(StreamUtils.copyToByteArray(request.getInputStream()),"UTF-8");
            map.put("xinyi_data", body);

            map.put("method", ServletRequestUtils.getRequiredStringParameter(request, "method"));
            map.put("timestamp", ServletRequestUtils.getRequiredStringParameter(request, "timestamp"));
            map.put("format", ServletRequestUtils.getRequiredStringParameter(request, "format"));
            map.put("app_key", ServletRequestUtils.getRequiredStringParameter(request, "app_key"));
            map.put("v", ServletRequestUtils.getRequiredStringParameter(request, "v"));
            map.put("sign_method", ServletRequestUtils.getRequiredStringParameter(request, "sign_method"));
            map.put("customerId", ServletRequestUtils.getRequiredStringParameter(request, "customerId"));
            map.put("sign", ServletRequestUtils.getRequiredStringParameter(request, "sign"));
        } catch (ServletRequestBindingException e) {
            if (e instanceof MissingServletRequestParameterException) {
                throw new KiddException("参数" + ((MissingServletRequestParameterException) e).getParameterName() + "校验失败.");
            }
            throw new KiddException("参数校验失败.");
        }

        String method = map.get("method");
        String body = map.get("xinyi_data");
        HttpUrl.Builder urlBuilder = HttpUrl.parse(DEFAULT_URL).newBuilder();
        for (String key : map.keySet()) {
            if ("xinyi_data".equals(key)) {
                continue;
            }
            urlBuilder.addQueryParameter(key, map.get(key));
        }
        log.info("juban service geteway method:{},url:{},body:{}", method, urlBuilder.build().toString(), body);

        String serverSign;
        try {
            serverSign = md5Security.sign(map);
        } catch (Exception e) {
            throw new KiddException("数字签名失败.");
        }
        String inputSign = map.get("sign");
        if (!StringUtils.equals(serverSign, inputSign)) {
            throw new KiddException("数字签名校验失败.服务器签名:" + serverSign + ",客户端签名:" + inputSign);
        }

        if (!JubanConstants.provders.containsKey(method)) {
            throw new KiddException("API方法不存在.");
        }
        return map;
    }

}
