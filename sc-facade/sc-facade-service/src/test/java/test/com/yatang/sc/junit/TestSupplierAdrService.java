package test.com.yatang.sc.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yatang.sc.facade.domain.SpAdrBasicPo;
import com.yatang.sc.facade.domain.SpAdrContactPo;
import com.yatang.sc.facade.domain.SupplierAdrInfoPo;
import com.yatang.sc.facade.service.SupplierAdrService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext*.xml" })
public class TestSupplierAdrService {

	@Autowired
	private SupplierAdrService service;



	@Test
	public void testQueryById() {
		SupplierAdrInfoPo po = service.queryById(1);
		System.out.println(po.toString());
	}



	@Test
	public void testInsertSupplierAdr() {
		SupplierAdrInfoPo record = new SupplierAdrInfoPo();
		record.setParentId("xprov149");
		record.setBasicId(1);
		record.setContId(1);
		record.setStatus(0);
		record.setCreateUser("admin");
		record.setModifyUser("admin");
		service.insertSupplierAdrInfo(record);
	}



	@Test
	public void testInsertBasicInfo() {
		SpAdrBasicPo record = new SpAdrBasicPo();
		record.setProviderNo("10001");
		record.setProviderName("伊利纯牛奶有限股份有限公司-四川");
		record.setPayType(2);
		record.setSettlementPeriod(15);
		record.setBelongArea(28010);
		record.setGoodsArrivalCycle(3);
		record.setGrade(1);
		record.setOperaStatus(1);
		service.insertSpAdrBasicInfo(record);
	}



	@Test
	public void testInsertContractInfo() {
		SpAdrContactPo record = new SpAdrContactPo();
		record.setProviderName("王二狗");
		record.setProviderPhone("18520242025");
		record.setProviderEmail("wangergou@qq.com");
		record.setPurchaseName("张骁蛋");
		record.setPurchasePhone("17755485454");
		record.setPurchaseEmail("zhangxiaodan@163.com");
		service.insertSpAdrContactInfo(record);
	}



	@Test
	public void testSupplierSearchBox() {
		// PageInfo<SpSearchBoxPo> pageInfo =
		// service.supplierSearchBox("xprov497",null, "四川", 1, 5);
		// for (SpSearchBoxPo po : pageInfo.getList()) {
		// System.out.println("data:"+po.toString());
		// }
	}
}
