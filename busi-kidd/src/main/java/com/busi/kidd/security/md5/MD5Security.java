package com.busi.kidd.security.md5;

import com.busi.kidd.KiddSetting;
import com.busi.kidd.security.KiddSecurity;
import org.apache.commons.collections4.MapUtils;

import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

/**
 * MD5数字签名安全类
 *
 * @author yangqingsong
 */
public class MD5Security implements KiddSecurity {

    private KiddSetting kiddSetting;

    @Override
    public String sign(Map<String, String> params) throws Exception {
        if (MapUtils.isEmpty(params)) {
            return null;
        }

        TreeMap<String, String> sortedParams = new TreeMap<String, String>(params);
        // 将secret同时放在头尾，拼成字符串
        String secret = kiddSetting.getAppSecret();
        StringBuilder source = new StringBuilder(secret);
        for (String key : sortedParams.keySet()) {
            if ("sign".equals(key)) {
                continue;
            }
            if ("xinyi_data".equals(key)) {
                source.append(sortedParams.get(key));
                continue;
            }
            source.append(key).append(sortedParams.get(key));
        }
        source.append(secret);
        return encode(source.toString());
    }

    @Override
    public String encode(String str) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return byte2hex(md.digest(str.getBytes("utf-8")));
    }

    /**
     * 二进制转字符串
     *
     * @param b
     * @return
     */
    private String byte2hex(byte[] b) {

        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs.append("0").append(stmp);
            else
                hs.append(stmp);
        }

        return hs.toString().toUpperCase();
    }

    public KiddSetting getKiddSetting() {
        return kiddSetting;
    }

    public void setKiddSetting(KiddSetting kiddSetting) {
        this.kiddSetting = kiddSetting;
    }
}
