package com.yatang.sc.facade.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class QueryHomePageAdDto implements Serializable{

    private static final long serialVersionUID = -677737342343124035L;
    private String companyId;

    private Integer homePageType;//1:global,0:custom
}
