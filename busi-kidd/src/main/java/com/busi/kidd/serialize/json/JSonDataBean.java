package com.busi.kidd.serialize.json;

import com.busi.kidd.serialize.DataBean;

import java.lang.reflect.Type;

/**
 * json格式数据bean
 *
 * @author yangqingsong
 */
public class JSonDataBean implements DataBean {
    // json字符串
    private String jsonStr;
    // 数据
    private Object data;
    // 数据的数据类型,供反序列化时使用
    private Type dataClass;

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
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

}
