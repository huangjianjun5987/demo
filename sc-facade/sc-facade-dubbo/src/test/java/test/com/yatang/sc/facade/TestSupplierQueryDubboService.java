package test.com.yatang.sc.facade;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.ProvinceDto;
import com.yatang.sc.facade.dto.RegionBasicDto;
import com.yatang.sc.facade.dto.SupplierPlaceDto;
import com.yatang.sc.facade.dto.supplier.SpAdrBasicDto;
import com.yatang.sc.facade.dto.supplier.SpSearchBoxDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.supplier.SupplierInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoQueryListDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoQueryParamDto;
import com.yatang.sc.facade.dubboservice.SupplierQueryDubboService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @描述: 供应商只读服务测试类
 * @作者: huangjianjun
 * @创建时间: 2017年3月31日-下午8:36:20 .
 * @版本: 1.0 .
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestSupplierQueryDubboService {
	@Autowired
	private SupplierQueryDubboService service;

	@Test
	public void testQueryById(){
		Response<SupplierInfoDto> response = service.queryById("xprov368");
		SupplierInfoDto dto = response.getResultObject();
		System.out.println(dto.toString());
	}

	/**
	 * 地点所属区域
	 */
	@Test
	public void testSupplierPlaceRegion(){
		Response<List<ProvinceDto>> response = new Response<>();
		List<ProvinceDto> ss = service.querySupplierPlaceRegion("xprov331").getResultObject();
		System.out.println(ss.toString());
	}

	@Test
	public void querySettledList(){
		SupplierInfoQueryParamDto param = new SupplierInfoQueryParamDto();
		param.setPageNum(1);
		param.setPageSize(10);
		param.setProviderName("");

		Response<PageResult<SupplierInfoQueryListDto>> response = service.querySettledList(param);
		System.out.println(response.getResultObject());
		for (SupplierInfoQueryListDto dto : response.getResultObject().getData()) {
			System.out.println(dto);
		}
	}

	@Test
	public void queryManageList(){
		SupplierInfoQueryParamDto param = new SupplierInfoQueryParamDto();
		param.setPageNum(1);
		param.setPageSize(10);

		Response<PageResult<SupplierInfoQueryListDto>> response = service.queryManageList(param);
		System.out.println(response.getResultObject());
		for (SupplierInfoQueryListDto dto : response.getResultObject().getData()) {
			System.out.println(dto);
		}
	}

	/**
	 * 供应商地点
	 */
	@Test
	public void queryProviderPlaceInfo() {
		int spid = 1;
		Response<SupplierPlaceDto> ba = service.queryProviderPlaceInfo(spid);
		System.out.println(ba.getResultObject());
	}

	/**
	 * 修改前后信息
	 */
	@Test
	public void testQueryEditBeforeAfter() {
		String spid = "xprov030";
		Response<ArrayList> ba = service.queryEditBeforeAfter(spid);
		System.out.println("==========+++========="+ba.getResultObject());
	}

	@Test
	public void queryByAdrInfoId() {
		Response<SpAdrBasicDto> spAdrBasicDto= service.queryByAdrInfoId(1);
		System.out.println("==========="+spAdrBasicDto.getResultObject());
	}

	/**
	 * 生成供应商/供应商地点编号
	 */
	@Test
	public void testGetSupplierNo() {
		Response<String> res = service.getSupplierNo("SP");
		System.out.println(res.getResultObject());
	}


	@Test
	/**
	 * 测试查询供应商地点
	 * */
	public void testSupplierAdrSearchBox(){


		List<String> branchCompanyIds=new ArrayList<>();
		branchCompanyIds.add("10000");
		branchCompanyIds.add("100028");
		Response<PageResult<SpSearchBoxDto>> pageResultResponse = service.supplierAdrSearchBox(null, branchCompanyIds, null, 1, 6);
		System.out.println(JSON.toJSONString(pageResultResponse));

	}

	@Test
	/**
	 * 测试供应商列表导出
	 * */
	public void testQueryExportManageList(){

		SupplierInfoQueryParamDto supplierInfoQueryParamDto = new SupplierInfoQueryParamDto();
		supplierInfoQueryParamDto.setPageNum(1);
		supplierInfoQueryParamDto.setPageSize(Integer.MAX_VALUE);

		supplierInfoQueryParamDto.setProviderType(1);
		supplierInfoQueryParamDto.setStatus(1);
		supplierInfoQueryParamDto.setGrade(2);
		Response<PageResult<SupplierInfoQueryListDto>> result = service.queryExportManageList(supplierInfoQueryParamDto);
		System.out.println(JSON.toJSONString(result));
	}


}
