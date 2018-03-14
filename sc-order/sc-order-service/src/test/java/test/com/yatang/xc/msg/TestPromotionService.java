package test.com.yatang.xc.msg;

import com.sun.media.sound.SoftTuning;
import com.yatang.sc.order.domain.CompanyCancelNoPayPo;
import com.yatang.sc.order.domain.PromoStoresPo;
import com.yatang.sc.order.domain.PromotionPo;
import com.yatang.sc.order.service.OrderPaymentsService;
import com.yatang.sc.order.service.PromotionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext*.xml","classpath*:test/dubbo/applicationContext-consumer.xml"})
public class TestPromotionService {

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private OrderPaymentsService orderPaymentsService;

    @Test
    public void testQueryPromotionDetail(){
        promotionService.queryById("HD888");
    }

    @Test
    public void testQueryAvailablePromotions(){
        promotionService.queryAvailablePromotions("10000","order_promo");
    }

    @Test
    public void testGetCompanyCancelNoPay(){
        orderPaymentsService.getCompanyCancelNoPay();
    }

    @Test
    public void testUpdateStoreId(){
        PromotionPo promotionPo = new PromotionPo();
        PromoStoresPo promoStoresPo = new PromoStoresPo();
        promoStoresPo.setStoreId("A000993");
        promoStoresPo.setPromoId("HD225");
        promotionPo.setStores(promoStoresPo);
        promotionService.updateStoreId(promotionPo);
    }

    @Test
    public void testGetNoPayByCompanyId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long leftTime = orderPaymentsService.getNoPayByCompanyId("10000");
        System.out.println("设置的最大未付款时间（分钟）：" + leftTime);

    }

    @Test
    public void testBatchUpdatePromoStatus(){
        String[] ids = {"HD355","HD437","HD447"};
        String status = "closed";
        Boolean flag = promotionService.batchUpdatePromoStatus(ids,status);
        System.out.println("批量修改促销或者优惠券的的状态：" + flag);
    }
}
