package com.yatang.sc.utils;

import com.google.common.collect.Lists;
import com.yatang.sc.operation.vo.pm.PmPurchaseOrderInfoVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseRefundVo;
import com.yatang.sc.operation.web.PmPurchaseOrderPdfGenerater;
import com.yatang.sc.operation.web.PmPurchaseOrderPdfsGenerater;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by yipeng on 2017/7/27.
 */
public class PdfGenerate {

	@Test
	public void generateOne() {

		PmPurchaseOrderPdfGenerater generater = new PmPurchaseOrderPdfGenerater("http://sit.image.com");
		try {
			PmPurchaseOrderInfoVo order = PmPurchaseOrderPdfGenerater.order();
			generater.generate(
					new FileOutputStream("target/" + order.getPurchaseOrderNo() + ".pdf"), order);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	@Test
	public void generateZip() {

		try {
			List<PmPurchaseOrderInfoVo> orders = Lists.newArrayList(PmPurchaseOrderPdfGenerater.order(),
					PmPurchaseOrderPdfGenerater.order(), PmPurchaseOrderPdfGenerater.order(),
					PmPurchaseOrderPdfGenerater.order());

			List<PmPurchaseOrderPdfsGenerater.GeneraterPdf> pdfs = Lists.newArrayList();
			PmPurchaseOrderPdfGenerater generater;
			PmPurchaseOrderPdfsGenerater.GeneraterPdf pdf;
			for (PmPurchaseOrderInfoVo order : orders) {
				generater = new PmPurchaseOrderPdfGenerater("http://sit.image.com");
				pdf = new PmPurchaseOrderPdfsGenerater.GeneraterPdf();
				pdf.setFileName(order.getPurchaseOrderNo() + ".pdf");
				pdf.setBytes(generater.generate(order));
				pdfs.add(pdf);
			}

			PmPurchaseOrderPdfsGenerater.generate(new FileOutputStream("target/采购订单.zip"), pdfs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generateRefund() {

		PmPurchaseOrderPdfGenerater generater = new PmPurchaseOrderPdfGenerater("http://sit.image.com");
		try {
//			PmPurchaseOrderInfoVo order = PmPurchaseOrderPdfGenerater.order();
			PmPurchaseRefundVo refundVo = PmPurchaseOrderPdfGenerater.refundVo();
			generater.generatePdf(
					new FileOutputStream("target/" + refundVo.getPurchaseRefundNo() + ".pdf"), refundVo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
