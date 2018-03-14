package test.com.yatang.sc.purchase;

import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.order.dubboservice.WebReturnRequestDubboService;
import com.yatang.sc.purchase.dto.ReturnRequestDetailDto;
import com.yatang.sc.purchase.dto.ReturnRequestListDto;
import com.yatang.sc.purchase.dto.ReturnRequestQueryParamDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @描述: 退换货测试类
 * @类名: TestQueryReturnRequestDubboService
 * @作者: kangdong
 * @创建时间: 2017/10/19 16:05
 * @版本: v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestQueryReturnRequestDubboService {

}
