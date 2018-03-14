package test.com.yatang.xc.msg;

import com.yatang.sc.order.dao.InvoiceInfoDao;
import com.yatang.sc.order.domain.UserInvoiceInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by liusongjie on 2017/7/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext*.xml"})
public class TestInvoiceInfo {

    @Autowired
    InvoiceInfoDao invoiceInfoDao;

    @Test
    public void testGetInvoiceInfoByProfileId(){
        UserInvoiceInfo userInvoiceInfo = invoiceInfoDao.selectByProfileId("pro3");
        System.out.print(userInvoiceInfo);
    }
}
