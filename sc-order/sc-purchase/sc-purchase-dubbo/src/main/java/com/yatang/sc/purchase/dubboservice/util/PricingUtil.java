package com.yatang.sc.purchase.dubboservice.util;

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


}
