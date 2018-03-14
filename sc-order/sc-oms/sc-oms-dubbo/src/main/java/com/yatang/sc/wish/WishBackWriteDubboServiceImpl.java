package com.yatang.sc.wish;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.busi.common.resp.Response;
import com.busi.mq.producer.SimpleMQProducer;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.dto.WishMqDto;
import com.yatang.sc.order.domain.WishListPo;
import com.yatang.sc.order.service.WishService;
import com.yatang.sc.wish.dubboService.WishBackWriteDubboService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description: 心愿单 后端write接口实现类
 * @author: yinyuxin
 * @date: 2018/1/3 15:56
 * @version: v1.0
 */
@Service("wishBackWriteDubboService")
public class WishBackWriteDubboServiceImpl implements WishBackWriteDubboService{

	@Autowired
	private WishService wishService;

	@Autowired
	private ProductScIndexDubboService productScIndexDubboService;

	@Resource(name = "wishMqProducer")
	private SimpleMQProducer wishMqProducer;

	private static final String WISHSUCCESS="xcr://100/reservation?key=inStock";

	private static final String WISHFAILD="xcr://100/reservation?key=reservations";

	private static final Logger LOGGER = LoggerFactory.getLogger(WishBackQueryDubboServiceImpl.class);



	@Override
	public Response<Boolean> comleteOrCloseWishList(Long wishListId, String status) {
		LOGGER.info("wishWriteDubboService-->comleteOrCloseWishList()-->param:wishListId="+wishListId+",status="+status);
		Response<Boolean> response=new Response<Boolean>();
		if (null==wishListId||0L==wishListId||!("complete".equals(status)||"close".equals(status))){
			throw new RuntimeException("参数有误，请检查");
		}
		try {
			//0:检验当前心愿单下的门店集合
			List<String> storeIds=wishService.queryWishDetailIdsByWishListId(wishListId);
			if (null==storeIds||storeIds.size()<=0){
				throw new RuntimeException("当前预订单下无商户记录,操作终止");
			}
			//1：查询心愿单详情，并查询商品id
			WishListPo wishListPo = wishService.queryWishListById(wishListId);
			if (null==wishListPo||null==wishListPo.getGbCode()||"".equals(wishListPo.getGbCode())){
				throw new RuntimeException("心愿单里无商品数据,操作终止,心愿单id:"+wishListId);
			}
			//2: 向小超B端发送mq
			WishMqDto wishMqDto=new WishMqDto();
			wishMqDto.setStatus(true);
			wishMqDto.setStoreIds(storeIds);
			if ("complete".equals(status)){
				Response<Map<String, ProductIndexDto>> productResponse = productScIndexDubboService
						.queryByInternationalCodes(Lists.newArrayList(wishListPo.getGbCode()));
				LOGGER.info("wishWriteDubboService-->comleteOrCloseWishList()-->查商品数据:{}",JSONObject.toJSONString(productResponse));
				if (!productResponse.isSuccess()||null==productResponse.getResultObject()
						||null==productResponse.getResultObject().get(wishListPo.getGbCode())){
					throw new RuntimeException("根据预订单中商品国际码未查到商品记录,操作终止");
				}
				wishMqDto.setProductDetailUrl(WISHSUCCESS);
				wishMqDto.setTitle("到货通知");
				wishMqDto.setContent("您预订的["+productResponse.getResultObject().get(wishListPo.getGbCode()).getSaleName()+"...]到货啦！点击购买！");
			}else {
				wishMqDto.setProductDetailUrl(WISHFAILD);
				wishMqDto.setTitle("缺货通知");
				wishMqDto.setContent("您预订的商品条码["+wishListPo.getGbCode()+"]暂时缺货!");
			}
			wishMqDto.setStoreIds(storeIds);
			wishMqDto.setStatus("complete".equals(status)?true:false);
			String mqBody= JSONObject.toJSONString(wishMqDto);
			LOGGER.info("wishWriteDubboService-->comleteOrCloseWishList()-->mq(param):"+mqBody);
			SendResult sendResult = wishMqProducer.sendMsg(wishMqDto);
			LOGGER.info("wishWriteDubboService-->comleteOrCloseWishList()-->mq(result):"+JSONObject.toJSONString(sendResult));
			//3：修改wish_list表状态
			wishService.modifyWishListStatus(wishListId,status);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
			response.setResultObject(true);
			return response;
		} catch (RuntimeException e) {
			LOGGER.info("wishWriteDubboService-->comleteOrCloseWishList()-->error:"+ ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(e.getMessage());
			response.setSuccess(false);
			response.setResultObject(false);
			return response;
		}
	}
}
