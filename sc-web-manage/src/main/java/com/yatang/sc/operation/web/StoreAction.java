package com.yatang.sc.operation.web;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.operation.vo.StoreQueryVo;
import com.yatang.sc.operation.vo.StoreSimpleResultVo;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationSCService;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * <class description>门店信息接口
 *
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月26日
 */
@Slf4j
@RestController
@RequestMapping(value = "/sc/store")
public class StoreAction extends BaseAction {

	@Autowired
	private OrganizationSCService organizationSCService;



	/**
	 *
	 * <method description>根据参数在门店编码和门店名称两个字段里面搜索门店信息，需要过滤当前操作人的子公司ID
	 *
	 * @param storeQueryVo
	 * @return
	 */
	@RequestMapping(value = "/getStoreInfo", method = RequestMethod.GET)
	public Response<PageResult<StoreSimpleResultVo>> getStoreInfo(@Valid StoreQueryVo storeQueryVo,
			HttpSession session) {
		Map<String, Object> param = new HashMap<>();
		LoginInfoVO currentUser = (LoginInfoVO) session.getAttribute(CURRENT_USER);
		if (null == currentUser || null == currentUser.getCompanyId()) {
			log.info("取当前用户信息为空。^_^");
			getFailResponse();
		}
		param.put("branchCompanyId", currentUser.getCompanyId());
		param.put("idOrName", storeQueryVo.getParam());
		Response<Integer> total = organizationSCService.querySCStoreByIdOrName(param);
		if (total.isSuccess()) {
			if (0 == total.getResultObject()) {
				log.info("查询门店总数成功，但是总数为0。^_^");
				return getSuccessResponse();
			}
		} else {
			log.info("查询门店总数出错了。(ㄒoㄒ)");
			return getFailResponse();
		}
		param.put("pageCount", true);
		Response<List<StoreDto>> result = organizationSCService.queryPageSCStoreByIdOrName(storeQueryVo.getPageNum(),
				storeQueryVo.getPageSize(), param);
		if (!result.isSuccess()) {
			log.info("查询门店列表出错了。(ㄒoㄒ)");
			getFailResponse();
		}
		List<StoreDto> resultObject = result.getResultObject();
		PageResult<StoreSimpleResultVo> pageResult = new PageResult<>();
		List<StoreSimpleResultVo> list = new ArrayList<>();
		for (StoreDto storeDto : resultObject) {
			StoreSimpleResultVo storeSimpleResultVo = BeanConvertUtils.convert(storeDto, StoreSimpleResultVo.class);
			list.add(storeSimpleResultVo);
		}
		pageResult.setData(list);
		pageResult.setPageNum(storeQueryVo.getPageNum());
		pageResult.setPageSize(list.size());
		pageResult.setTotal(total.getResultObject() + 0L);
		return getSuccessResponse(pageResult);
	}
}
