package test.com.yatang.sc.facade;

import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDetailDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptItemsDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseReceiptWriteDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>@description:采购收货单测试类</p>
 *
 * @author yangshuang
 * @version v1.0
 * @date 2018/1/11 13:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestPmPurchaseReceiptWriteDubboService {

    @Autowired
    private PmPurchaseReceiptWriteDubboService writeDubboService;

    @Test
    public void testCreatePurchaseReceiptASN() {


        PmPurchaseReceiptDetailDto detailDto = new PmPurchaseReceiptDetailDto();
        /**
         * 收货单基础信息
         */
        PmPurchaseReceiptDto pmPurchaseReceipt = new PmPurchaseReceiptDto();

        pmPurchaseReceipt.setId(222L);
         /**
         * 收货单明细
         */
        List<PmPurchaseReceiptItemsDto> receiptPruducts = new ArrayList<>();

        PmPurchaseReceiptItemsDto receiptItemsDto = new PmPurchaseReceiptItemsDto();

        receiptItemsDto.setPurchaseOrderItemsId(String.valueOf(671));
        receiptItemsDto.setId(314L);
        receiptItemsDto.setDeliveryNumber(10);
        receiptPruducts.add(receiptItemsDto);

        detailDto.setPmPurchaseReceipt(pmPurchaseReceipt);
//        detailDto.getPmPurchaseReceipt().setSaleOrderId("10000201801220001001");
        detailDto.getPmPurchaseReceipt().setEstimatedDeliveryDate(new Date());
        detailDto.setReceiptPruducts(receiptPruducts);
        writeDubboService.createPurchaseReceiptASN(detailDto);

    }
    //210
}
