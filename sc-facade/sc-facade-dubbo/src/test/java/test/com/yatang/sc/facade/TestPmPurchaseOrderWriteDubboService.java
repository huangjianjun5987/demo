package test.com.yatang.sc.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yatang.sc.facade.dto.pm.PmPurchaseOrderDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderExtDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderItemDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptSHDto;
import com.yatang.sc.facade.dto.pm.PurchaseConfirmOrderLinesDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseOrderWriteDubboService;


/**
 * @描述:
 * @类名:
 * @作者: 柳晓坤
 * @创建时间: 2017/12/1
 * @版本: v1.0
 */@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestPmPurchaseOrderWriteDubboService {
    @Autowired
    private PmPurchaseOrderWriteDubboService pmPurchaseOrderWriteDubboService;

//    @Test
    public void testUpdatePmPurchaseOrder(){
        PmPurchaseOrderExtDto pmPurchaseOrderExtDto = new PmPurchaseOrderExtDto();
        PmPurchaseOrderDto pmPurchaseOrder = new PmPurchaseOrderDto();
        pmPurchaseOrder.setAdrType(0);
        pmPurchaseOrder.setAdrTypeCode("YT901");
        pmPurchaseOrder.setBusinessMode(0);
        pmPurchaseOrder.setCurrencyCode("CNY");
        pmPurchaseOrder.setId(447L);
        pmPurchaseOrder.setPayCondition(1);
        pmPurchaseOrder.setPayType(0);
        pmPurchaseOrder.setPurchaseOrderType(0);
        pmPurchaseOrder.setSpAdrId("8");
        pmPurchaseOrder.setStatus(1);
        pmPurchaseOrder.setBranchCompanyId("10000");
        pmPurchaseOrderExtDto.setPmPurchaseOrder(pmPurchaseOrder);

        List<PmPurchaseOrderItemDto> pmPurchaseOrderItems = new ArrayList<>();
        PmPurchaseOrderItemDto pmPurchaseOrderItemDto = new PmPurchaseOrderItemDto();
        pmPurchaseOrderItemDto.setId(526L);
        pmPurchaseOrderItemDto.setProdPurchaseId("1773");
        pmPurchaseOrderItemDto.setProductCode("1000042");
        pmPurchaseOrderItemDto.setProductId("60000117016");
        pmPurchaseOrderItemDto.setPurchaseNumber(300);
        pmPurchaseOrderItemDto.setPurchasePrice(new BigDecimal(30));
        pmPurchaseOrderItems.add(pmPurchaseOrderItemDto);
        pmPurchaseOrderExtDto.setPmPurchaseOrderItems(pmPurchaseOrderItems);

        pmPurchaseOrderWriteDubboService.updatePmPurchaseOrder(pmPurchaseOrderExtDto);
    }

    @Test
    public void testAddPmPurchaseOrder(){
        PmPurchaseOrderExtDto pmPurchaseOrderExtDto = new PmPurchaseOrderExtDto();
        PmPurchaseOrderDto pmPurchaseOrder = new PmPurchaseOrderDto();
        pmPurchaseOrder.setAdrType(0);
        pmPurchaseOrder.setAdrTypeCode("YT901");
        pmPurchaseOrder.setBusinessMode(0);
        pmPurchaseOrder.setCurrencyCode("CNY");
        pmPurchaseOrder.setId(4471L);
        pmPurchaseOrder.setPayCondition(1);
        pmPurchaseOrder.setPayType(0);
        pmPurchaseOrder.setPurchaseOrderType(0);
        pmPurchaseOrder.setSpAdrId("8");
        pmPurchaseOrder.setStatus(1);
        pmPurchaseOrder.setBranchCompanyId("10000");
        pmPurchaseOrderExtDto.setPmPurchaseOrder(pmPurchaseOrder);

        List<PmPurchaseOrderItemDto> pmPurchaseOrderItems = new ArrayList<>();
        PmPurchaseOrderItemDto pmPurchaseOrderItemDto = new PmPurchaseOrderItemDto();
        pmPurchaseOrderItemDto.setId(526L);
        pmPurchaseOrderItemDto.setProdPurchaseId("1773");
        pmPurchaseOrderItemDto.setProductCode("1000042");
        pmPurchaseOrderItemDto.setProductId("60000117016");
        pmPurchaseOrderItemDto.setPurchaseNumber(300);
        pmPurchaseOrderItemDto.setPurchasePrice(new BigDecimal(30));
        pmPurchaseOrderItems.add(pmPurchaseOrderItemDto);
        pmPurchaseOrderExtDto.setPmPurchaseOrderItems(pmPurchaseOrderItems);

        pmPurchaseOrderWriteDubboService.addPmPurchaseOrder(pmPurchaseOrderExtDto);
    }

//    @Test
    public void testAuditCallbackPurchaseOrderInfo(){
        pmPurchaseOrderWriteDubboService.auditCallbackPurchaseOrderInfo(410L,"10018",null,true);
    }



    @Test
    public void testUpdateSpAcceptStatus(){

        pmPurchaseOrderWriteDubboService.updateSpAcceptStatus(572L,"100023");

    }



	/**
	 * 
	 * <method description>测试采购订单收货
	 *
	 */
	@Test
	public void testPmPurchasealiquOrderReceipt() {
		PmPurchaseReceiptSHDto pmPurchaseReceiptSHDto=new PmPurchaseReceiptSHDto();
		pmPurchaseReceiptSHDto.setPurchaseReceiptNo("1010101010");
		List<PurchaseConfirmOrderLinesDto>	orderLines=new ArrayList<>();
		pmPurchaseReceiptSHDto.setOrderLines(orderLines);
		PurchaseConfirmOrderLinesDto confirmOrderLinesDto=new PurchaseConfirmOrderLinesDto();
		orderLines.add(confirmOrderLinesDto);
		confirmOrderLinesDto.setItemCode("1001662");
		confirmOrderLinesDto.setActualQty(11);
		pmPurchaseOrderWriteDubboService.updatePurchaseReceiptSHStatus(pmPurchaseReceiptSHDto);
	}


    @Test
    public void testAddSalePmPurchaseOrder(){
        PmPurchaseOrderExtDto pmPurchaseOrderExtDto = new PmPurchaseOrderExtDto();
        PmPurchaseOrderDto pmPurchaseOrder = new PmPurchaseOrderDto();
        pmPurchaseOrder.setAdrType(1);
        pmPurchaseOrder.setAdrTypeCode("A900001");
        pmPurchaseOrder.setBusinessMode(0);
        pmPurchaseOrder.setCurrencyCode("CNY");
        pmPurchaseOrder.setId(4471L);
        pmPurchaseOrder.setPurchaseOrderType(0);
        pmPurchaseOrder.setSpAdrId("8");
        pmPurchaseOrder.setStatus(1);
        pmPurchaseOrder.setBranchCompanyId("10000");
        pmPurchaseOrder.setSaleOrderId("123");
        pmPurchaseOrderExtDto.setPmPurchaseOrder(pmPurchaseOrder);

        List<PmPurchaseOrderItemDto> pmPurchaseOrderItems = new ArrayList<>();
        PmPurchaseOrderItemDto pmPurchaseOrderItemDto = new PmPurchaseOrderItemDto();
        pmPurchaseOrderItemDto.setId(526L);
        pmPurchaseOrderItemDto.setProductCode("1000042");
        pmPurchaseOrderItemDto.setProductId("60000117016");
        pmPurchaseOrderItemDto.setPurchaseNumber(301);
        pmPurchaseOrderItemDto.setPurchasePrice(new BigDecimal(30));
        pmPurchaseOrderItems.add(pmPurchaseOrderItemDto);
        pmPurchaseOrderExtDto.setPmPurchaseOrderItems(pmPurchaseOrderItems);

        pmPurchaseOrderWriteDubboService.addPmPurchaseOrder(pmPurchaseOrderExtDto);
    }


}
