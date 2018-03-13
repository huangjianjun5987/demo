package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDetailDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptExportDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptParamDto;

import java.util.List;

/**
 * 
 * 采购收货单查询dubboservice
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年7月29日
 */
public interface PmPurchaseReceiptQueryDubboService {

	/**
	 * 
	 * 分页查询收货单列表
	 *
	 * @param paramDto
	 * @return
	 */
	public Response<PageResult<PmPurchaseReceiptDto>> queryReceiptByPages(PmPurchaseReceiptParamDto paramDto);



	/**
	 * 
	 * 根据收货单id查询收货单详情
	 *
	 * @param id
	 * @return
	 */
	public Response<PmPurchaseReceiptDetailDto> queryReceiptDetailById(Long id);


	/**
	 * 导出收货单详情列表
	 * @param paramDto
	 * @return
	 */
	Response<List<PmPurchaseReceiptExportDto>> exportReceipt(PmPurchaseReceiptParamDto paramDto);

	/**
	 * 根据采购单ID生成收货单
	 * @param id
	 * @return
	 */
	Response<PmPurchaseReceiptDto> queryPurchaseReceiptByPurchaseOrderId(Long id);

	/**
	 * 根据采购单查询
	 * @param id
	 * @return
	 */
	Response<PmPurchaseReceiptDetailDto> queryReceiptDetailByPurchaseOrderId(Long id);
}
