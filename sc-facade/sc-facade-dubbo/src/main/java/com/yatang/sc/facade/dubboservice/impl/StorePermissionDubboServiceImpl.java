package com.yatang.sc.facade.dubboservice.impl;
import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import java.util.List;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.domain.QueryStorePermissionPo;
import com.yatang.sc.facade.domain.StorePermissionPo;
import com.yatang.sc.facade.dto.QueryStorePermissionDto;
import com.yatang.sc.facade.dto.StorePermissionDto;
import com.yatang.sc.facade.dubboservice.StorePermissionDubboService;
import com.yatang.sc.facade.service.StorePermissionService;
import org.apache.commons.lang.exception.ExceptionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "storePermissionDubboService")
public class StorePermissionDubboServiceImpl implements StorePermissionDubboService {
    private Logger log = LoggerFactory.getLogger(StorePermissionDubboServiceImpl.class);

    @Autowired
    private StorePermissionService storePermissionService;

    /**
     * 跟据storeId查询白名单
     * @param storeId
     * @return
     */
    @Override
    public Response<StorePermissionDto> queryStorePermissionByStoreId(String storeId) {
        log.info("queryStorePermissionByStoreId:requestParam:{}",storeId);
        Response<StorePermissionDto> response = new Response<StorePermissionDto>();
        try {
            if (storeId == null) {
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                response.setSuccess(false);
                response.setResultObject(null);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                return response;
            }
            StorePermissionPo po = storePermissionService.queryStorePermissionByStoreId(storeId);
            log.info("queryStorePermissionByStoreId:po:{}", JSON.toJSONString(po));
            if (po == null) {
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                response.setSuccess(false);
                response.setResultObject(null);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                return response;
            }
            StorePermissionDto dto = BeanConvertUtils.convert(po, StorePermissionDto.class);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            response.setResultObject(dto);
            log.info("queryStorePermissionByStoreId:response:{}", JSON.toJSONString(response));
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            e.printStackTrace();
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 白名单的分页查询
     * @param queryStorePermissionDto
     * @return
     */
    @Override
    public Response<PageResult<StorePermissionDto>> queryStorePermissionPage(QueryStorePermissionDto queryStorePermissionDto) {
        log.info("queryStorePermissionPage:param:{}", JSON.toJSONString(queryStorePermissionDto));
        Response<PageResult<StorePermissionDto>> response = new Response<PageResult<StorePermissionDto>>();
        try{
            PageResult<StorePermissionDto> storePermissionDtoPageResult = new PageResult<>();
            QueryStorePermissionPo queryStorePermissionPo = BeanConvertUtils.convert(queryStorePermissionDto,QueryStorePermissionPo.class);
            queryStorePermissionPo.setPassNum((queryStorePermissionPo.getPageNum()-1)*queryStorePermissionPo.getPageSize());
            //获取分页查询的集合
            List<StorePermissionPo> storePermissionPos = storePermissionService.queryStorePermissionPage(queryStorePermissionPo);
            List<StorePermissionDto> storePermissionDtos = BeanConvertUtils.convertList(storePermissionPos,StorePermissionDto.class);
            log.info("queryStorePermissionPage:storePermissionDtos:{}", JSON.toJSONString(storePermissionDtos));
            //获取分页查询的总条数
            Long total = storePermissionService.queryStorePermissionTotal(queryStorePermissionPo);
            log.info("queryStorePermissionPage:total:{}", JSON.toJSONString(total));
            storePermissionDtoPageResult.setData(storePermissionDtos);
            storePermissionDtoPageResult.setPageNum(queryStorePermissionDto.getPageNum());
            storePermissionDtoPageResult.setPageSize(queryStorePermissionDto.getPageSize());
            storePermissionDtoPageResult.setTotal(total);
            log.info("queryStorePermissionPage:storePermissionDtoPageResult:{}", JSON.toJSONString(storePermissionDtoPageResult));
            response.setResultObject(storePermissionDtoPageResult);
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        }catch (Exception e){
            response.setResultObject(null);
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            e.printStackTrace();
        }
        return  response;
    }

    /**
     * 新增白名单
     * @param storePermissionDto
     * @return
     */
    @Override
    public Response<Boolean> insertStorePermission(StorePermissionDto storePermissionDto) {
        log.info("insertStorePermission:param:{}", JSON.toJSONString(storePermissionDto));
        Response<Boolean> response = new Response<>();
        try{
            StorePermissionPo storePermissionPo = BeanConvertUtils.convert(storePermissionDto,StorePermissionPo.class);
            //根据storeId判断一下看这个新增的记录是否存在
            StorePermissionPo po = storePermissionService.queryStorePermissionByStoreId(storePermissionPo.getStoreId());
            log.info("insertStorePermission:po:{}", JSON.toJSONString(po));
            if (po != null) {
                response.setCode(CommonsEnum.RESPONSE_10046.getCode());
                response.setSuccess(false);
                response.setResultObject(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10046.getName());
                return response;
            }
            boolean success = storePermissionService.insertSelective(storePermissionPo);
            if (!success){
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setSuccess(false);
                response.setResultObject(false);
                response.setErrorMessage("新增失败");
                return response;
            }
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(success);
            response.setResultObject(success);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        }catch (Exception e){
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setResultObject(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getCode());
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 修改白名单之回显
     * @param id
     * @return
     */
    @Override
    public Response<StorePermissionDto> updateStorePermission(Integer id) {
        log.info("updateStorePermission:id:{}", JSON.toJSONString(id));
        Response<StorePermissionDto> storePermissionDtoResponse = new Response<StorePermissionDto>();
        try {
            StorePermissionPo storePermissionPo = storePermissionService.selectByPrimaryKey(id);
            log.info("updateStorePermission:storePermissionPo:{}", JSON.toJSONString(storePermissionPo));
            StorePermissionDto storePermissionDto = BeanConvertUtils.convert(storePermissionPo,StorePermissionDto.class);
            storePermissionDtoResponse.setResultObject(storePermissionDto);
            storePermissionDtoResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            storePermissionDtoResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
            storePermissionDtoResponse.setSuccess(true);
        }catch (Exception e){
            storePermissionDtoResponse.setResultObject(null);
            storePermissionDtoResponse.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            storePermissionDtoResponse.setCode(CommonsEnum.RESPONSE_500.getCode());
            storePermissionDtoResponse.setSuccess(false);
            e.printStackTrace();
        }
        return storePermissionDtoResponse;
    }

    /**
     * 具体执行修改白名单
     * @param storePermissionDto
     * @return
     */
    @Override
    public Response<Boolean> updateStorePermission(StorePermissionDto storePermissionDto) {
        log.info("updateStorePermission:storePermissionDto:{}", JSON.toJSONString(storePermissionDto));
        Response<Boolean> res = new Response<Boolean>();
        try{
            StorePermissionPo storePermissionPo = BeanConvertUtils.convert(storePermissionDto,StorePermissionPo.class);
            //修改的时候也需要判断下看修改的白名单在数据库里是否存在
            StorePermissionPo storePermissionPoInDataBase = storePermissionService.queryStorePermissionByStoreIdAndId(storePermissionPo);
            if (storePermissionPoInDataBase != null){
                res.setCode(CommonsEnum.RESPONSE_10046.getCode());
                res.setSuccess(false);
                res.setResultObject(false);
                res.setErrorMessage(CommonsEnum.RESPONSE_10046.getName());
                return res;
            }
            storePermissionService.updateByPrimaryKeySelective(storePermissionPo);
            res.setCode(CommonsEnum.RESPONSE_200.getCode());
            res.setSuccess(true);
            res.setResultObject(true);
            res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        }catch (Exception e){
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setResultObject(false);
            res.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 删除白名单
     * @param id
     * @return
     */
    @Override
    public Response<Boolean> deleteStorePermission(Integer id) {
        log.info("updateStorePermission:id:{}", JSON.toJSONString(id));
        Response<Boolean> res = new Response<Boolean>();
        try{
            storePermissionService.deleteByPrimaryKey(id);
            res.setCode(CommonsEnum.RESPONSE_200.getCode());
            res.setSuccess(true);
            res.setResultObject(true);
            res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        }catch (Exception e){
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setResultObject(false);
            res.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            e.printStackTrace();
        }
        return res;
    }


}
