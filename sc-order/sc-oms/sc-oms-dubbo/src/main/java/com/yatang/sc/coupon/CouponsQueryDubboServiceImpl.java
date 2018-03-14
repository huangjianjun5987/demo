package com.yatang.sc.coupon;

import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.coupon.dubboservice.CouponsQueryDubboService;
import com.yatang.sc.dto.CouponActivityDto;
import com.yatang.sc.dto.CouponActivityParamDto;
import com.yatang.sc.dto.CouponRecordDto;
import com.yatang.sc.dto.CouponRecordParamDto;
import com.yatang.sc.dto.CouponsDto;
import com.yatang.sc.dto.CouponsParamDto;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.order.domain.CouponActivityParamPo;
import com.yatang.sc.order.domain.CouponActivityPo;
import com.yatang.sc.order.domain.CouponRecordDetailPo;
import com.yatang.sc.order.domain.CouponRecordParamPo;
import com.yatang.sc.order.domain.CouponsParamPo;
import com.yatang.sc.order.domain.CouponsPo;
import com.yatang.sc.order.domain.OrderGiveCouponToStoreLog;
import com.yatang.sc.order.domain.OrderPrice;
import com.yatang.sc.order.dubboservice.WebOrderDubboService;
import com.yatang.sc.order.service.CouponActivityService;
import com.yatang.sc.order.service.CouponRecordService;
import com.yatang.sc.order.service.CouponsService;
import com.yatang.sc.order.service.OrderGiveCouponToStoreLogService;
import com.yatang.sc.order.service.OrderPriceService;
import com.yatang.sc.order.service.PromotionService;
import com.yatang.sc.payment.common.ResponseUtils;
import com.yatang.xc.mbd.biz.org.dto.FranchiseeDto;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationSCService;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.biz.org.sc.dto.QueryStoreScDto;
import com.yatang.xc.mbd.biz.org.sc.dto.StoreScDto;
import com.yatang.xc.oc.b.member.biz.core.dto.LevelPrivilegeDto;
import com.yatang.xc.oc.b.member.biz.core.dto.PrivilegeInfoDto;
import com.yatang.xc.oc.b.member.biz.core.dubboservice.PrivilegeInfoDubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: yinyuxin
 * @date: 2017/9/19 20:30
 * @version: v1.0
 */
@Service("couponsQueryDubboService")
@Transactional
public class CouponsQueryDubboServiceImpl implements CouponsQueryDubboService{

	@Autowired
	private CouponsService        couponsService;
	@Autowired
	private CouponRecordService   couponRecordService;
	@Autowired
	private CouponActivityService couponActivityService;
	@Autowired
	private PromotionService      promotionService;
	@Autowired
	private OrganizationService   organizationService;
	@Autowired
	private OrganizationSCService organizationSCService;
	@Autowired
	private PrivilegeInfoDubboService privilegeInfoDubboService;
	@Autowired
	private WebOrderDubboService webOrderDubboService;
	@Autowired
	private OrderGiveCouponToStoreLogService orderGiveCouponToStoreLogService;
	@Autowired
	private OrderPriceService orderPriceService;

	private Logger log= LoggerFactory.getLogger(this.getClass());

	@Override
	public Response<CouponsDto> queryCouponsById(String id) {
		log.info("------------couponsQueryDubboService-->queryCouponsById(),param:"+ JSONObject.toJSONString(id)+"------------");
		Response<CouponsDto> response=new Response<CouponsDto>();
		try {
			CouponsPo couponsPo = couponsService.queryById(id);
			response.setResultObject(BeanConvertUtils.convert(couponsPo,CouponsDto.class));
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
			return response;
		} catch (Exception e) {
			log.error("------------couponsQueryDubboService-->queryCouponsById(),error:"+ e.toString()+"------------");
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			return response;
		}
	}



	@Override
	public Response<PageResult<CouponsDto>> queryCouponsList(CouponsParamDto couponsParamDto) {
		log.info("------------couponsQueryDubboService-->queryCouponsList(),param:"+ JSONObject.toJSONString(couponsParamDto)+"------------");
		Response<PageResult<CouponsDto>> response=new Response<PageResult<CouponsDto>>();
		PageResult<CouponsDto> pageResult=new PageResult<CouponsDto>();
		List<CouponsDto> couponsDtoList= Lists.newArrayList();
		try {
			//0:验证参数是否为null
			if (null==couponsParamDto){
				throw new RuntimeException("请求参数为null");
			}
			//1:调用service查询数据
			couponsParamDto.setPageNum(
					null==couponsParamDto.getPageNum()? Integer.valueOf( CommonsEnum.RESPONSE_PAGE_NUM.getCode()) :couponsParamDto.getPageNum());
			couponsParamDto.setPageSize(
					null==couponsParamDto.getPageSize()? Integer.valueOf( CommonsEnum.RESPONSE_PAGE_SIZE.getCode()) :couponsParamDto.getPageSize());
			if (Objects.equal("ended",couponsParamDto.getStatus())){
				couponsParamDto.setEndDate(new Date());
				couponsParamDto.setStatus(null);
			}else if(!Objects.equal(null,couponsParamDto.getStatus())){
				couponsParamDto.setStartDate(new Date());
			}
			CouponsParamPo couponsParamPo=BeanConvertUtils.convert(couponsParamDto,CouponsParamPo.class);
			PageInfo<CouponsPo> pageInfo = couponsService.queryCouponsList(couponsParamPo);
			//2:封装response
			if (pageInfo!=null && pageInfo.getList()!=null && pageInfo.getList().size()>0){
				couponsDtoList=BeanConvertUtils.convertList(pageInfo.getList(),CouponsDto.class);
				//2.1:根据结束时间替换已结束状态
				for (CouponsDto couponsDto:couponsDtoList){
					Long nowTime=(new Date()).getTime();
					if(couponsDto.getEndDate().getTime()<nowTime){
						couponsDto.setStatus("ended");
					}
				}
			}
			pageResult.setData(couponsDtoList);
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setTotal(pageInfo.getTotal());
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(pageResult);
			response.setSuccess(true);
			return response;
		} catch (Exception e) {
			log.error("------------couponsQueryDubboService-->queryCouponsList(),error:"+ e.toString()+"------------");
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			return response;
		}
	}



	/**
	 * 查询可发放的优惠券
	 * @param couponsParamDto
	 * @return
	 */
	@Override
	public Response<PageResult<CouponsDto>> queryAliveCouponsList(CouponsParamDto couponsParamDto) {
		log.info("------------couponsQueryDubboService-->queryAliveCouponsList(),param:"+ JSONObject.toJSONString(couponsParamDto)+"------------");
		Response<PageResult<CouponsDto>> response=new Response<PageResult<CouponsDto>>();
		PageResult<CouponsDto> pageResult=new PageResult<CouponsDto>();
		List<CouponsDto> couponsDtoList= Lists.newArrayList();
		try {
			//0:验证参数是否为null
			if (null==couponsParamDto){
				throw new RuntimeException("请求参数为null");
			}
			//1:调用service查询数据
			couponsParamDto.setPageNum(
					null==couponsParamDto.getPageNum()? Integer.valueOf( CommonsEnum.RESPONSE_PAGE_NUM.getCode()) :couponsParamDto.getPageNum());
			couponsParamDto.setPageSize(
					null==couponsParamDto.getPageSize()? Integer.valueOf( CommonsEnum.RESPONSE_PAGE_SIZE.getCode()) :couponsParamDto.getPageSize());
			CouponsParamPo couponsParamPo=BeanConvertUtils.convert(couponsParamDto,CouponsParamPo.class);
			couponsParamPo.setGrantChannel("platform");
			PageInfo<CouponsPo> pageInfo = couponsService.queryAliveCouponsList(couponsParamPo);
			//2:封装response
			if (pageInfo!=null && pageInfo.getList()!=null && pageInfo.getList().size()>0){
				couponsDtoList=BeanConvertUtils.convertList(pageInfo.getList(),CouponsDto.class);
			}
			pageResult.setData(couponsDtoList);
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setTotal(pageInfo.getTotal());
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(pageResult);
			response.setSuccess(true);
			return response;
		} catch (Exception e) {
			log.error("------------couponsQueryDubboService-->queryAliveCouponsList(),error:"+ e.toString()+"------------");
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			return response;
		}
	}



	@Override
	public Response<PageResult<CouponRecordDto>> queryCouponRecordList(CouponRecordParamDto couponRecordParamDto) {
		log.info("------------couponsQueryDubboService-->queryCouponRecordList(),param:"+ JSONObject.toJSONString(couponRecordParamDto)+"------------");
		Response<PageResult<CouponRecordDto>> response=new Response<PageResult<CouponRecordDto>>();
		PageResult<CouponRecordDto> pageResult=new PageResult<CouponRecordDto>();
		List<CouponRecordDto> data=Lists.newArrayList();
		try {
			//0:验证参数是否为null
			if (null==couponRecordParamDto){
				throw new RuntimeException("请求参数为null");
			}

			//1:通过门店名称查询门店id集合(暂时只提供门店id精确查询)
			List<String> storeIds=new ArrayList<String>();
			List<String> franchiseeIds=new ArrayList<String>();
			if (couponRecordParamDto.getStoreId()!=null){
				storeIds.add(couponRecordParamDto.getStoreId());
			}
			if (storeIds==null || storeIds.size()<=0){
				//2:通过加盟商名称查询加盟商id集合(暂时只提供加盟商id精确查询)

				if (couponRecordParamDto.getFranchiseeId()!=null){
					franchiseeIds.add(couponRecordParamDto.getFranchiseeId());
				}
				if (franchiseeIds==null || franchiseeIds.size()<=0){
					//1031补充：通过子公司id查询加盟商id集合
					if (couponRecordParamDto.getBranchCompanyId()!=null){
						Response<List<FranchiseeDto>> franchisees = organizationService
								.queryFranchiseesByBranchCompanyId(couponRecordParamDto.getBranchCompanyId());
						if (franchisees.isSuccess() ){
							if (franchisees.getResultObject()!=null && franchisees.getResultObject().size()>0){
								for (FranchiseeDto franchiseeDto:franchisees.getResultObject()){
									franchiseeIds.add(franchiseeDto.getId());
								}
							}
						}else {
							throw new RuntimeException("通过子公司id查询加盟商信息失败:"+franchisees.getErrorMessage());
						}
					}
				}
			}
			//3:调用service查询数据
			CouponRecordParamPo couponRecordParamPo=BeanConvertUtils.convert(couponRecordParamDto,CouponRecordParamPo.class);
			couponRecordParamPo.setStoreIds(storeIds);
			couponRecordParamPo.setFranchiseeIds(franchiseeIds);
			//3.1:封装分页数据
			Integer pageSize=
					couponRecordParamDto.getPageSize()==null? Integer.valueOf(CommonsEnum.RESPONSE_PAGE_SIZE.getCode()) :couponRecordParamDto.getPageSize();
			Integer pageNum=couponRecordParamDto.getPageNum()==null? Integer.valueOf(CommonsEnum.RESPONSE_PAGE_NUM.getCode()) :couponRecordParamDto.getPageNum();
			couponRecordParamPo.setPageNum(pageNum);
			couponRecordParamPo.setPageSize(pageSize);
			PageInfo<CouponRecordDetailPo> pageInfo = couponRecordService.queryRecordList(couponRecordParamPo);
			//4:封装response
			pageResult.setTotal(pageInfo.getTotal());
			data=BeanConvertUtils.convertList(pageInfo.getList(), CouponRecordDto.class);
			if (data!=null){
				for (CouponRecordDto couponRecordDto:data){
					//根据门店id查询门店名称、子公司名称、加盟商id、加盟商名称
					Response<StoreDto> storeDtoResponse = organizationService
							.queryStoreById(couponRecordDto.getStoreId());
					log.info("------------couponsQueryDubboService-->queryCouponRecordList(),查询到子公司等信息:"+ JSONObject.toJSONString(storeDtoResponse)+"------------");
					if (storeDtoResponse.getResultObject()!=null){
						couponRecordDto.setBranchCompanyName(storeDtoResponse.getResultObject().getBranchCompanyName());
						couponRecordDto.setFranchinessController(storeDtoResponse.getResultObject().getFranchiseeName());
						couponRecordDto.setStoreName(storeDtoResponse.getResultObject().getName());
					}
				}
			}
			pageResult.setData(data);
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setTotal(pageInfo.getTotal());
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(pageResult);
			response.setSuccess(true);
			return response;
		} catch (Exception e) {
			log.error("------------couponsQueryDubboService-->queryCouponRecordList(),error:"+ e.toString()+"------------");
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			return response;
		}
	}



	@Override
	public Response<PageResult<CouponActivityDto>> queryCouponActivityActiveList(
			CouponActivityParamDto couponActivityParamDto) {
		log.info("------------couponsQueryDubboService-->queryCouponActivityActiveList(),param:"+ JSONObject.toJSONString(couponActivityParamDto)+"------------");
		Response<PageResult<CouponActivityDto>> response=new Response<PageResult<CouponActivityDto>>();
		PageResult<CouponActivityDto> pageResult=new PageResult<CouponActivityDto>();
		List<CouponActivityDto> data=Lists.newArrayList();
		try {
			//eg:参数情况  1、只有门店-直接查  2、只有加盟商-查询加盟商下所有门店作为查询条件 3、只有子公司-查询子公司下所有门店
			//4、有门店和加盟商 - 加盟商条件作废 直接岔门店  5、有门店和子公司-同4  6、门店、加盟商、子公司都有-只用门店
			//目前问题：在没有门店参数的条件下，若加盟商或者子公司下没有门店，会导致查出所有的数据，原因：最后调用sql时，只用到了门店，所以这种情况下会导致查询条件为空，即全部查询，
			//解决办法：下属门店为空时，手动将门店查询条件设置为不可能的值


			//0:验证参数是否为null
			if (null==couponActivityParamDto){
				throw new RuntimeException("请求参数为null");
			}
			//1:通过门店名称查询门店id集合(暂时只提供门店id精确查询)
			List<String> storeIds=new ArrayList<String>();
			if (couponActivityParamDto.getStoreId()!=null){
				storeIds.add(couponActivityParamDto.getStoreId());
			}
			if (storeIds ==null ||storeIds.size()<=0){
				//2:通过加盟商名称查询加盟商id集合(暂时只提供加盟商id精确查询)
				List<String> franchiseeIds=Lists.newArrayList();
				if (couponActivityParamDto.getFranchiseeId()!=null){
					franchiseeIds.add(couponActivityParamDto.getFranchiseeId());
				}
				if (franchiseeIds ==null ||franchiseeIds.size()<=0){
					//1031补充：通过子公司id查询门店id集合

					if (couponActivityParamDto.getBranchCompanyId()!=null){

						Response<Integer> dtoResponse = organizationSCService
								.queryStoreBybranchCompanyId(couponActivityParamDto.getBranchCompanyId());
						if (dtoResponse.isSuccess()){
							Response<List<StoreDto>> listResponse = organizationSCService
									.queryStorePageBybranchCompanyId(couponActivityParamDto.getBranchCompanyId(), 1,
											Integer.MAX_VALUE, dtoResponse.getResultObject());
							if (listResponse.isSuccess()){
								List<StoreDto> storeDtos = listResponse.getResultObject();
								if (storeDtos!=null && storeDtos.size()>0){
									for (StoreDto storeDto:storeDtos){
										storeIds.add(storeDto.getId());
									}
								}
							}
						}else {
							throw new RuntimeException("通过子公司id查询门店集合失败:"+dtoResponse.getErrorMessage());
						}
					}
				}
				//3:通过加盟商id集合查询门店id集合
				if (franchiseeIds!=null && franchiseeIds.size()>0){
					for (String franchiseeId:franchiseeIds){
						QueryStoreScDto queryStoreScDto=new QueryStoreScDto();
						queryStoreScDto.setFranchiseeId(franchiseeId);
						queryStoreScDto.setPageSize(Integer.MAX_VALUE);
						Response<List<StoreScDto>> listResponse = organizationSCService.queryPageSCStoreInfo(queryStoreScDto);
						log.info("------------couponsQueryDubboService-->queryCouponActivityActiveList(),通过加盟商id获取门店id集合:"+ JSONObject.toJSONString(listResponse)+"------------");
						if (listResponse.getResultObject()!=null && listResponse.getResultObject().size()>0){
							for (StoreScDto storeDto:listResponse.getResultObject()){
								storeIds.add(storeDto.getStoreId());
							}
						}
					}
				}
			}

			//参数中加盟商或者子公司有值，但筛选后门店为空，才不做查询，避免出现未天任何参数，但没做查询
			if (couponActivityParamDto.getFranchiseeId()!=null ||couponActivityParamDto.getBranchCompanyId()!=null){
				if (storeIds==null||storeIds.size()<=0){
					return this.queryCouponActivityActiveListHelper(couponActivityParamDto);
				}
			}

			//4:调用service查询数据
			CouponActivityParamPo couponActivityParamPo=BeanConvertUtils.convert(couponActivityParamDto,CouponActivityParamPo.class);
			couponActivityParamPo.setStoreIds(storeIds);
			couponActivityParamPo.setState((couponActivityParamPo.getQueryType() == 1) ? "active":"canceled");
			PageInfo<CouponActivityPo> pageInfo = couponActivityService
					.queryActivityActiveList(couponActivityParamPo);
			data=BeanConvertUtils.convertList(pageInfo.getList(),CouponActivityDto.class);
			//5：补充需要显示的数据
			if (data!=null && data.size()>0){
				for (CouponActivityDto couponActivityDto:data){
					//5.1:查询优惠券名称
					String promName=promotionService.queryPromotionDetail(couponActivityDto.getPromoId()).getPromotionName();
					couponActivityDto.setPromotionName(promName);
					//5.2:根据门店id查询门店名称、子公司名称、加盟商id、加盟商名称
					Response<StoreDto> storeDtoResponse = organizationService
							.queryStoreById(couponActivityDto.getStoreId());
					log.info("------------couponsQueryDubboService-->queryCouponActivityActiveList(),查询到子公司等信息:"+ JSONObject.toJSONString(storeDtoResponse)+"------------");
					if (storeDtoResponse.getResultObject()!=null){
						couponActivityDto.setBranchCompanyName(storeDtoResponse.getResultObject().getBranchCompanyName());
						couponActivityDto.setFranchinessController(storeDtoResponse.getResultObject().getFranchiseeName());
						couponActivityDto.setStoreName(storeDtoResponse.getResultObject().getName());
						couponActivityDto.setFranchiseeId(storeDtoResponse.getResultObject().getFranchiseeId());
					}
				}
			}
			//6:封装response
			pageResult.setData(data);
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setTotal(pageInfo.getTotal());
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(pageResult);
			response.setSuccess(true);
			return response;
		} catch (Exception e) {
			log.error("------------couponsQueryDubboService-->queryCouponActivityActiveList(),error:"+ e.toString()+"------------");
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			return response;
		}

	}



	@Override
	public List<CouponsDto> queryToGiveCoupons() {
		return BeanConvertUtils.convertList(couponsService.queryToGiveCoupons(), CouponsDto.class);
	}

	//@Override 该方法没有使用，查询优惠券参与数据时，门店、加盟商、子公司取交集来查（与实际业务需求不符） 目前使用的上面queryCouponActivityActiveList方法
	/*
	public Response<PageResult<CouponActivityDto>> queryCouponActivityActiveList2(
			CouponActivityParamDto couponActivityParamDto) {
		log.info("------------couponsQueryDubboService-->queryCouponActivityActiveList(),param:"+ JSONObject.toJSONString(couponActivityParamDto)+"------------");
		Response<PageResult<CouponActivityDto>> response=new Response<PageResult<CouponActivityDto>>();
		PageResult<CouponActivityDto> pageResult=new PageResult<CouponActivityDto>();
		List<CouponActivityDto> data=Lists.newArrayList();
		try {
			//0:验证参数是否为null
			if (null==couponActivityParamDto){
				throw new RuntimeException("请求参数为null");
			}

			//1:校验参数里是否有加盟商id
			List<String> storeIdsOfFranchisee=new ArrayList<String>();
			if (!Strings.isNullOrEmpty(couponActivityParamDto.getFranchiseeId())){
				//查询当前加盟商下门店id集合
				QueryStoreScDto queryStoreScDto=new QueryStoreScDto();
				queryStoreScDto.setFranchiseeId(couponActivityParamDto.getFranchiseeId());
				queryStoreScDto.setPageSize(Integer.MAX_VALUE);
				Response<List<StoreScDto>> listResponse = organizationSCService.queryPageSCStoreInfo(queryStoreScDto);
				if (!listResponse.isSuccess()){
					throw new RuntimeException("根据加盟商id查询门店集合失败,加盟商id:"+couponActivityParamDto.getFranchiseeId());
				}
				if (listResponse.getResultObject()!=null && listResponse.getResultObject().size()>0){
					for (StoreScDto storeScDto:listResponse.getResultObject()){
						storeIdsOfFranchisee.add(storeScDto.getStoreId());
					}
				}
			}

			//2:校验参数里是否有子公司id
			List<String> storeIdsOfBranchConpany=new ArrayList<String>();
			if (!Strings.isNullOrEmpty(couponActivityParamDto.getBranchCompanyId())){
				//查询当前子公司下门店id集合
				Response<Integer> dtoResponse = organizationSCService
						.queryStoreBybranchCompanyId(couponActivityParamDto.getBranchCompanyId());
				if (!dtoResponse.isSuccess()){
					throw new RuntimeException("根据子公司id查询门店集合失败,子公司id:"+couponActivityParamDto.getBranchCompanyId());
				}
				if (dtoResponse.getResultObject()!=null && dtoResponse.getResultObject()>0){
					//主数据根据子公司id查询门店集合需要调用两个服务,先查记录数，再查实际数据
					Response<List<StoreDto>> dtoResponse2 = organizationSCService
							.queryStorePageBybranchCompanyId(couponActivityParamDto.getBranchCompanyId(), 1,
									Integer.MAX_VALUE, dtoResponse.getResultObject());
					if (!dtoResponse2.isSuccess()){
						throw new RuntimeException("根据子公司id查询门店集合失败,子公司id:"+couponActivityParamDto.getBranchCompanyId());
					}
					if (dtoResponse2.getResultObject()!=null && dtoResponse2.getResultObject().size()>0){
						for (StoreDto storeDto:dtoResponse2.getResultObject()){
							storeIdsOfBranchConpany.add(storeDto.getId());
						}
					}
				}
			}

			//3：将盟商id、子公司id做交集筛选,并验证参数里门店id是否在集合中
			List<String> storeIds=new ArrayList<String>();
			//3.1：加盟商id和子公司id都查询出了门店
			if (storeIdsOfBranchConpany !=null && storeIdsOfBranchConpany.size()>0 && storeIdsOfFranchisee!=null && storeIdsOfFranchisee.size()>0){
				storeIdsOfBranchConpany.retainAll(storeIdsOfFranchisee);
				if (Strings.isNullOrEmpty(couponActivityParamDto.getStoreId())){
					//参数里门店id为空
					storeIds.addAll(storeIdsOfBranchConpany);
				}else {
					//参数里门店id不为空，验证门店id是否在门店集合中，不在的话，直接返回，不做查询
					if (!storeIdsOfBranchConpany.contains(couponActivityParamDto.getStoreId())){
						return this.queryCouponActivityActiveListHelper(couponActivityParamDto);
					}
				}
			}else if (storeIdsOfBranchConpany !=null && storeIdsOfBranchConpany.size()>0){
				//只有子公司id查询出了门店
				if (Strings.isNullOrEmpty(couponActivityParamDto.getStoreId())){
					//参数里门店id为空
					storeIds.addAll(storeIdsOfBranchConpany);
				}else {
					//参数里门店id不为空，验证门店id是否在门店集合中，不在的话，直接返回，不做查询
					if (!storeIdsOfBranchConpany.contains(couponActivityParamDto.getStoreId())){
						return this.queryCouponActivityActiveListHelper(couponActivityParamDto);
					}
				}
			}else if (storeIdsOfFranchisee!=null && storeIdsOfFranchisee.size()>0){
				if (Strings.isNullOrEmpty(couponActivityParamDto.getStoreId())){
					//参数里门店id为空
					storeIds.addAll(storeIdsOfFranchisee);
				}else {
					//参数里门店id不为空，验证门店id是否在门店集合中，不在的话，直接返回，不做查询
					if (!storeIdsOfFranchisee.contains(couponActivityParamDto.getStoreId())){
						return this.queryCouponActivityActiveListHelper(couponActivityParamDto);
					}
				}
			}

			//参数中加盟商或者子公司有值，但筛选后门店为空，才不做查询，避免出现未天任何参数，但没做查询
			if (couponActivityParamDto.getFranchiseeId()!=null ||couponActivityParamDto.getBranchCompanyId()!=null){
				if (storeIds==null||storeIds.size()<=0){
					return this.queryCouponActivityActiveListHelper(couponActivityParamDto);
				}
			}
			//4:调用service查询数据
			CouponActivityParamPo couponActivityParamPo=BeanConvertUtils.convert(couponActivityParamDto,CouponActivityParamPo.class);
			couponActivityParamPo.setStoreIds(storeIds);
			couponActivityParamPo.setState((couponActivityParamPo.getQueryType() == 1) ? "active":"canceled");
			PageInfo<CouponActivityPo> pageInfo = couponActivityService
					.queryActivityActiveList(couponActivityParamPo);
			data=BeanConvertUtils.convertList(pageInfo.getList(),CouponActivityDto.class);
			//5：补充需要显示的数据
			if (data!=null && data.size()>0){
				for (CouponActivityDto couponActivityDto:data){
					//5.1:查询优惠券名称
					String promName=promotionService.queryPromotionDetail(couponActivityDto.getPromoId()).getPromotionName();
					couponActivityDto.setPromotionName(promName);
					//5.2:根据门店id查询门店名称、子公司名称、加盟商id、加盟商名称
					Response<StoreDto> storeDtoResponse = organizationService
							.queryStoreById(couponActivityDto.getStoreId());
					log.info("------------couponsQueryDubboService-->queryCouponActivityActiveList(),查询到子公司等信息:"+ JSONObject.toJSONString(storeDtoResponse)+"------------");
					if (storeDtoResponse.getResultObject()!=null){
						couponActivityDto.setBranchCompanyName(storeDtoResponse.getResultObject().getBranchCompanyName());
						couponActivityDto.setFranchinessController(storeDtoResponse.getResultObject().getFranchiseeName());
						couponActivityDto.setStoreName(storeDtoResponse.getResultObject().getName());
						couponActivityDto.setFranchiseeId(storeDtoResponse.getResultObject().getFranchiseeId());
					}
				}
			}
			//6:封装response
			pageResult.setData(data);
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setTotal(pageInfo.getTotal());
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(pageResult);
			response.setSuccess(true);
			return response;
		} catch (Exception e) {
			log.error("------------couponsQueryDubboService-->queryCouponActivityActiveList(),error:"+ e.toString()+"------------");
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			return response;
		}
	}
	*/

	private Response<Double> getDiscountRatio(String userId){
		Response<Double> response = ResponseUtils.RESPONSE_200();
		try{
			Response<LevelPrivilegeDto> privilegeInfo = privilegeInfoDubboService.getPrivilegeInfoByMemberCode(userId, "QY_system_gyl");
			if(privilegeInfo == null || privilegeInfo.getResultObject() == null || CollectionUtils.isEmpty(privilegeInfo.getResultObject().getPrivilegeInfoDtoList())){
				throw new RuntimeException("获取等级权限失败");
			}
			PrivilegeInfoDto privilegeInfoDto = privilegeInfo.getResultObject().getPrivilegeInfoDtoList().get(0);
			double discountNumeric = privilegeInfoDto.getDiscountNumeric();
			response.setResultObject(discountNumeric);
		}catch (Exception e){
			response.setErrorMessage(e.getMessage());
			response.setCode("500");
			response.setSuccess(false);
		}
		return response;
	}

	public Response<PageResult<CouponActivityDto>> queryCouponActivityActiveListHelper(CouponActivityParamDto couponActivityParamDto){
		Response<PageResult<CouponActivityDto>> response=new Response<PageResult<CouponActivityDto>>();
		PageResult<CouponActivityDto> pageResult=new PageResult<CouponActivityDto>();
		pageResult.setPageSize(couponActivityParamDto.getPageSize());
		pageResult.setPageNum(couponActivityParamDto.getPageNum());
		pageResult.setTotal(0L);
		response.setCode(CommonsEnum.RESPONSE_200.getCode());
		response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		response.setResultObject(pageResult);
		response.setSuccess(true);
		return response;
	}

	@Override
	public Response<Double> calcCouponAftPay(Long priceId) {
		Response<Double> response = new Response<Double>();
		try{
			OrderPrice orderPrice = orderPriceService.selectByPrimaryKey(priceId);
			if(orderPrice == null){
				throw new RuntimeException("价格信息为空");
			}
			OrderGiveCouponToStoreLog orderGiveCouponToStoreLog = orderGiveCouponToStoreLogService.queryByPriceInfoId(priceId);
			if(orderGiveCouponToStoreLog == null){
				throw new RuntimeException("未能成功返券，请联系管理人员");
			}
			Double discount = orderGiveCouponToStoreLog.getDiscount();
			Double aDouble = couponsService.calcCouponValue(orderPrice.getAmount(), discount);
			response.setResultObject(aDouble);
			response.setCode("200");
			response.setSuccess(true);
		}catch (Exception e){
			response.setErrorMessage(e.getMessage());
			response.setSuccess(false);
			response.setCode("500");
		}
		return response;
	}

	@Override
	public Response<Double> calcGiveCoupon(Double price, String userId) {
		Response response = ResponseUtils.RESPONSE_200();
		Double aDouble = 0.0d;
		try{
			Response<Double> discountRatio = getDiscountRatio(userId);
			if(discountRatio.isSuccess()){
				aDouble = couponsService.calcCouponValue(price, discountRatio.getResultObject());
			}else {
				response.setCode("500");
				response.setErrorMessage(discountRatio.getErrorMessage());
				response.setSuccess(discountRatio.isSuccess());
			}
		}catch (Exception e){
			response.setCode("500");
			response.setErrorMessage(e.getMessage());
			response.setSuccess(false);
		}
		response.setResultObject(aDouble);
		return response;
	}
}
