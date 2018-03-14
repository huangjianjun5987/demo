package test.com.yatang.sc;

import com.yatang.sc.timedtask.AutoSendOrderIndexScheduler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestOrderIndexScheduler {

    @Autowired
    private AutoSendOrderIndexScheduler autoSendOrderIndexScheduler;


    @Test
    public void autoSendOrderIndexSchedulerTest(){
        try {
            autoSendOrderIndexScheduler.execute("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
