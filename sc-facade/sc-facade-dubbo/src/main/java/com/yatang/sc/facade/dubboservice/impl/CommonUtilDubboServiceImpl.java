package com.yatang.sc.facade.dubboservice.impl;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.Constants;
import com.yatang.sc.facade.dubboservice.CommonUtilDubboService;
import com.yatang.sc.facade.dubboservice.util.BarcodeUtil;
import com.yatang.xc.oc.biz.adapter.RedisAdapterServie;
import com.yatang.xc.oc.biz.redis.dubboservice.RedisPlatform;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service("commonUtilDubboService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonUtilDubboServiceImpl implements CommonUtilDubboService {

    String lockPath = "/scOrderId";


    private final static String zoomKeeperAddr="sit.zk.com:2181";

    protected final Log log	= LogFactory.getLog(this.getClass());

    private final RedisAdapterServie<String, String> redisDubboAdapterServie;//用于存放序列号
    private Lock lock = new ReentrantLock();// 可重入锁

    /*ReentrantZkDistributeLock distLock = ZkDistributeLockFactory.getInstance().getZkDistributeLock(lockPath,
            this.zoomKeeperAddr);*/

    @Override
    public Response<byte[]> generateBarCode(String msg) {
        Response<byte[]> response = new Response<>();
        try {
            ByteArrayOutputStream ous = new ByteArrayOutputStream();
            BarcodeUtil.generate(msg, ous);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            response.setResultObject(ous.toByteArray());
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    public Response<String> generateSerialNo(String redisKey,String preFix, String dateFormat, int length) {
        Response<String> response = new Response<>();

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);//格式化
        String dateFormatStr = sdf.format(new Date());

       // distLock.lock();// 取得锁
        String newSerialNo = null;//新的系列号
        String serialNoPreFix=null;
        if (null!=preFix&& StringUtils.isNotEmpty(preFix)) {
            serialNoPreFix =preFix+dateFormatStr;//前缀 No.YYYYMMDD
        }else {
            serialNoPreFix=dateFormatStr;
        }
        try {
            //1.从redis获取数据
            String oldSerialNo = redisDubboAdapterServie.get(RedisPlatform.common,redisKey, String.class);
            if (StringUtils.isNotEmpty(oldSerialNo)) {//不为空
                //获取当前的数据
                //截取日期
                String dateString = oldSerialNo.substring(0, oldSerialNo.length() - length);//日期的格式化数据
                if (serialNoPreFix.equals(dateString)) {//同一天
                    String numberStr = oldSerialNo.substring(oldSerialNo.length() - length);
                    int oldNum = Integer.parseInt(numberStr);//当天已经存在的单数
                    oldNum++;//单数++
                    newSerialNo = createNewSerialNo(preFix, dateFormatStr, oldNum, length);
                    redisDubboAdapterServie.set(RedisPlatform.common,Constants.OLD_SERIAL_NO_KEY, newSerialNo);

                } else {//不是同一天重新计数
                    newSerialNo = createNewSerialNo(preFix, dateFormatStr, 1, length);
                    redisDubboAdapterServie.set(RedisPlatform.common,Constants.OLD_SERIAL_NO_KEY, newSerialNo);
                }
            } else {//数据为空直接初始化
                newSerialNo = createNewSerialNo(preFix, dateFormatStr, 1, length);
                redisDubboAdapterServie.set(RedisPlatform.common,Constants.OLD_SERIAL_NO_KEY, newSerialNo);
            }
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        } finally {
         //   distLock.unlock();

        }
        if (StringUtils.isNotEmpty(newSerialNo)) {//不为空
            response.setResultObject(newSerialNo);
            response.setSuccess(true);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        } else {
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_10030.getName());
            response.setCode(CommonsEnum.RESPONSE_10030.getCode());
        }
        return response;
    }

    /**
     * 根据当日的单数生成后置的单号
     *
     * @param preFix 前缀
     * @param number 当前的账单号
     * @param length 规定的后坠长度
     * @return
     */
    public String createNewSerialNo(String preFix, String dateFormat, int number, int length) {
        String s = String.valueOf(number);
        StringBuilder builder = new StringBuilder();
        if (null!=preFix&& StringUtils.isNotEmpty(preFix)) {
            builder.append(preFix);
        }
        builder.append(dateFormat);
        int initLength = s.length();//当前单数长度
        while (initLength < length) {
            builder.append("0");
            initLength++;
        }
        builder.append(number);
        return builder.toString();
    }

}
