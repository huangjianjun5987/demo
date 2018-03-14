package test.com.yatang.sc.settlement;

import com.yatang.sc.settlement.dubboservice.SimpleSettlementQueryDubboService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @描述: 库存
 * @作者: 向永红
 * @创建时间: 2017年3月31日-下午8:36:20 .
 * @版本: 1.0 .
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/applicationContext-test.xml"})
public class TestFranchiseeDubboService {

    @Autowired
    SimpleSettlementQueryDubboService simpleSettlementQueryDubboService;


}
