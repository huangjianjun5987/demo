package test.com.yatang.sc;

import com.yatang.sc.timedtask.AutoReceiveOrderScheduler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestAutoReceiveOrderScheduler {

    @Autowired
    private AutoReceiveOrderScheduler autoReceiveOrderScheduler;

    @Test
    public void autoReceiveOrderSchedulerTest(){
        autoReceiveOrderScheduler.execute();
    }
}
