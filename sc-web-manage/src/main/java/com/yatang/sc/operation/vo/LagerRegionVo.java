package com.yatang.sc.operation.vo;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 大区区域
 * @类名:
 * @作者: yipeng
 * @创建时间: 2017年06月07日17:50:36
 * @版本: v1.0
 */
@Getter
@Setter
public class LagerRegionVo implements java.io.Serializable {
  private static final long serialVersionUID = -8198832640974301939L;

  private String code;
  private String regionName;
  private List<RegionVo> regions;

  @Data
  public static class RegionVo implements java.io.Serializable {
    private String code;
    private String regionName;
    private String regionType;
    private List<RegionVo> regions;

  }

}
