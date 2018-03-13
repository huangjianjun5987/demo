package test.com.yatang.sc.inventory;

import com.yatang.sc.inventory.dto.im.ImAdjustmentDto;
import com.yatang.sc.inventory.dto.im.ImAdjustmentItemDto;
import com.yatang.sc.inventory.dto.im.ImAdjustmentReceiptDto;
import com.yatang.sc.inventory.dubboservice.ImAdjustmentWriteDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 库存调整调整dubboService测试类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/31 下午3:41
 * @版本: v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/applicationContext-test.xml"})
public class TestImAdjustmentDubboService {

    @Autowired
    private ImAdjustmentWriteDubboService writeDubboService;


    @Test
    public void TestAcceptAdjustmentReceiptFromWareHouse() {

        ImAdjustmentReceiptDto receiptDto = new ImAdjustmentReceiptDto();//库存调整单

        ImAdjustmentDto imAdjustmentDto = new ImAdjustmentDto();//库存调整

        imAdjustmentDto.setDescription("库存调整主表");

        imAdjustmentDto.setWarehouseCode("YT901");//仓库编号


        ImAdjustmentItemDto imAdjustmentItemDto = new ImAdjustmentItemDto();
        imAdjustmentItemDto.setProductCode("1001662");
        imAdjustmentItemDto.setQuantity(10l);

        receiptDto.setImAdjustment(imAdjustmentDto);

        List<ImAdjustmentItemDto> list = new ArrayList<ImAdjustmentItemDto>();
        list.add(imAdjustmentItemDto);

        receiptDto.setImAdjustmentItems(list);

        writeDubboService.addAdjustmentReceipt(receiptDto);



    }
}
