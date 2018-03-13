package com.busi.kidd.serialize.json;

import com.alibaba.fastjson.JSON;
import com.busi.kidd.KiddException;
import com.busi.kidd.serialize.KiddDataSerialization;

import java.lang.reflect.Type;

/**
 * json格式序列化
 *
 * @author yangqingsong
 */
public class JSonDataSerialization implements KiddDataSerialization<JSonDataBean> {

    /**
     * 序列化数据
     */
    @Override
    public void serialize(JSonDataBean dataBean) throws KiddException {
        if (null != dataBean.getJsonStr()) {
            return;
        }
        String jsonStr = JSON.toJSONString(dataBean.getData());
        dataBean.setJsonStr(jsonStr);
    }

    /**
     * 反序列化数据
     */
    @Override
    public void deserialize(JSonDataBean dataBean) throws KiddException {
        if (null != dataBean.getData()) {
            return;
        }
        String jsonStr = dataBean.getJsonStr();
        Type classes = dataBean.getDataClass();
        Object data = JSON.parseObject(jsonStr, classes);
        dataBean.setData(data);
    }

}
