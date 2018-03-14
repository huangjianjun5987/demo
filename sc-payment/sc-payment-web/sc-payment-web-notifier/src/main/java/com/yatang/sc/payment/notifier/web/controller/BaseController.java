package com.yatang.sc.payment.notifier.web.controller;

import com.yatang.sc.payment.notifier.web.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @描述: BaseController.
 * @作者: huangjianjun
 * @创建时间: 2017年5月19日-下午2:28:40 .
 * @版本: 1.0 .
 */
public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @InitBinder()
    public void initBinder(WebDataBinder binder) {
        // 注册自定义的属性编辑器
        // 1、日期
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        // 表示如果命令对象有Date类型的属性，将使用该属性编辑器进行类型转换
        binder.registerCustomEditor(Date.class, dateEditor);
        // 自定义的电话号码编辑器(和【4.16.1、数据类型转换】一样)
    }

    /**
     * 获取客户端的ip地址
     */
    protected static String getIp(HttpServletRequest req) {
        String ip = req.getHeader("x-forwarded-for") == null ? req.getHeader("X-Forwarded-For") : req
                .getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "0:0:0:0:0:0:0:1".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取当前的openid
     */
    protected static String getCurrentOpenid(HttpServletRequest req) {
        String openid = req.getParameter("openid");
        if (StringUtils.isBlank(openid)) {
            openid = (String) req.getSession().getAttribute("openid");
        }
        return openid;
    }

    /**
     * 从uri中找出参数
     *
     * @param request
     * @param method
     * @return
     */
    protected static String getParameterByUri(HttpServletRequest request, String method) {
        String uri = request.getRequestURI();
        int b = uri.indexOf(method);
        int e = uri.indexOf('.', b);
        return uri.substring(b + method.length(), e);
    }

    /**
     * 向客户端返回json或者jsonp数据，支持ajax跨域请求 修改成不往上抛异常
     *
     * @param result
     * @param req
     * @param res
     */
    protected void writeToView(Object result, HttpServletRequest req, HttpServletResponse res) {
        try {
            if (result != null) {
                res.setHeader("Pragma", "No-cache");
                res.setHeader("Cache-Control", "no-cache");
                res.setDateHeader("Expires", 0);
                res.setContentType("text/json");
                res.setCharacterEncoding("UTF-8");
                PrintWriter out = res.getWriter();
                String callback = req.getParameter("callback");
                String json = "";
                if (result instanceof String) {
                    json = (String) result;
                } else {
                    json = JsonUtil.beanToJsonStrFormat(result);
                }
                // 是否为跨域请求，如果是，则拼接jsonp数据
                if (StringUtils.isNotBlank(callback)) {
                    json = callback + "(" + json + ")";
                }
                out.write(json);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            logger.info("json writeToView 出错!", e);
        }
    }

    /**
     * @param result
     * @param request
     * @param response
     * @Description:返回客户端数据
     * @author huangjianjun
     * @date 2017年5月19日 下午2:30:05
     */
    protected void responseJson(Object result, HttpServletRequest request, HttpServletResponse response) {
        writeToView(result, request, response);
    }

    /**
     * 向客户端返回json数据
     */
    public static void responseJson(Object result, HttpServletResponse res) throws Exception {
        if (result != null) {
            res.setContentType("text/json");
            res.setCharacterEncoding("UTF-8");
            PrintWriter out = res.getWriter();
            out.write(JsonUtil.beanToJsonStr(result));
            out.flush();
            out.close();
        }
    }
}
