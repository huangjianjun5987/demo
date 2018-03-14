package com.yatang.sc.operation.web;

import com.busi.common.resp.Response;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.dto.DepositRecordDto;
import com.yatang.sc.facade.dubboservice.DepositQueryDubboService;
import com.yatang.sc.facade.dubboservice.DepositWriteDubboService;
import com.yatang.sc.facade.enums.PublicEnum;
import com.yatang.sc.operation.vo.DepositRecordExcelModel;
import com.yatang.sc.operation.vo.DepositRecordExcelView;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @描述: 保证金 HTTP API
 * @作者: yipeng
 * @创建时间: 2017年05月23日12:56:45
 * @版本: 1.0 .
 */
@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping(value = "/sc/deposit")
public class DepositAction extends BaseAction {
  private final DepositWriteDubboService depositWriteDubboService;
  private final DepositQueryDubboService depositQueryDubboService;

    @RequestMapping(value = "depositRecords", method = RequestMethod.GET)
  public Response<PageInfo<DepositRecordDto>> depositRecords(@RequestParam Map<String, Object> dto) {
    log.info("dto:" + dto);
    Response<PageInfo<DepositRecordDto>> depositRecords = depositQueryDubboService.depositRecords(dto);
    return depositRecords;
  }

  @RequestMapping(value = "excelDepositRecords", method = RequestMethod.GET)
  public ModelAndView excelDepositRecords(@RequestParam Map<String, Object> dto) {
    log.info("dto:" + dto);

    dto.put(PublicEnum.MAP_PAGE_NUM.getCodeStr(), 1);
    dto.put(PublicEnum.MAP_PAGE_SIZE.getCodeStr(), Integer.MAX_VALUE);

    Response<PageInfo<DepositRecordDto>> depositRecords = depositQueryDubboService.depositRecords(dto);

    List<DepositRecordExcelModel> models = DepositRecordExcelModel.of(depositRecords.getResultObject().getList());
    DepositRecordExcelView excelView = new DepositRecordExcelView(models);

    //如果导出文件名不正确，请用其他浏览器试试
    return new ModelAndView(excelView);
  }

}
