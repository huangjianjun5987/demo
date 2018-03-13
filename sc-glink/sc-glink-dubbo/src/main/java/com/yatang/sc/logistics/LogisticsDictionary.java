package com.yatang.sc.logistics;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiugang on 10/31/2017.
 */
public class LogisticsDictionary {

    public static final String SF = "SF";
    public static final String EMS = "EMS";
    public static final String EYB = "EYB";
    public static final String ZJS = "ZJS";
    public static final String YTO = "YTO";
    public static final String ZTO = "ZTO";
    public static final String HTKY = "HTKY";
    public static final String UC = "UC";
    public static final String STO = "STO";
    public static final String TTKDEX = "TTKDEX";
    public static final String QFKD = "QFKD";
    public static final String FAST = "FAST";
    public static final String POSTB = "POSTB";
    public static final String GTO = "GTO";
    public static final String JD = "JD";
    public static final String DD = "DD";
    public static final String AMAZON = "AMAZON";
    public static final String YUNDA = "YUNDA";
    public static final String OTHER = "OTHER";

    public static Map<String,String> logisticsCodeToNames = new HashMap<String,String>();
    static{
        logisticsCodeToNames.put(SF,"顺丰");
        logisticsCodeToNames.put(EMS,"EMS");
        logisticsCodeToNames.put(EYB,"经济快件");
        logisticsCodeToNames.put(ZJS,"宅急送");
        logisticsCodeToNames.put(YTO,"圆通");
        logisticsCodeToNames.put(ZTO,"中通");
        logisticsCodeToNames.put(HTKY,"百世汇通");
        logisticsCodeToNames.put(UC,"优速");
        logisticsCodeToNames.put(STO,"申通");
        logisticsCodeToNames.put(TTKDEX,"天天快递");
        logisticsCodeToNames.put(QFKD,"全峰");
        logisticsCodeToNames.put(FAST,"快捷");
        logisticsCodeToNames.put(POSTB,"邮政小包");
        logisticsCodeToNames.put(GTO,"国通");
        logisticsCodeToNames.put(JD,"京东配送");
        logisticsCodeToNames.put(DD,"当当宅配");
        logisticsCodeToNames.put(AMAZON,"亚马逊物流");
        logisticsCodeToNames.put(YUNDA,"韵达");
        logisticsCodeToNames.put(OTHER,"其他");
    }


    public static String  getlogisticsName(String code){
       return  logisticsCodeToNames.get(code);
    }
}
