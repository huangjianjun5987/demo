package com.yatang.sc.operation.web;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.DictionaryContentDto;
import com.yatang.sc.facade.dto.DictionaryDto;
import com.yatang.sc.facade.dubboservice.DictionaryQueryDubboService;
import com.yatang.sc.facade.dubboservice.DictionaryWriteDubboService;
import com.yatang.sc.operation.vo.DictionaryContentForSelectVo;
import com.yatang.sc.operation.vo.DictionaryContentVo;
import com.yatang.sc.operation.vo.DictionaryQueryVo;
import com.yatang.sc.operation.vo.DictionaryVo;
import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.sc.web.paramvalid.ParamValid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.yatang.sc.facade.enums.PublicEnum.DEFAULT_PAGE_SIZE;

/**
 * @描述:数据字典控制层
 * @类名:DictionaryAtion
 * @作者: kangdong
 * @创建时间: 2017/6/8 10:11
 * @版本: v1.0
 */
@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
@RestController
@RequestMapping(value = "/sc/dictionary")
public class DictionaryAtion extends BaseAction {

	private final DictionaryQueryDubboService dictionaryQueryDubboService;

	private final DictionaryWriteDubboService dictionaryWriteDubboService;



	/**
	 * @Description:字典列表
	 * @author kangdong
	 * @date 2017年6月8日 下午11:24:30
	 * @param dictionaryVo
	 * @return
	 */
	@RequestMapping(value = "dictionaryList", method = { RequestMethod.GET })
	public Response<PageResult<DictionaryDto>> dictionaryList(DictionaryQueryVo dictionaryVo) {
		log.info("/sc/dictionary/dictionaryList->dictionaryVo" + dictionaryVo);
		int pageNum = dictionaryVo.getPageNum() != null ? dictionaryVo.getPageNum() : 1;
		int pageSize = dictionaryVo.getPageSize() != null ? dictionaryVo.getPageSize()
				: Integer.valueOf(DEFAULT_PAGE_SIZE.getCodeStr());
		String keyword = dictionaryVo.getKeyword() != null ? dictionaryVo.getKeyword() : "";
		Response<PageResult<DictionaryDto>> dictionary = dictionaryQueryDubboService.findByPage(keyword, pageNum,
				pageSize);
		return dictionary;
	}



	/**
	 * @Description:新增字典
	 * @param dictionaryVo
	 * @return
	 */
	@RequestMapping(value = "/insertDictionary", method = RequestMethod.POST)
	public Response<Boolean> insertDictionaryInfo(@Validated({ GroupOne.class }) @RequestBody DictionaryVo dictionaryVo) {
		log.info("/sc/dictionary/insertDictionary->dictionaryVo:" + dictionaryVo);
		DictionaryDto dictionaryDto = BeanConvertUtils.convert(dictionaryVo, DictionaryDto.class);
		return dictionaryWriteDubboService.insertDictionaryInfo(dictionaryDto);
	}



	/**
	 * 查询显示数据字典
	 * 
	 * @param id
	 * @return
	 */
	@ParamValid
	@RequestMapping(value = "/queryDictionaryDetail", method = RequestMethod.GET)
	public Response<DictionaryDto> queryDictionaryDetail(@RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) Integer id) {
		log.info("/sc/dictionary/deleteDictionary?Id=" + id);
		return dictionaryQueryDubboService.queryById(id);
	}



	/**
	 * @Description:修改数据字典
	 * @param dictionaryVo
	 * @return
	 */
	@RequestMapping(value = "/updateDictionary", method = RequestMethod.POST)
	public Response<Boolean> updateDictionaryInfo(@Validated({ GroupTwo.class }) @RequestBody DictionaryVo dictionaryVo) {
		log.info("/sc/dictionary/updateDictionary->dictionaryVo:" + dictionaryVo);
		DictionaryDto dictionaryDto = BeanConvertUtils.convert(dictionaryVo, DictionaryDto.class);
		return dictionaryWriteDubboService.updateDictionary(dictionaryDto);
	}



	/**
	 * @Description:删除数据字典
	 * @param id
	 * @return
	 */
	@ParamValid
	@RequestMapping(value = "/deleteDictionary", method = RequestMethod.GET)
	public Response<Boolean> deleteDictionary(@RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) Integer id) {
		log.info("/sc/dictionary/deleteDictionary?id=" + id);
		return dictionaryWriteDubboService.deleteDictionaryInfo(id);
	}



	/**
	 * 根据条件检测数据字典是否存在
	 * 
	 * @param dictionary,code
	 * @return
	 */
	@ParamValid
	@RequestMapping(value = "/checkDictionary", method = RequestMethod.GET)
	public Response<Boolean> checkDictionary(@RequestParam @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String dictionary, String code) {
		DictionaryDto dictionaryDto = new DictionaryDto();
		dictionaryDto.setDictionary(dictionary == null ? null : dictionary);
		dictionaryDto.setCode(code == null ? null : code);
		return dictionaryWriteDubboService.checkDictionaryInfo(dictionaryDto);
	}



	/**
	 * 根据条件检测数据字典是否存在
	 * 
	 * @param dictionaryContentVo
	 * @return
	 */
	@RequestMapping(value = "/checkDictionaryContent", method = RequestMethod.POST)
	public Response<Boolean> checkDictionaryContent(@RequestBody DictionaryContentVo dictionaryContentVo) {
		log.info("/sc/dictionary/checkDictionaryContent->dictionaryContentVo:" + dictionaryContentVo);
		DictionaryContentDto contentDto = BeanConvertUtils.convert(dictionaryContentVo, DictionaryContentDto.class);
		return dictionaryWriteDubboService.checkDictionaryContentInfo(contentDto);
	}



	// ===========================================华丽的分割线==========================================
	// ------------------------------------------数据字典内容维护---------------------------------------

	/**
	 * 查询显示字典维护内容列表
	 * 
	 * @param dictionaryId
	 * @return
	 */
	@ParamValid
	@RequestMapping(value = "/dictionaryContentList", method = { RequestMethod.GET })
	public Response<List<DictionaryContentDto>> queryDictionaryContent(@RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) Integer dictionaryId) {
		log.info("/sc/dictionary/dictionaryContentList?dictionaryId=" + dictionaryId);
		return dictionaryQueryDubboService.queryDictionaryContentList(dictionaryId);
	}



	/**
	 * 新增字典内容
	 * 
	 * @param dictionaryContentVo
	 * @return
	 */
	@RequestMapping(value = "/insertDictionaryContent", method = RequestMethod.POST)
	public Response<Boolean> insertDictionaryContent(@Validated({ GroupOne.class }) @RequestBody DictionaryContentVo dictionaryContentVo) {
		log.info("/sc/dictionary/insertDictionaryContent->dictionaryContentVo:" + dictionaryContentVo);
		DictionaryContentDto contentDto = BeanConvertUtils.convert(dictionaryContentVo, DictionaryContentDto.class);
		return dictionaryWriteDubboService.insertDictionaryContentInfo(contentDto);
	}



	/**
	 * @Description:修改字典内容或设置状态
	 * @param dictionaryContentVo
	 * @return
	 */
	@RequestMapping(value = "/updateContent", method = RequestMethod.POST)
	public Response<Boolean> updateContent(@Validated({ GroupTwo.class }) @RequestBody DictionaryContentVo dictionaryContentVo) {
		log.info("/sc/dictionary/updateContent->dictionaryContentVo:" + dictionaryContentVo);
		DictionaryContentDto contentDto = BeanConvertUtils.convert(dictionaryContentVo, DictionaryContentDto.class);
		return dictionaryWriteDubboService.updateContentInfo(contentDto);
	}

	/**
	 * @Description:设置状态
	 * @param dictionaryContentVo
	 * @return
	 */
	@RequestMapping(value = "/updateState", method = RequestMethod.POST)
	public Response<Boolean> updateState(@Validated({ DefaultGroup.class }) @RequestBody DictionaryContentVo dictionaryContentVo) {
		log.info("/sc/dictionary/updateState->dictionaryContentVo:" + dictionaryContentVo);
		DictionaryContentDto contentDto = BeanConvertUtils.convert(dictionaryContentVo, DictionaryContentDto.class);
		return dictionaryWriteDubboService.updateState(contentDto);
	}



	/**
	 * 
	 * 查询系统级字典下拉框值列表（尹玉新）
	 *
	 * @param dictionaryName
	 *            字典名称
	 * @return
	 */
	@RequestMapping(value = "querySelectValues", method = RequestMethod.GET)
	public Response<List<DictionaryContentForSelectVo>> querySelectValues(String dictionaryCode) {
		Response<List<DictionaryContentForSelectVo>> voResponse = new Response<List<DictionaryContentForSelectVo>>();
		List<DictionaryContentForSelectVo> list = new ArrayList<DictionaryContentForSelectVo>();
		Response<List<DictionaryContentDto>> dtoResponse = dictionaryQueryDubboService
				.queryDictionContentForSelect(dictionaryCode);
		if (dtoResponse.getResultObject() != null && dtoResponse.getResultObject().size() > 0) {
			list = BeanConvertUtils.convertList(dtoResponse.getResultObject(), DictionaryContentForSelectVo.class);
		}
		voResponse.setCode(dtoResponse.getCode());
		voResponse.setErrorMessage(dtoResponse.getErrorMessage());
		voResponse.setResultObject(list);
		voResponse.setSuccess(dtoResponse.isSuccess());
		return voResponse;
	}
}
