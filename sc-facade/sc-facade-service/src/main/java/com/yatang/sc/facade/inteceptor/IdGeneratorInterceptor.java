package com.yatang.sc.facade.inteceptor;

import com.busi.idgenerator.IdGenerator;
import com.yatang.sc.facade.domain.PmPurchaseOrderPo;
import com.yatang.sc.facade.domain.SupplierInfoPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseReceiptPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseRefundPo;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service("idGeneratorInterceptor")
public class IdGeneratorInterceptor implements MethodInterceptor {

	@Autowired
	private IdGenerator idGenerator;



	@Override
	public Object invoke(MethodInvocation pMethodInvocation) throws Throwable {
		Object[] arguments = pMethodInvocation.getArguments();
		if (arguments[0] instanceof SupplierInfoPo) {
			SupplierInfoPo po = (SupplierInfoPo) arguments[0];
			po.setId(idGenerator.generateId("xprov"));
		} else if (arguments[0] instanceof PmPurchaseOrderPo) {// 生成流水单号
			// 生成流水号 yyyymmdd,4位数
			String dateFormat = "yyyyMMdd";// 1.前缀格式日期yymm
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);// 格式化
			String dateFormatStr = sdf.format(new Date());
			PmPurchaseOrderPo po = (PmPurchaseOrderPo) arguments[0];
			po.setPurchaseOrderNo(
					idGenerator.generateScPurchaseOrderId(dateFormatStr.substring(2, dateFormatStr.length())));
		} else if (arguments[0] instanceof PmPurchaseReceiptPo) {
			String dateFormat = "yyyyMMdd";// 1.前缀格式日期yyyyMMdd
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);// 格式化
			String dateFormatStr = sdf.format(new Date());
			PmPurchaseReceiptPo po = (PmPurchaseReceiptPo) arguments[0];
			po.setPurchaseReceiptNo(
					idGenerator.generateScReceiptOrderId(dateFormatStr.substring(2, dateFormatStr.length())));
		}
		//else if (arguments[0] instanceof PmPurchaseRefundPo){
		//	String dateFormat = "yyyyMMdd";// 1.前缀格式日期yyyyMMdd
		//	SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);// 格式化
		//	String dateFormatStr = sdf.format(new Date());
		//	PmPurchaseRefundPo po = (PmPurchaseRefundPo) arguments[0];
		//	po.setPurchaseRefundNo(
		//			idGenerator.generateScReturnPurchaseOrderId(dateFormatStr.substring(2, dateFormatStr.length())));
		//}
		return pMethodInvocation.proceed();
	}

}
