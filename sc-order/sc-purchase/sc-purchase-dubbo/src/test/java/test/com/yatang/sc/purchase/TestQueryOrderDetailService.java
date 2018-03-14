package test.com.yatang.sc.purchase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yatang.sc.purchase.dubboservice.QueryOrderDetailDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @描述:
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/7/11 10:39
 * @版本:v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestQueryOrderDetailService {

    @Autowired
    private QueryOrderDetailDubboService queryOrderDetailDubboService;

    @Test
    public void query(){
        System.out.println( queryOrderDetailDubboService.queryOrderDetaiById("12366" ));
        System.out.println(JSONObject.toJSONString(queryOrderDetailDubboService.queryOrderDetaiById("12366" )));
    }
}