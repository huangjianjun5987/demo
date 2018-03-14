package test.com.yatang.sc.purchase;

import com.yatang.sc.purchase.dubboservice.CouponsDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.yatang.sc.purchase.dubboservice.QueryOrderDetailDubboService;

import java.util.Date;

/**
 * @描述:
 * @类名:
 * @作者:kangdong
 * @创建时间:2017/9/21 10:39
 * @版本:v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestQueryCouponsService {

    @Autowired
    private CouponsDubboService couponsDubboService;

    @Test
    public void queryOverdue(){
        System.out.println(couponsDubboService.queryOverdueCouponActivityItems("2").getResultObject());
        System.out.println(JSONObject.toJSONString(couponsDubboService.queryStoreCouponActivities("1","active").getResultObject()));
    }
}