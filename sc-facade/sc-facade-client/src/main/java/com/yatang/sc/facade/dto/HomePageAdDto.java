package com.yatang.sc.facade.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author tangqi
 * @create 2017-11-17 9:51
 * @desc 首页配置
 **/
@Getter
@Setter
public class HomePageAdDto {
    private int id;
    private String companyId;
    private int isUsingNation;
    private List<HomeAreaAdDto> areas;
}
