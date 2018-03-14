package com.yatang.sc.operation.web;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.operation.util.SessionUtil;
import com.yatang.sc.operation.vo.ag.*;
import com.yatang.sc.validgroup.*;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationSCService;
import com.yatang.xc.mbd.biz.org.sc.dto.QueryScGroupStoreDto;
import com.yatang.xc.mbd.biz.org.sc.dto.QueryStoreAreaGroupDto;
import com.yatang.xc.mbd.biz.org.sc.dto.StoreAreaGroupDto;
import com.yatang.xc.mbd.biz.org.sc.dto.StoreAreaGroupResultDto;
import com.yatang.xc.mbd.org.es.dubboservice.OrganizationIndexSCDubboService;
import com.yatang.xc.mbd.org.es.sc.dto.QueryIndexScGroupStoreDto;
import com.yatang.xc.mbd.org.es.sc.dto.ScGroupStoreResultDto;
import com.yatang.xc.mbd.web.commons.ResourceUtil;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

/**
 * 区域组控制层
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/sc/areaGroup")
public class AreaGroupAction extends BaseAction {

	@Autowired
	OrganizationSCService organizationSCService;

	@Autowired
	OrganizationIndexSCDubboService organizationIndexSCDubboService;




	/**
	 * @Description: 查询区域组分页显示
	 * @author zhudanning
	 * @date 2018/1/11- 14:09
	 * @param
	 */
	@RequestMapping(value = "/queryAreaGroupList", method = RequestMethod.GET)
	public Response<StoreAreaGroupResultVo> queryAreaGroupList(QueryParamAreaVo queryParamAreaVo, HttpServletRequest request){
		log.info("查询区域组分页显示,区域组的编码和名称={},子公司的Id={}",queryParamAreaVo.getAreaGroupIdOrName(),queryParamAreaVo.getBranchCompanyId());
		Response<StoreAreaGroupResultVo> result = new Response<>();
		// 只能查询当前用户子公司的区域组
		LoginInfoVO userInfo = (LoginInfoVO) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
		if (null==userInfo){
			result.setErrorMessage("查询用户信息失败,操作终止");
			result.setCode(CommonsEnum.RESPONSE_500.getCode());
			result.setSuccess(false);
			return result;
		}
		String companyId;
		if (userInfo.getCompanyType() == 0) {
			companyId = userInfo.getCompanyId();
		} else {
			companyId = queryParamAreaVo.getBranchCompanyId();
		}
		queryParamAreaVo.setBranchCompanyId(companyId);
		QueryStoreAreaGroupDto queryStoreAreaGroupDto = BeanConvertUtils.convert(queryParamAreaVo,QueryStoreAreaGroupDto.class);
		Response<StoreAreaGroupResultDto> resResultDto = organizationSCService.queryPageStoreAreaGroup(queryStoreAreaGroupDto);
		//将数据转换成前端要的格式
		StoreAreaGroupResultVo storeAreaGroupResultVo = BeanConvertUtils.convert(resResultDto.getResultObject(),StoreAreaGroupResultVo.class);
		storeAreaGroupResultVo.setTotal(resResultDto.getResultObject().getTotalSize());
		storeAreaGroupResultVo.setData(resResultDto.getResultObject().getRecords());
		result.setCode(resResultDto.getCode());
		result.setErrorMessage(resResultDto.getErrorMessage());
		result.setResultObject(storeAreaGroupResultVo);
		result.setSuccess(resResultDto.isSuccess());
		result.setPageNum(resResultDto.getPageNum());
		return result;
	}


	/**
	 * @Description: 获取区域组下面所有的门店信息
	 * @author zhudanning
	 * @date 2018/1/11- 14:09
	 * @param
	 */
	@RequestMapping(value = "/queryStoresFromGroup", method = RequestMethod.GET)
	public Response<ScGroupStoreResultVo> queryStoresFromGroup(@Validated({ GroupOne.class })  QueryParamAreaVo queryParamAreaVo){
		QueryIndexScGroupStoreDto groupStoreDto = BeanConvertUtils.convert(queryParamAreaVo, QueryIndexScGroupStoreDto.class);
		groupStoreDto.setPageNo(queryParamAreaVo.getPageNum());
		log.info("获取区域组下面所有的门店信息,QueryScGroupStoreDto={}",JSON.toJSONString(groupStoreDto));
		Response<ScGroupStoreResultVo> result = new Response<>();
		Response<ScGroupStoreResultDto> resGroupDto = organizationIndexSCDubboService.queryScGroupStorePage(groupStoreDto);
		ScGroupStoreResultDto resultDto = resGroupDto.getResultObject();
		ScGroupStoreResultVo resultVo = BeanConvertUtils.convert(resultDto,ScGroupStoreResultVo.class);
        resultVo.setTotal(resultDto.getTotalCount());
		resultVo.setPageNum(resultDto.getPageNo());
		resultVo.setData(resultDto.getRecords());
		//将数据转换成前端要的格式
		result.setPageNum(resGroupDto.getPageNum());
		result.setSuccess(resGroupDto.isSuccess());
		result.setResultObject(resultVo);
		result.setErrorMessage(resGroupDto.getErrorMessage());
		result.setCode(resGroupDto.getCode());
		return result;
	}



	/**
	 * @Description: 新增区域组
	 * @author zhudanning
	 * @date 2018/1/11- 14:10
	 * @param
	 */
	@RequestMapping(value = "/insertAreaGroup", method = RequestMethod.POST)
	public Response<Integer> insertAreaGroup(@Validated({ GroupOne.class }) @RequestBody WriteParamAreaVo writeParamAreaVo, HttpSession session){
		LoginInfoVO currentUser = (LoginInfoVO)
				session.getAttribute(CURRENT_USER);
		if (null == currentUser || null == currentUser.getUserId()) {
			log.error("获取用户信息出错，areaGroupVo={},session={}",
					JSON.toJSONString(writeParamAreaVo),
					JSON.toJSONString(session));
			return getFailResponse();
		}
		log.info("新增区域组,区域组的名称={}",writeParamAreaVo.getAreaGroupName());
		//获得创建的相关信息
		StoreAreaGroupDto storeAreaGroupDto = BeanConvertUtils.convert(writeParamAreaVo,StoreAreaGroupDto.class);
		storeAreaGroupDto.setCreateBy(currentUser.getLoginName());
		storeAreaGroupDto.setCreateDate(new Date());
		Response<Integer> result = organizationSCService.insertStoreAreaGroup(storeAreaGroupDto);
		return result;
	}




	/**
	 * @Description: 新增区域组里的门店
	 * @author zhudanning
	 * @date 2018/1/11- 14:10
	 * @param
	 */
	@RequestMapping(value = "/insertStoreToGroup", method = RequestMethod.POST)
	public Response<Integer> insertStoreToGroup(@Validated({GroupTwo.class}) @RequestBody WriteParamAreaVo writeParamAreaVo){
		Response<Integer> response = new Response<>();
		LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
		if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
			response.setCode(CommonsEnum.RESPONSE_401.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			return response;
		}
		String[] stores = writeParamAreaVo.getStoreIds().split(",");
		List<String> storeIds = Arrays.asList(stores);
		log.info("新增区域组里的门店，storeIds={},区域组编码={},区域组名称={}",
				JSON.toJSONString(storeIds),writeParamAreaVo.getAreaGroupCode(),writeParamAreaVo.getAreaGroupName());
		Response<Integer> result = organizationSCService.updateStoreAreaGroup(storeIds,writeParamAreaVo.getAreaGroupCode(),writeParamAreaVo.getAreaGroupName());
		return result;
	}





	/**
	 * @Description: 删除区域组
	 * @author zhudanning
	 * @date 2018/1/11- 14:11
	 * @param
	 */
	@RequestMapping(value = "/deleteAreaGroup", method = RequestMethod.POST)
	public Response<Integer> deleteAreaGroup(@Validated({GroupThree.class}) @RequestBody WriteParamAreaVo writeParamAreaVo, HttpSession session){
		LoginInfoVO currentUser = (LoginInfoVO)
				session.getAttribute(CURRENT_USER);
		if (null == currentUser || null == currentUser.getUserId()) {
			log.error("获取用户信息出错，areaGroupCode={},session={}",
					JSON.toJSONString(writeParamAreaVo),
					JSON.toJSONString(session));
			return getFailResponse();
		}
		List<String> areaGroupCodes = Arrays.asList(writeParamAreaVo.getAreaGroupCode().split(","));
		log.info("删除区域组,areaGroupCodes={}",JSON.toJSONString(areaGroupCodes));
		Response<Integer> result = organizationSCService.deleteStoreAreaGroup(areaGroupCodes,currentUser.getLoginName(),new Date());
		return result;
	}



	/**
	 * @Description: 删除区域组中的门店
	 * @author zhudanning
	 * @date 2018/1/11- 14:11
	 * @param
	 */
	@RequestMapping(value = "/deleteStoreFromArea", method = RequestMethod.POST)
	public Response<Integer> deleteStoreFromArea(@Validated({DefaultGroup.class}) @RequestBody WriteParamAreaVo writeParamAreaVo){
		Response<Integer> response = new Response<>();
		LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
		if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
			response.setCode(CommonsEnum.RESPONSE_401.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			return response;
		}
		List<String> storeIds = Arrays.asList(writeParamAreaVo.getStoreIds().split(","));
		log.info("删除区域组中的门店,storeIds:{}",JSON.toJSONString(storeIds));
		Response<Integer> result = organizationSCService.updateStoreAreaGroup(storeIds,null,null);
		return result;
	}







	/**
	 * @Description: 根据区域组名字获取区域组信息
	 * @author zhudanning
	 * @date 2018/1/11- 14:09
	 * @param
	 */
	@RequestMapping(value ="/queryStoreGroupByName", method = RequestMethod.GET)
	public Response<StoreAreaVo> queryStoreGroupByName(String areaGroupName){
		log.info("根据区域组名称获取区域组信息,areaGroupName={}",areaGroupName);
		Response<StoreAreaGroupDto> resAreaGroupDto = organizationSCService.queryStoreGroupByName(areaGroupName);
		//判断是否已经存在同名的区域组
		StoreAreaVo storeAreaVo = BeanConvertUtils.convert(resAreaGroupDto.getResultObject(),StoreAreaVo.class);
		Response<StoreAreaVo> resAreaGroupVo = new Response<>();
		if(storeAreaVo != null){
			storeAreaVo.setExists(true);
			resAreaGroupVo.setSuccess(false);
			resAreaGroupVo.setResultObject(storeAreaVo);
			resAreaGroupVo.setCode(CommonsEnum.RESPONSE_50011.getCode());
			resAreaGroupVo.setErrorMessage(CommonsEnum.RESPONSE_50011.getName());
			return resAreaGroupVo;
		}
		resAreaGroupVo.setCode(resAreaGroupDto.getCode());
		resAreaGroupVo.setSuccess(resAreaGroupDto.isSuccess());
		resAreaGroupVo.setResultObject(new StoreAreaVo());
		resAreaGroupVo.setErrorMessage(resAreaGroupDto.getErrorMessage());
		return resAreaGroupVo;
	}


	/**
	 * @Description: 删除区域组中的所有门店
	 * @author zhudanning
	 * @date 2018/1/11- 14:11
	 * @param
	 */
	@RequestMapping(value = "/deleteAllStoresFromArea", method = RequestMethod.POST)
	public Response<Integer> deleteAllStoresFromArea(@Validated({GroupFour.class}) @RequestBody WriteParamAreaVo writeParamAreaVo){
		Response<Integer> result = new Response<>();
		LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
		if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
			result.setCode(CommonsEnum.RESPONSE_401.getCode());
			result.setSuccess(false);
			result.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			return result;
		}
		QueryScGroupStoreDto queryScGroupStoreDto = BeanConvertUtils.convert(writeParamAreaVo,QueryScGroupStoreDto.class);
		List<String> branchCompanyIds = new ArrayList<>();
		branchCompanyIds.add(writeParamAreaVo.getBranchCompanyId());
		queryScGroupStoreDto.setAreaGroupId(writeParamAreaVo.getAreaGroupCode());
		queryScGroupStoreDto.setExistsAreaGroup(false);
		queryScGroupStoreDto.setAreaGroupName(writeParamAreaVo.getAreaGroupName());
		queryScGroupStoreDto.setBranchCompanyIds(branchCompanyIds);
		log.info("删除区域组中所有的门店,queryScGroupStoreDto:{}",JSON.toJSONString(queryScGroupStoreDto));
		result = organizationSCService.updateStoreAreaGroupQuery(queryScGroupStoreDto);
		return result;
	}


	/**
	 * @Description: 新增所有的查询结果的门店到区域组
	 * @author zhudanning
	 * @date 2018/1/11- 14:10
	 * @param
	 */
	@RequestMapping(value = "/insertAllStoresToGroup", method = RequestMethod.POST)
	public Response<Integer> insertAllStoresToGroup(@Validated({GroupFour.class}) @RequestBody WriteParamAreaVo writeParamAreaVo){
		Response<Integer> response = new Response<>();
		LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
		if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
			response.setCode(CommonsEnum.RESPONSE_401.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			return response;
		}
		QueryScGroupStoreDto queryScGroupStoreDto = BeanConvertUtils.convert(writeParamAreaVo,QueryScGroupStoreDto.class);
		List<String> branchCompanyIds = new ArrayList<>();
		branchCompanyIds.add(writeParamAreaVo.getBranchCompanyId());
		queryScGroupStoreDto.setAreaGroupId(writeParamAreaVo.getAreaGroupCode());
		queryScGroupStoreDto.setExistsAreaGroup(true);
		queryScGroupStoreDto.setAreaGroupName(writeParamAreaVo.getAreaGroupName());
		queryScGroupStoreDto.setBranchCompanyIds(branchCompanyIds);
		log.info("新增所有的查询结果的门店到区域组里，queryScGroupStoreDto={}", JSON.toJSONString(queryScGroupStoreDto));
		Response<Integer> result = organizationSCService.updateStoreAreaGroupQuery(queryScGroupStoreDto);
		return result;
	}
}
