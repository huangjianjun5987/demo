package com.yatang.sc.operation.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptExportDto;
import com.yatang.sc.web.common.excel.service.IExcelExportService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDetailDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptParamDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseReceiptQueryDubboService;
import com.yatang.sc.facade.dubboservice.PmPurchaseReceiptWriteDubboService;
import com.yatang.sc.operation.vo.pm.PmPurchaseReceiptDetailsVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseReceiptParamVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseReceiptVo;
import com.yatang.xc.mbd.web.commons.ResourceUtil;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 收货单Action
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年7月26日
 */
@Slf4j
@RestController
@RequestMapping("/sc/pmPurchaseReceipt")
public class PmPurchaseReceiptAction extends BaseAction{

	@Autowired
	private PmPurchaseReceiptQueryDubboService	pmPurchaseReceiptQueryDubboService;

	@Autowired
	private PmPurchaseReceiptWriteDubboService	pmPurchaseReceiptWriteDubboService;

	@Autowired
	private IExcelExportService excelExportService;


	public String getCurrentCompanyId(HttpServletRequest req) {
		LoginInfoVO userInfo = (LoginInfoVO) req.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		String companyId = null;
		if (userInfo.getCompanyType() == 0) {
			companyId = userInfo.getCompanyId();
		}
		return companyId;
	}



	/**
	 * 
	 * 查询收货单列表
	 *
	 * @param paramVo
	 * @return
	 */
	@RequestMapping(value = "queryReceiptList", method = RequestMethod.GET)
	public Response<PageResult<PmPurchaseReceiptVo>> queryReceiptList(PmPurchaseReceiptParamVo paramVo,
			HttpServletRequest req) {
		Response<PageResult<PmPurchaseReceiptVo>> voResponse = new Response<>();
		PageResult<PmPurchaseReceiptVo> voPage = new PageResult<>();
		List<PmPurchaseReceiptVo> list = new ArrayList<>();
		PmPurchaseReceiptParamDto paramDto = BeanConvertUtils.convert(paramVo, PmPurchaseReceiptParamDto.class);

		String companyId = getCurrentCompanyId(req);
		if (!StringUtils.isEmpty(companyId)) {
			paramDto.setBranchCompanyId(companyId);
		}
		Response<PageResult<PmPurchaseReceiptDto>> dtoResponse = pmPurchaseReceiptQueryDubboService
				.queryReceiptByPages(paramDto);
		PageResult<PmPurchaseReceiptDto> dtoPage = dtoResponse.getResultObject();
		if (dtoPage != null && dtoPage.getData() != null && dtoPage.getData().size() > 0) {
			list = BeanConvertUtils.convertList(dtoPage.getData(), PmPurchaseReceiptVo.class);
			voPage.setPageNum(dtoPage.getPageNum());
			voPage.setPageSize(dtoPage.getPageSize());
			voPage.setTotal(dtoPage.getTotal());
		}
		voPage.setData(list);
		voResponse.setResultObject(voPage);
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setSuccess(dtoResponse.isSuccess());

		return voResponse;
	}



	/**
	 * 
	 * 通过收货单id查询详细信息（包含明细）
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "queryReceiptDetailById", method = RequestMethod.GET)
	public Response<PmPurchaseReceiptDetailDto> queryReceiptDetailById(Long id) {
		return pmPurchaseReceiptQueryDubboService.queryReceiptDetailById(id);
	}



	/**
	 * 
	 * <method description>重新推送采购收货单到MQ
	 *
	 * @param purchaseOrderNo
	 *            采购定单单号
	 * @return
	 */

	@RequestMapping(value = "/rePushPurchaseReceiptToMQ", method = RequestMethod.GET)
	public Response<Boolean> rePushPurchaseReceiptToMQ(String purchaseOrderNo) {
		return pmPurchaseReceiptWriteDubboService.pushPurchaseReceiptToMQ(purchaseOrderNo);
	}


	/**
	 *
	 * 查询收货单列表导出
	 *
	 * @param paramVo
	 * @return
	 */
	@RequestMapping(value = "exportReceiptList", method = RequestMethod.GET)
	public void exportReceiptList(PmPurchaseReceiptParamVo paramVo, HttpServletRequest req, HttpServletResponse resp) {
		log.info("exportReceiptList --> Param:{}", JSON.toJSONString(paramVo));
		PmPurchaseReceiptParamDto paramDto = BeanConvertUtils.convert(paramVo, PmPurchaseReceiptParamDto.class);

		String companyId = getCurrentCompanyId(req);
		if (!StringUtils.isEmpty(companyId)) {
			paramDto.setBranchCompanyId(companyId);
		}

		List<PmPurchaseReceiptExportDto> exportDtos = pmPurchaseReceiptQueryDubboService.exportReceipt(paramDto).getResultObject();
		try {
			excelExportService.excelExport("purchase_receive", exportDtos, resp);
		} catch (Exception e) {
			logger.error("exportReceiptList 收货单导出异常 Param:{} --> {}", JSON.toJSONString(paramVo), ExceptionUtils.getFullStackTrace(e));
		}

	}
}
