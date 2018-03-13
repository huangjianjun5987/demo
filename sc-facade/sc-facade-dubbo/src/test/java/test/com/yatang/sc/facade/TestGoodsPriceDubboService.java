package test.com.yatang.sc.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceQueryParamDto;
import com.yatang.sc.facade.dto.prod.ProdSellSectionPriceDto;
import com.yatang.sc.facade.dubboservice.GoodsPriceQueryDubboService;
import com.yatang.sc.facade.dubboservice.GoodsPriceWriteDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.busi.common.resp.Response;

/**
 * @描述: 价格管理测试服务测试类
 * @作者: huangjianjun
 * @创建时间: 2017年3月31日-下午8:36:20 .
 * @版本: 1.0 .
 * @param <>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/applicationContext-test.xml" })
public class TestGoodsPriceDubboService {
	@Autowired
	private GoodsPriceQueryDubboService service;
	@Autowired
	private GoodsPriceWriteDubboService goodsPriceWriteDubboService;

	@Test
	@SuppressWarnings("unchecked")
	/**
	 * 查询方法
	 */
	public void testQuerySettledListPage(){
		ProdSellPriceQueryParamDto goodsSellPriceQueryParamDto = new ProdSellPriceQueryParamDto();
		goodsSellPriceQueryParamDto.setProductId("1005");
		//goodsSellPriceQueryParamDto.setBranchCompanyId("002");
		Response<ProdSellPriceInfoDto> goodsSellPrice = service.getGoodsSellPrice("1005", "002");
		ProdSellPriceInfoDto resultObject = goodsSellPrice.getResultObject();
		System.out.println(resultObject);

		}
		@Test
		/**
		 * 测试删除销售区间价格
		 */
		public void deleteSellPriceById(){
			Long in = Long.valueOf(1);
			goodsPriceWriteDubboService.deleteSellPriceById(in,"23","2");

		}

	@Test
	@SuppressWarnings("unchecked")
	/**
	 *根据多个商品id批量查询方法商品价格信息
	 */
	public void testQuerySellPriceInfo(){
		List<String> list = new ArrayList<>();
		list.add("1005");
		Response<Map<String, List<ProdSellPriceInfoDto>>> sellPriceInfo = service.findSellPriceInfo(list);
		Map<String, List<ProdSellPriceInfoDto>> resultObject = sellPriceInfo.getResultObject();


	}
	@Test
	/**
	 * 增加价格测试方法
	 */
	public void testAllSellPrice(){
		ProdSellSectionPriceDto prodSellSectionPriceDto1 = new ProdSellSectionPriceDto();
		prodSellSectionPriceDto1.setPrice(new BigDecimal(10));
		prodSellSectionPriceDto1.setStartNumber(10);
		prodSellSectionPriceDto1.setEndNumber(20);
		ProdSellSectionPriceDto prodSellSectionPriceDto2 = new ProdSellSectionPriceDto();
		prodSellSectionPriceDto2.setPrice(new BigDecimal(9));
		prodSellSectionPriceDto2.setStartNumber(21);
		prodSellSectionPriceDto2.setEndNumber(30);
		ProdSellSectionPriceDto prodSellSectionPriceDto3 = new ProdSellSectionPriceDto();
		prodSellSectionPriceDto3.setPrice(new BigDecimal(8));
		prodSellSectionPriceDto3.setStartNumber(31);
		prodSellSectionPriceDto3.setEndNumber(40);
		List<ProdSellSectionPriceDto> list = new ArrayList<>();
		list.add(prodSellSectionPriceDto1);
		list.add(prodSellSectionPriceDto2);
		list.add(prodSellSectionPriceDto3);
		ProdSellPriceInfoDto s = new ProdSellPriceInfoDto();
		s.setCreateUserId("2");
		s.setBranchCompanyId("001");
		s.setBranchCompanyName("成都子公司");
		s.setDeliveryDay(5);
		s.setMinNumber(3);
		s.setProductId("1231");
		s.setSuggestPrice(new BigDecimal(26));
		s.setMinNumber(55);
		s.setSalesInsideNumber(66);
		s.setSellSectionPrices(list);
		goodsPriceWriteDubboService.insertSellPrice(s);
	}

	@Test
	/**
	 * 修改价格测试方法
	 */
	public void testUpdateSellPrice(){
		ProdSellSectionPriceDto prodSellSectionPriceDto1 = new ProdSellSectionPriceDto();
		prodSellSectionPriceDto1.setPrice(new BigDecimal(10));
		prodSellSectionPriceDto1.setStartNumber(10);
		prodSellSectionPriceDto1.setEndNumber(20);
		ProdSellSectionPriceDto prodSellSectionPriceDto2 = new ProdSellSectionPriceDto();
		prodSellSectionPriceDto2.setPrice(new BigDecimal(9));
		prodSellSectionPriceDto2.setStartNumber(21);
		prodSellSectionPriceDto2.setEndNumber(30);
		ProdSellSectionPriceDto prodSellSectionPriceDto3 = new ProdSellSectionPriceDto();
		prodSellSectionPriceDto3.setPrice(new BigDecimal(8));
		prodSellSectionPriceDto3.setStartNumber(31);
		prodSellSectionPriceDto3.setEndNumber(40);
		ProdSellSectionPriceDto prodSellSectionPriceDto4 = new ProdSellSectionPriceDto();
		prodSellSectionPriceDto4.setPrice(new BigDecimal(7));
		prodSellSectionPriceDto4.setStartNumber(41);
		prodSellSectionPriceDto4.setEndNumber(50);
		List<ProdSellSectionPriceDto> list = new ArrayList<>();
		list.add(prodSellSectionPriceDto1);
		list.add(prodSellSectionPriceDto2);
		list.add(prodSellSectionPriceDto3);
		list.add(prodSellSectionPriceDto4);
		ProdSellPriceInfoDto s = new ProdSellPriceInfoDto();
		s.setId(Long.valueOf(9));
		s.setModifyUserId("4");
		s.setBranchCompanyId("002");
		s.setBranchCompanyName("重庆子公司");
		s.setDeliveryDay(5);
		s.setMinNumber(3);
		s.setProductId("1231");
		s.setSuggestPrice(new BigDecimal(26));
		s.setMinNumber(55);
		s.setSalesInsideNumber(66);
		s.setSellSectionPrices(list);
		goodsPriceWriteDubboService.updateSellPrice(s);
	}
	/**
	 * 测试查看采购价格list
	 */
	@Test
	public void testGetPurchasePriceDetailo() {
      /*  productId=100&code=06001&regionType=2*/
		/*PurchasePriceQueryParamDto purchasePriceQueryParamPo = new PurchasePriceQueryParamDto();
		purchasePriceQueryParamPo.setCode("06001");
		purchasePriceQueryParamPo.setRegionType("3");
		purchasePriceQueryParamPo.setProductId("100");*/
//		SupplierAuditInfoPo supplierAuditInfoPo = new SupplierAuditInfoPo();
//
//		supplierAuditInfoPo.setId("SP00001");
//		supplierAuditInfoPo.setGuaranteeMoney(new BigDecimal(500));
//		supplierAuditInfoPo.setSettlementAccountType(1);
//		supplierAuditInfoPo.setRebateRate(new BigDecimal(5.2));
//		supplierAuditInfoPo.setStatus(100);
//		supplierAuditInfoPo.setStoreContractDate(500);
//		supplierAuditInfoPo.setSettlementPeriod(12);
//		Boolean aBoolean = service.ge(supplierAuditInfoPo);
//		System.out.println(aBoolean);

	}

	/**
	 * 测试价格区间校验（不跳跃不重复）
	 */
	@Test
	public void fortest(){
		/*List<GoodsSellPriceInfoDto> list = new ArrayList<>();
		GoodsSellPriceInfoDto goodsSellPriceInfoDto1= new GoodsSellPriceInfoDto();
		goodsSellPriceInfoDto1.setStartNumber1(10);
		goodsSellPriceInfoDto1.setEndNumber1(20);

		GoodsSellPriceInfoDto goodsSellPriceInfoDto2= new GoodsSellPriceInfoDto();
		goodsSellPriceInfoDto2.setStartNumber1(21);
		goodsSellPriceInfoDto2.setEndNumber1(41);

		GoodsSellPriceInfoDto goodsSellPriceInfoDto3= new GoodsSellPriceInfoDto();
		goodsSellPriceInfoDto3.setStartNumber1(42);
		goodsSellPriceInfoDto3.setEndNumber1(47);

		GoodsSellPriceInfoDto goodsSellPriceInfoDto4= new GoodsSellPriceInfoDto();
		goodsSellPriceInfoDto4.setStartNumber1(48);
		goodsSellPriceInfoDto4.setEndNumber1(56);
		list.add(goodsSellPriceInfoDto1);
		list.add(goodsSellPriceInfoDto2);
		list.add(goodsSellPriceInfoDto3);
		list.add(goodsSellPriceInfoDto4);

		for (int i=0;i<list.size();i++){
			if (i==list.size()-1){
				break;
			}
			if (list.get(i).getStartNumber1()>list.get(i).getEndNumber1() || list.get(i).getEndNumber1()>list.get(i+1).getStartNumber1() || (list.get(i).getEndNumber1()+1!=list.get(i+1).getStartNumber1())){
				System.out.println("数据是不合理的");
			}
			System.out.println("数据是合理的");
		}*/
	}

}


