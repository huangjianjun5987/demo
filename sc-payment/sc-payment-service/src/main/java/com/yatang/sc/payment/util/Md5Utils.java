package com.yatang.sc.payment.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * 主要功能：md5工具类
 */
public class Md5Utils {
    private static final Logger LOGGER = LoggerFactory.getLogger(Md5Utils.class.getName());

    /**
     * 功能说明：md5加密
     *
     * @param str 需要加密的字符串
     * @return String 加密后的字符串
     */
    public static String getEncode(String str) {
        String md5Str = str;
        try {
            MessageDigest messageDigest;
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
            byte[] byteArray = messageDigest.digest();
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                else
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
            md5Str = md5StrBuff.toString();
        } catch (Exception e) {
            LOGGER.error("Occur Exception do MD5 encode", e);
        }
        return md5Str;
    }
}
