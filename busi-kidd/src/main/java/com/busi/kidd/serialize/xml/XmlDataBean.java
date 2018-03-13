package com.busi.kidd.serialize.xml;

import com.busi.kidd.serialize.DataBean;

import java.lang.reflect.Type;

/**
 * xml格式数据bean
 *
 * @author yipeng
 */
public class XmlDataBean implements DataBean {
    // json字符串
    private String xmlStr;
    // 数据
    private Object data;
    // 数据的数据类型,供反序列化时使用
    private Type dataClass;
    // 跟别名
    private String rootAlias;

    public String getXmlStr() {
        return xmlStr;
    }

    public void setXmlStr(String xmlStr) {
        this.xmlStr = xmlStr;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Type getDataClass() {
        return dataClass;
    }

    public void setDataClass(Type dataClass) {
        this.dataClass = dataClass;
    }

    public String getRootAlias() {
        return rootAlias;
    }

    public void setRootAlias(String rootAlias) {
        this.rootAlias = rootAlias;
    }
}
