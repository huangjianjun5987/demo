package com.yatang.sc.common.utils;

import java.math.BigDecimal;

/**
 * Created by qiugang on 7/14/2017.
 */
public class PricingUtil {

    public static double roundPrice(double pNumber){
        BigDecimal bd = new BigDecimal(Double.toString(pNumber));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
    
    /**
     * 提供精确减法运算的sub方法
     * 
     * @param pNumber1
     *            被减数
     * @param pNumber2
     *            减数
     * @return 两个参数的差
     */
    public static double sub(double pNumber1,double pNumber2){
        BigDecimal number1 = new BigDecimal(Double.toString(pNumber1));
        BigDecimal number2 = new BigDecimal(Double.toString(pNumber2));
        BigDecimal result = number1.subtract(number2);
        result = result.setScale(2, BigDecimal.ROUND_HALF_UP);
        return result.doubleValue();
    }

    /**
     * 提供精确乘法运算的mul方法
     * 
     * @param pNumber1
     *            被乘数
     * @param pNumber2
     *            乘数
     * @return 两个参数的积
     */
    public static double mul(double pNumber1, double pNumber2) {
	BigDecimal b1 = new BigDecimal(Double.toString(pNumber1));
	BigDecimal b2 = new BigDecimal(Double.toString(pNumber2));
	return b1.multiply(b2).doubleValue();
    }
    
    /**
     * 提供精确的除法运算方法div
     * 
     * @param pNumber1
     *            被除数
     * @param pNumber2
     *            除数
     * @param scale
     *            精确范围
     * @return 两个参数的商
     */
    public static double div(double pNumber1, double pNumber2, int scale) {
		// 如果精确范围小于0，抛出异常信息
		if (scale < 0) {
		    scale = 3;
		}
		BigDecimal b1 = new BigDecimal(Double.toString(pNumber1));
		BigDecimal b2 = new BigDecimal(Double.toString(pNumber2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确加法计算的add方法
     * 
     * @param pNumber1
     *            被加数
     * @param pNumber2
     *            加数
     * @return 两个参数的和
     */
    public static double add(double pNumber1, double pNumber2) {
		BigDecimal b1 = new BigDecimal(Double.toString(pNumber1));
		BigDecimal b2 = new BigDecimal(Double.toString(pNumber2));
		return b1.add(b2).doubleValue();
    }
}
