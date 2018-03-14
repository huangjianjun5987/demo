package com.yatang.sc.app.action;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.app.vo.ZiptoftpInfoResultVo;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.dto.ZiptoftpInfoDto;
import com.yatang.sc.facade.dubboservice.ZiptoftpInfoDubboService;

import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/sc/commonUploadFile")
public class UploadZipAction {
	private static final Logger				log	= LoggerFactory.getLogger(UploadZipAction.class);
	private final ZiptoftpInfoDubboService	ziptoftpInfoDubboService;
	@RequestMapping(value = "/selectPath", method = RequestMethod.GET)
	public Response<ZiptoftpInfoResultVo> selectPath(@Valid @NotBlank Integer id) {
		Response<ZiptoftpInfoResultVo> response = new Response<>();
		log.info("/sc/commonUploadFile/selectPath收到请求，查询ID：{}", id);
		Response<ZiptoftpInfoDto> result = ziptoftpInfoDubboService.selectByPrimaryKey(id);
		if ("500".equals(result.getCode())) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			return response;
		}
		ZiptoftpInfoResultVo ziptoftpInfoResultVo = BeanConvertUtils.convert(result.getResultObject(),
				ZiptoftpInfoResultVo.class);
		response.setSuccess(true);
		response.setCode(CommonsEnum.RESPONSE_200.getCode());
		response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		response.setResultObject(ziptoftpInfoResultVo);
		return response;
	}
}
