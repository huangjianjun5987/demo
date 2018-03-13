package com.yatang.sc.facade.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.base.Throwables;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.CarouselAdPo;
import com.yatang.sc.facade.domain.CarouselParamPo;
import com.yatang.sc.facade.domain.EditQuickNavigationStatusPo;
import com.yatang.sc.facade.domain.HomeAreaAdPo;
import com.yatang.sc.facade.domain.HomeItemAdPo;
import com.yatang.sc.facade.domain.QuickNavigationPo;
import com.yatang.sc.facade.dto.CarouselAdDto;
import com.yatang.sc.facade.dto.CarouselParamDto;
import com.yatang.sc.facade.dto.EditQuickNavigationStatusDto;
import com.yatang.sc.facade.dto.HomeAreaAdDto;
import com.yatang.sc.facade.dto.HomeItemAdDto;
import com.yatang.sc.facade.dto.QuickNavigationDto;
import com.yatang.sc.facade.dubboservice.HomeAdWriteDubboService;
import com.yatang.sc.facade.service.HomeAdService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("homeAdWriteDubboService")
public class HomeAdWriteDubboServiceImpl implements HomeAdWriteDubboService {

  protected final Log log	= LogFactory.getLog(this.getClass());

  @Autowired
  private HomeAdService service;

  @Override
  public Response<Boolean> moveArea(String areaId, Boolean isUp) {
    if (log.isInfoEnabled()) {
      log.info("---------- <<移动区域>> moveArea(String areaId, Boolean isUp, String companyId): areaId="
              + areaId + "isUp=" + isUp + "----------");
    }
    Response<Boolean> response = new Response<Boolean>();
    try {
      Boolean flag = service.moveArea(areaId, isUp);
      if (flag) {
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
      } else {
        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
      }
      response.setSuccess(flag);
      response.setResultObject(null);

    } catch (Exception e) {
      log.error(ExceptionUtils.getFullStackTrace(e));
      response.setCode(CommonsEnum.RESPONSE_500.getCode());
      response.setSuccess(false);
      response.setErrorMessage(Throwables.getRootCause(e).getMessage());
    }
    return response;
  }

  @Override
  public Response<Boolean> setAreaEnable(String areaId, Boolean isEnabled) {
    if (log.isInfoEnabled()) {
      log.info("---------- <<设置区域是否可用>> setAreaEnable(String areaId, Boolean isEnabled): areaId="
              + areaId + "isEnabled=" + isEnabled + "----------");
    }
    Response<Boolean> response = new Response<Boolean>();
    try {
      Boolean flag = service.setAreaEnable(areaId, isEnabled);
      if (flag) {
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
      } else {
        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
      }
      response.setSuccess(flag);
      response.setResultObject(null);

    } catch (Exception e) {
      log.error(ExceptionUtils.getFullStackTrace(e));
      response.setCode(CommonsEnum.RESPONSE_500.getCode());
      response.setSuccess(false);
      response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
    }
    return response;
  }

  @Override
  public Response<Boolean> saveArea(HomeAreaAdDto dto) {
    if (log.isInfoEnabled()) {
      log.info("---------- <<保存区域>> saveArea(HomeAreaAdDto dto): dto="
              + dto + "----------");
    }
    Response<Boolean> response = new Response<Boolean>();
    try {
      HomeAreaAdPo po = BeanConvertUtils.convert(dto, HomeAreaAdPo.class);
      Boolean flag = service.saveArea(po);
      if (flag) {
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
      } else {
        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
      }
      response.setSuccess(flag);
      response.setResultObject(null);

    } catch (Exception e) {
      log.error(ExceptionUtils.getFullStackTrace(e));
      response.setCode(CommonsEnum.RESPONSE_500.getCode());
      response.setSuccess(false);
      response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
    }
    return response;
  }

  @Override
  public Response<Boolean> saveItemAd(HomeItemAdDto dto) {
    if (log.isInfoEnabled()) {
      log.info("---------- <<保存区域Item>> saveItemAd(HomeItemAdDto dto): dto="
              + dto + "----------");
    }
    Response<Boolean> response = new Response<Boolean>();
    try {
      HomeItemAdPo po = BeanConvertUtils.convert(dto, HomeItemAdPo.class);
      Boolean flag = service.saveItemAd(po);
      if (flag) {
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
      } else {
        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
      }
      response.setSuccess(flag);
      response.setResultObject(null);

    } catch (Exception e) {
      log.error(ExceptionUtils.getFullStackTrace(e));
      response.setCode(CommonsEnum.RESPONSE_500.getCode());
      response.setSuccess(false);
      response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
    }
    return response;
  }

  @Override
  public Response<Boolean> deleteArea(String areaId) {
    if (log.isInfoEnabled()) {
      log.info("---------- <<删除区域>> deleteArea(String areaId): areaId="
              + areaId + "----------");
    }
    Response<Boolean> response = new Response<Boolean>();
    try {
      Boolean flag = service.deleteArea(areaId);
      if (flag) {
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
      } else {
        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
      }
      response.setSuccess(flag);
      response.setResultObject(null);

    } catch (Exception e) {
      log.error(ExceptionUtils.getFullStackTrace(e));
      response.setCode(CommonsEnum.RESPONSE_500.getCode());
      response.setSuccess(false);
      response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
    }
    return response;
  }

  @Override
  public Response<Boolean> deleteItemAd(String itemAdId) {
    if (log.isInfoEnabled()) {
      log.info("---------- <<删除区域item>> deleteItemAd(String itemAdId): itemAdId="
              + itemAdId + "----------");
    }
    Response<Boolean> response = new Response<Boolean>();
    try {
      Boolean flag = service.deleteItemAd(itemAdId);
      if (flag) {
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
      } else {
        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
      }
      response.setSuccess(flag);
      response.setResultObject(null);

    } catch (Exception e) {
      log.error(ExceptionUtils.getFullStackTrace(e));
      response.setCode(CommonsEnum.RESPONSE_500.getCode());
      response.setSuccess(false);
      response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
    }
    return response;
  }

  @Override
  public Response<Boolean> updateCertainQuickNavigation(QuickNavigationDto record) {
    if (log.isInfoEnabled()) {
      log.info("---------- <<修改快捷导航(传特定参数)>> updateCertainQuickNavigation(QuickNavigationDto record): record="
              + JSON.toJSONString(record) + "----------");
    }
    Response<Boolean> response = new Response<Boolean>();
    try {
      QuickNavigationPo po = BeanConvertUtils.convert(record, QuickNavigationPo.class);
      Boolean flag = service.updateCertainQuickNavigation(po);
              if (flag) {
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
              } else {
                response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
              }
      response.setSuccess(flag);
      response.setResultObject(null);

    } catch (Exception e) {
      log.error(ExceptionUtils.getFullStackTrace(e));
      response.setCode(CommonsEnum.RESPONSE_500.getCode());
      response.setSuccess(false);
      response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
    }
    return response;
  }

  @Override
  public Response<Boolean> batchUpdateQuickNavigation(EditQuickNavigationStatusDto record, String userId) {
    if (log.isInfoEnabled()) {
      log.info("---------- <<批量冻结/启用快捷导航>> batchUpdateQuickNavigation(EditQuickNavigationStatusDto record, String userId): record="
              + JSON.toJSONString(record) + "; userId=" + userId + "----------");
    }
    Response<Boolean> response = new Response<Boolean>();
    try {
      EditQuickNavigationStatusPo po = BeanConvertUtils.convert(record, EditQuickNavigationStatusPo.class);
      Boolean flag = service.batchUpdateQuickNavigation(po, userId);
      if (flag) {
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
      } else {
        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
      }
      response.setSuccess(flag);
      response.setResultObject(null);

    } catch (Exception e) {
      log.error(ExceptionUtils.getFullStackTrace(e));
      response.setCode(CommonsEnum.RESPONSE_500.getCode());
      response.setSuccess(false);
      response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
    }
    return response;
  }

  @Override
  public Response<Boolean> updateCarouselIntervalById(CarouselParamDto paramDto) {
    if (log.isInfoEnabled()) {
      log.info("---------- <<修改轮播间隔时间>> updateCarouselIntervalById(CarouselParamDto paramDto): paramDto="
              + JSON.toJSONString(paramDto) + "----------");
    }
    Response<Boolean> response = new Response<Boolean>();
    try {
      Boolean flag = service.updateCarouselIntervalById(BeanConvertUtils.convert(paramDto, CarouselParamPo.class));
      if (flag) {
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
      } else {
        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
      }
      response.setSuccess(flag);
      response.setResultObject(null);

    } catch (Exception e) {
      log.error(ExceptionUtils.getFullStackTrace(e));
      response.setCode(CommonsEnum.RESPONSE_500.getCode());
      response.setSuccess(false);
      response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
    }
    return response;
  }

  @Override
  public Response<Boolean> deleteCarouselAd(Integer id) {
    if (log.isInfoEnabled()) {
      log.info("---------- <<根据id删除轮播广告>> deleteCarouselAd(Integer id): id=" + id + "----------");
    }
    Response<Boolean> response = new Response<Boolean>();
    try {
      Boolean flag = service.deleteCarouselAd(id);
      if (flag) {
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
      } else {
        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
      }
      response.setSuccess(flag);
      response.setResultObject(null);

    } catch (Exception e) {
      log.error(ExceptionUtils.getFullStackTrace(e));
      response.setCode(CommonsEnum.RESPONSE_500.getCode());
      response.setSuccess(false);
      response.setErrorMessage(Throwables.getRootCause(e).getMessage());
    }
    return response;
  }

  @Override
  public Response<Boolean> insertCarouselAd(CarouselAdDto record) {
    if (log.isInfoEnabled()) {
      log.info("---------- <<新增轮播广告>> insertCarouselAd(CarouselAdDto record): record="
              + JSON.toJSONString(record) + "----------");
    }
    Response<Boolean> response = new Response<Boolean>();
    try {
      CarouselAdPo po = BeanConvertUtils.convert(record, CarouselAdPo.class);
      Boolean flag = service.insertCarouselAd(po);
      if (flag) {
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
      } else {
        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
      }
      response.setSuccess(flag);
      response.setResultObject(null);

    } catch (Exception e) {
      log.error(ExceptionUtils.getFullStackTrace(e));
      response.setCode(CommonsEnum.RESPONSE_500.getCode());
      response.setSuccess(false);
      response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
    }
    return response;
  }

  @Override
  public Response<Boolean> updateCarouselAd(CarouselAdDto record) {
    if (log.isInfoEnabled()) {
      log.info("---------- <<停用启用轮播广告>> updateCarouselAd(CarouselAdDto record): record="
              + JSON.toJSONString(record) + "----------");
    }
    Response<Boolean> response = new Response<Boolean>();
    try {
      CarouselAdPo po = BeanConvertUtils.convert(record, CarouselAdPo.class);
      Boolean flag =  service.updateCarouselAd(po);
      if (flag) {
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
      } else {
        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
      }
      response.setSuccess(flag);
      response.setResultObject(null);

    } catch (Exception e) {
      log.error(ExceptionUtils.getFullStackTrace(e));
      response.setCode(CommonsEnum.RESPONSE_500.getCode());
      response.setSuccess(false);
      response.setErrorMessage(Throwables.getRootCause(e).getMessage());
    }
    return response;
  }

  @Override
  public Response<Boolean> updateCertainCarouselAd(CarouselAdDto record) {
    if (log.isInfoEnabled()) {
      log.info("---------- <<修改单个轮播广告>> updateCertainCarouselAd(CarouselAdDto record): record="
              + JSON.toJSONString(record)  + "----------");
    }
    Response<Boolean> response = new Response<Boolean>();
    try {
      CarouselAdPo po = BeanConvertUtils.convert(record, CarouselAdPo.class);
      Boolean flag =  service.updateCertainCarouselAd(po);
      if (flag) {
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
      } else {
        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
      }
      response.setSuccess(flag);
      response.setResultObject(null);

    } catch (Exception e) {
      log.error(ExceptionUtils.getFullStackTrace(e));
      response.setCode(CommonsEnum.RESPONSE_500.getCode());
      response.setSuccess(false);
      response.setErrorMessage(Throwables.getRootCause(e).getMessage());
    }
    return response;
  }

  @Override
  public Response<Boolean> switchOptWayOfHome(HomeAreaAdDto dto) {
    if (log.isInfoEnabled()) {
      log.info("---------- <<切换首页运营方式>> switchOptWayOfHome(HomeAreaAdDto dto): dto="
              + JSON.toJSONString(dto)  + "----------");
    }
    Response<Boolean> response = new Response<Boolean>();
    try {
      HomeAreaAdPo po = BeanConvertUtils.convert(dto, HomeAreaAdPo.class);
      Boolean flag =  service.switchOptWayOfHome(po);
      if (flag) {
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
      } else {
        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
      }
      response.setSuccess(flag);
      response.setResultObject(null);

    } catch (Exception e) {
      log.error(ExceptionUtils.getFullStackTrace(e));
      response.setCode(CommonsEnum.RESPONSE_500.getCode());
      response.setSuccess(false);
      response.setErrorMessage(Throwables.getRootCause(e).getMessage());
    }
    return response;
  }

  @Override
  public Response<Boolean> switchOptWayOfCarousel(HomeAreaAdDto dto) {
    if (log.isInfoEnabled()) {
      log.info("---------- <<切换轮转运营方式>> switchOptWayOfCarousel(HomeAreaAdDto dto): dto="
              + JSON.toJSONString(dto)  + "----------");
    }
    Response<Boolean> response = new Response<Boolean>();
    try {
      HomeAreaAdPo po = BeanConvertUtils.convert(dto, HomeAreaAdPo.class);
      Boolean flag =  service.switchOptWayOfCarousel(po);
      if (flag) {
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
      } else {
        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
      }
      response.setSuccess(flag);
      response.setResultObject(null);

    } catch (Exception e) {
      log.error(ExceptionUtils.getFullStackTrace(e));
      response.setCode(CommonsEnum.RESPONSE_500.getCode());
      response.setSuccess(false);
      response.setErrorMessage(Throwables.getRootCause(e).getMessage());
    }
    return response;
  }
}
