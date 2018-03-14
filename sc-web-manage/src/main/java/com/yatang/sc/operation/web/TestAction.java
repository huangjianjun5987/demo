package com.yatang.sc.operation.web;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.enums.AdType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @描述: 测试控制器
 * @作者: yipeng
 * @创建时间: 2017年07月04日16:32:31
 * @版本: 1.0 .
 */
@Slf4j
@RestController
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/sc/test")
public class TestAction extends BaseAction {

  @Data
  public static class EmptyDto {
    private String[] array;
    private List<String> list;
    private Set<String> set;
    private String string;
    private Integer integer;
    private AdType adType;
    private Map<String, Object> map;
  }

  @RequestMapping("/exception")
  public void exception() {
    log.info("异常测试");
    throw new RuntimeException("我是一个自定义异常");
  }

  @RequestMapping("/nullMapDto")
  public Response<Map<String, Object>> nullMapDto() {
    Response<Map<String, Object>> response = new Response<>();
    response.setResultObject(null);
    return response;
  }

    @RequestMapping("/nullDto")
  public Response<EmptyDto> nullDto() {
    Response<EmptyDto> response = new Response<>();
    response.setResultObject(null);
    return response;
  }

  @RequestMapping("/emptyDto")
  public Response<EmptyDto> emptyDto() {
    Response<EmptyDto> response = new Response<>();
    response.setResultObject(new EmptyDto());
    return response;
  }

  @RequestMapping("/nullList")
  public Response<List<String>> nullList() {
    List<String> temp = null;
    Response<List<String>> response = new Response<>();
    response.setResultObject(temp);
    return response;
  }


}
