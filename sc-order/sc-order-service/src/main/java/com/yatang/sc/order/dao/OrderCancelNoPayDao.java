package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.CompanyCancelNoPayPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderCancelNoPayDao {

	List<CompanyCancelNoPayPo> getCompanyCancelNoPay();

	CompanyCancelNoPayPo getNoPayByCompanyId(@Param("companyId") String companyId);
}
