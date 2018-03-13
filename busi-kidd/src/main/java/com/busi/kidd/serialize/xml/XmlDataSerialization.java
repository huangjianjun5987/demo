package com.busi.kidd.serialize.xml;

import com.busi.kidd.KiddException;
import com.busi.kidd.serialize.KiddDataSerialization;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.thoughtworks.xstream.security.RegExpTypePermission;

import java.io.*;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * xml格式序列化
 *
 * @author yipeng
 */
public class XmlDataSerialization implements KiddDataSerialization<XmlDataBean> {


    private static final Charset DEFAULT_CHARSET_NAME = Charset.forName("UTF-8");
//    private final XStream xStream;

    public XmlDataSerialization() {

//        String[] patters = {"(.*)RequestDto", "(.*)ResponseDto"};
//        xStream.addPermission(new RegExpTypePermission(patters));
    }

    /**
     * 序列化数据
     */
    @Override
    public void serialize(XmlDataBean dataBean) throws KiddException {
        if (null != dataBean.getXmlStr()) {
            return;
        }

        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        xStream.ignoreUnknownElements();
        xStream.addPermission(new AnyTypePermission());
        xStream.alias(dataBean.getRootAlias(), dataBean.getData().getClass());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        xStream.toXML(dataBean.getData(), new OutputStreamWriter(baos, DEFAULT_CHARSET_NAME));

        String xml = new String(baos.toByteArray(), DEFAULT_CHARSET_NAME);
        Pattern p = Pattern.compile("\\s{2,}|\t|\r|\n");
        Matcher m = p.matcher(xml);

//        dataBean.setXmlStr("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + m.replaceAll(""));
        dataBean.setXmlStr(m.replaceAll(""));
    }

    /**
     * 反序列化数据
     */
    @Override
    public void deserialize(XmlDataBean dataBean) throws KiddException {
        if (null != dataBean.getData()) {
            return;
        }
        String jsonStr = dataBean.getXmlStr();
// 际链服务停止 返回message处理
//        if (jsonStr.contains("<message>")) {
//            String messageStr = jsonStr.substring(jsonStr.indexOf("<message>") + 9, jsonStr.lastIndexOf("</message>"));
//            String newMessageStr = messageStr.replace("<", "\\u003c").replace(">", "\\u003e");
//            jsonStr = jsonStr.replace(messageStr, newMessageStr);
//        }

        if (jsonStr.contains("{message}")) {
            String messageStr = jsonStr.substring(jsonStr.indexOf("<message>") + 9, jsonStr.lastIndexOf("</message>"));
            String newMessageStr = messageStr.replace("<", "\\u003c").replace(">", "\\u003e");
            jsonStr = jsonStr.replace(messageStr, newMessageStr);
        }

        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        xStream.ignoreUnknownElements();
        xStream.addPermission(new AnyTypePermission());
        xStream.alias(dataBean.getRootAlias(), (Class) dataBean.getDataClass());
        InputStream serializedData = new ByteArrayInputStream(jsonStr.getBytes(DEFAULT_CHARSET_NAME));
        Object data = xStream.fromXML(new InputStreamReader(serializedData, DEFAULT_CHARSET_NAME));

        dataBean.setData(data);
    }

}
