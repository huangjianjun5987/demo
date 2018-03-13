package test.com.yatang.sc.facade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderItemQueryParamDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseQueryParamDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseRefundAuditQueryDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseRefundAuditResultDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseRefundDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseRefundItemDto;
import com.yatang.sc.facade.dto.pm.ProcessAuditLogParamDto;
import com.yatang.sc.facade.dto.pm.ProcessDefinitionDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseOrderQueryDubboService;
import com.yatang.sc.facade.dubboservice.PmPurchaseRefundQueryDubboService;
import com.yatang.sc.facade.dubboservice.ProcessDefinitionQueryDubboService;
import com.yatang.sc.facade.dubboservice.SupplierQueryDubboService;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dubboservice.PmPurchaseRefundWriteDubboService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestPmPurchaseRefundDubboService {
	@Autowired
	private PmPurchaseRefundWriteDubboService pmPurchaseRefundWriteDubboService;
	@Autowired
	private PmPurchaseOrderQueryDubboService pmPurchaseOrderQueryDubboService;
	@Autowired
	private PmPurchaseRefundQueryDubboService pmPurchaseRefundQueryDubboService;
	@Autowired
	private ProcessDefinitionQueryDubboService processDefinitionQueryDubboService;
	@Autowired
	private SupplierQueryDubboService supplierQueryDubboService;
	@Autowired
	private WarehouseLogicQueryDubboService warehouseLogicQueryDubboService;
	@Autowired
	private OrganizationService organizationService;


	
	@Test
	public void testDeleteBatchRefundOrder() {
		Long [] purchaseRefundIds = {1L,2L,3L,4L};
		Response<Boolean> result= pmPurchaseRefundWriteDubboService.deleteBatchRefundOrder(purchaseRefundIds);
		System.out.println(result.getResultObject());
	}


	@Test
	public void testGetRefundNo() {
		Response<List<BranchCompanyDto>> listResponse = organizationService
				.querySimpleByBranchCompanyIdAndName("", "");
		System.out.println("--------"+ JSON.toJSONString(listResponse));
	}

	@Test
	public void testQueryPurchaseOrderProducts(){
		PmPurchaseOrderItemQueryParamDto pmPurchaseOrderItemQueryParamDto=new PmPurchaseOrderItemQueryParamDto();
		pmPurchaseOrderItemQueryParamDto.setPurchaseOrderNo("17111700009");
		pmPurchaseOrderItemQueryParamDto.setLogicWareHouseCode("WLL");
		pmPurchaseOrderItemQueryParamDto.setSpAdrNo("100173");
		pmPurchaseOrderQueryDubboService.selectBrandsByOrderNo(pmPurchaseOrderItemQueryParamDto);

	}

	@Test
	public void testAddRefundProducts(){
		PmPurchaseOrderItemQueryParamDto pmPurchaseOrderItemQueryParamDto=new PmPurchaseOrderItemQueryParamDto();
		pmPurchaseOrderItemQueryParamDto.setPurchaseOrderNo("17111700009");
		pmPurchaseOrderItemQueryParamDto.setLogicWareHouseCode("YT901");
		pmPurchaseOrderQueryDubboService.addRefundProducts(pmPurchaseOrderItemQueryParamDto);
	}

	@Test
	public void testCreateRefundWithItems(){
		PmPurchaseRefundDto pmPurchaseRefundDto=new PmPurchaseRefundDto();
		List<PmPurchaseRefundItemDto> pmPurchaseRefundItemDtos=new ArrayList<>();
		pmPurchaseRefundDto.setPurchaseRefundNo("12345678");
		pmPurchaseRefundDto.setAdrType(0);
		pmPurchaseRefundDto.setId(41L);
		pmPurchaseRefundDto.setBranchCompanyId("100028");
		pmPurchaseRefundDto.setFailedReason("测试");
		pmPurchaseRefundDto.setRefundAdrCode("YT901");
		pmPurchaseRefundDto.setRefundAdrName("深圳仓深圳");
		pmPurchaseRefundDto.setRefundTimeEarly(new Date());
		pmPurchaseRefundDto.setRemark("yyx测试一下");
		pmPurchaseRefundDto.setSpAdrId("10000");
		pmPurchaseRefundDto.setSpAdrName("四川 - 宜宾丰源盐业有限公司");
		pmPurchaseRefundDto.setSpAdrNo("100173");
		pmPurchaseRefundDto.setStatus(1);

		PmPurchaseRefundItemDto pmPurchaseRefundItemDto=new PmPurchaseRefundItemDto();
		pmPurchaseRefundItemDto.setPurchaseOrderNo("17101000013");
		pmPurchaseRefundItemDto.setAvCost(3.81);
		pmPurchaseRefundItemDto.setInputTaxRate(new BigDecimal(17));
		pmPurchaseRefundItemDto.setInternationalCode("6922222702293");
		pmPurchaseRefundItemDto.setPackingSpecifications("110g");
		pmPurchaseRefundItemDto.setProductCode("1000404");
		pmPurchaseRefundItemDto.setProductId("xprod347938");
		pmPurchaseRefundItemDto.setProductName("桃李吉士排面包(110g)");
		pmPurchaseRefundItemDto.setPurchasePrice(new BigDecimal(2.4));
		pmPurchaseRefundItemDto.setPossibleNum(9);
		pmPurchaseRefundItemDto.setRefundAmount(8);


		pmPurchaseRefundItemDtos.add(pmPurchaseRefundItemDto);
		pmPurchaseRefundDto.setPmPurchaseRefundItems(pmPurchaseRefundItemDtos);

		pmPurchaseRefundWriteDubboService.saveRefundWithItems(pmPurchaseRefundDto);

	}


	@Test
	public void testQueryRefundDetailById(){
		pmPurchaseRefundQueryDubboService.queryRefundDetailById(3L);
	}

	/*
	@Test
	public void testQueryPmPurchaseRefundAudit(){
		PmPurchaseRefundAuditQueryDto pmPurchaseRefundAuditQueryDto=new PmPurchaseRefundAuditQueryDto();
		pmPurchaseRefundAuditQueryDto.setBranchCompanyId("10000");
		List<String> list=new ArrayList();
		list.add("381");
		pmPurchaseRefundAuditQueryDto.setRoles(list);
		pmPurchaseRefundAuditQueryDto.setPageNum(1);
		pmPurchaseRefundAuditQueryDto.setPageSize(Integer.MAX_VALUE);
		Response<PageResult<PmPurchaseRefundAuditResultDto>> pageResultResponse = pmPurchaseRefundQueryDubboService
				.queryPmPurchaseRefundAudit(pmPurchaseRefundAuditQueryDto);
		System.out.println("-----------haha"+JSONObject.toJSONString(pageResultResponse));
	}
	*/

	@Test
	public  void testQueryProcessDefinitions(){
		ProcessDefinitionDto processDefinitionDto=new ProcessDefinitionDto();
		processDefinitionDto.setType(1L);
		processDefinitionDto.setBranchCompanyId("10000");
		processDefinitionDto.setBusinessId(15L);
		System.out.println("----------haha"+JSONObject.toJSONString(processDefinitionQueryDubboService.queryProcessDefinitions(processDefinitionDto)));
	}

	/*
	@Test
	public void testApproveRefund(){
		ProcessAuditLogParamDto processAuditLogParamDto=new ProcessAuditLogParamDto();
		processAuditLogParamDto.setAuditOpinion("尹玉新测试际链退货单审批第二个审批人");
		processAuditLogParamDto.setBusinessId(59L);
		processAuditLogParamDto.setAuditResult(1L);
		processAuditLogParamDto.setBranchCompanyId("10000");
		processAuditLogParamDto.setType(1L);
		processAuditLogParamDto.setProcessId("6");
		processAuditLogParamDto.setAuditUser("yinyuxin");
		processAuditLogParamDto.setAuditUserId("12345");
		pmPurchaseRefundWriteDubboService.approveRefund(processAuditLogParamDto);
	}
	*/

	@Test
	public void testQueryProviderPlaceInfo(){
		//supplierQueryDubboService.queryProviderPlaceInfo(202);
		warehouseLogicQueryDubboService.selectLogicWarehouseByPrimaryKey("WLL");
	}

	@Test
	public void queryPmPurchaseRefund(){
		Response<PmPurchaseRefundDto> pmPurchaseRefundDtoResponse = pmPurchaseRefundQueryDubboService
				.queryRefundDetailById(38L);
		System.out.println("------"+JSON.toJSONString(pmPurchaseRefundDtoResponse));
	}

	@Test
	public void queryPurchaseOrderList(){
		PmPurchaseQueryParamDto pmPurchaseQueryParamDto=new PmPurchaseQueryParamDto();
		pmPurchaseQueryParamDto.setBranchCompanyId("10000");
		Response<PageResult<PmPurchaseOrderDto>> pageResultResponse = pmPurchaseOrderQueryDubboService
				.queryPurchaseOrderList(pmPurchaseQueryParamDto);
		System.out.println("-------"+JSON.toJSONString(pageResultResponse));
	}

	@Test
	public void testWorkFlowCallBack(){
		Long keyId=115L;
		String auditorId="10021";
		Boolean auditorResult=true;
		pmPurchaseRefundWriteDubboService.workFlowCallBack(keyId,auditorId,"周建英",auditorResult);
	}
}
