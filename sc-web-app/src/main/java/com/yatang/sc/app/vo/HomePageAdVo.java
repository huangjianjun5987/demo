package com.yatang.sc.app.vo;

import com.yatang.sc.facade.dto.HomeAreaAdDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author tangqi
 * @create 2017-11-17 9:53
 * @desc 首页配置
 **/
@Getter
@Setter
public class HomePageAdVo {
    private int id;
    private String companyId;
    private int isUsingNation;
    private List<HomeAreaAdVo> areas;
}
