package com.yatang.sc.operation.web;

import com.google.common.collect.Lists;
import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceGray;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDate;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderItemDto;
import com.yatang.sc.operation.util.NumberToCN;
import com.yatang.sc.operation.vo.pm.PmPurchaseOrderInfoVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseRefundItemVo;
import com.yatang.sc.operation.vo.pm.PmPurchaseRefundVo;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.startsWith;

/**
 * Created by yipeng on 2017/7/27.
 */
@Log4j
public class PmPurchaseOrderPdfGenerater {

  private String imageDomain;

  //	public static final String FONT = "font/NotoSansCJKsc-Regular.otf";
//	public static final String FONT = "font/msyh.ttf";
  public static final String FONT_LOCAL = "font/WenQuanYiMicroHei.ttf";
  public static final String FONT = "/usr/share/fonts/pdf/WenQuanYiMicroHei.ttf";

  public PdfFormXObject template;
  public Image total;

  private PdfFont labelFont;

//	{
//		try {
////			labelFont = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false, true);
//			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
//			labelFont = PdfFontFactory.createFont(contextClassLoader.getResource(FONT).getFile(), PdfEncodings.IDENTITY_H);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}

  public PmPurchaseOrderPdfGenerater(String imageDomain) {
    this.imageDomain = imageDomain;

//			InputStream in = ResourceUtil.getResourceStream(FONT);
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			byte[] b = new byte[10000];
//			while (true) {
//				int r = in.read(b);
//				if (r == -1)
//					break;
//				out.write(b, 0, r);
//			}
//
//			labelFont = PdfFontFactory.createFont(out.toByteArray(), PdfEncodings.IDENTITY_H);

//			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
//			PdfFontFactory.registerDirectory(contextClassLoader.getResource(FONTDIR).getFile());
//			labelFont = PdfFontFactory.createRegisteredFont("notosanscjksc-regular", PdfEncodings.IDENTITY_H);

    try {
      labelFont = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
    } catch (Exception e) {
      try {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        labelFont = PdfFontFactory.createFont(contextClassLoader.getResource(FONT_LOCAL).getFile(), PdfEncodings.IDENTITY_H);
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    }
//			labelFont = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false, true);
  }

  public byte[] generate(PmPurchaseOrderInfoVo order) throws IOException {
    ByteArrayOutputStream ous = new ByteArrayOutputStream();
    generate(ous, order);
    return ous.toByteArray();
  }


  public void generate(OutputStream ous, PmPurchaseOrderInfoVo order) throws IOException {
    PdfWriter writer = new PdfWriter(ous);
    // Initialize PDF document
    PdfDocument pdfDoc = new PdfDocument(writer);

    // Initialize document
    Document document = new Document(pdfDoc, PageSize.A4.rotate());

    Table table = new Table(new float[]{850, 118, 90});
    table.addCell(new Cell().setBorder(Border.NO_BORDER).add(
      new Paragraph("雅堂小超采购订单").setMarginLeft(280) .setFont(labelFont).setFontSize(24)));
//		Barcode128 code128 = new Barcode128(pdfDoc);
//		code128.setCode(order.getPurchaseOrderNo());
//		code128.setCodeType(Barcode128.CODE128);
//		Image code128Image = new Image(code128.createFormXObject(pdfDoc));
    table.addCell(new Cell().setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE).add(new Paragraph("入库单号：").setFont(labelFont).setBold()));
    String text = order.getBarCodeUrl();
    if (StringUtils.isNotEmpty(text)) {
      try {
        Image code128Image = new Image(ImageDataFactory.create(startsWith(text, "http://") || startsWith(text, "https://") ? text
                : (imageDomain + (startsWith(text, "/") ? text : "/" + text)))).setHeight(40);
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(code128Image));
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        table.addCell(new Cell().setBorder(Border.NO_BORDER));
      }
    } else {
      table.addCell(new Cell().setBorder(Border.NO_BORDER));
    }
    document.add(table);

    document.add(new Paragraph());

    document.add(orderHeaderTable(order));
    document.add(new Paragraph());
    document.add(orderItemTable(order.getPmPurchaseOrderItems()));
    document.add(new Paragraph());
    document.add(orderItemTotalTable(order));

    document.close();
    writer.close();
    pdfDoc.close();
  }


  private Cell labelCell(String text) {
    return new Cell().setBorder(Border.NO_BORDER)
      .add(new Paragraph(StringUtils.defaultString(text, StringUtils.EMPTY)).setFont(labelFont).setBold());
  }


  private Cell fieldCell(String text) {
    return new Cell().setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5F))
      .add(new Paragraph(StringUtils.defaultString(text, StringUtils.EMPTY))).setFont(labelFont);
  }

  private String getOrderType(Integer num) {
    switch (num) {
      case 1:
        return "赠品采购";
      case 2:
        return "促销采购";
      default:
        return "普通采购";
    }
  }

  private Table orderHeaderTable(PmPurchaseOrderInfoVo order) {
    float[] columnWidths = {80, 120, 80, 250, 80, 120, 100, 60};
    Table table = new Table(columnWidths);
    table.addCell(labelCell("采购单号："));
    table.addCell(fieldCell(order.getPurchaseOrderNo()));
    table.addCell(labelCell("供应商："));
    table.addCell(fieldCell(order.getSpAdrNo() + "-" + order.getSpAdrName()));
    table.addCell(labelCell("经营模式："));
    table.addCell(fieldCell(order.getBusinessMode() == 0 ? "经销" : "代销"));
    table.addCell(labelCell("采购单类型："));
    table.addCell(fieldCell(getOrderType(order.getPurchaseOrderType())));
    table.startNewRow();
    table.addCell(labelCell("收货单位："));
    table.addCell(fieldCell(order.getAdrTypeCode() + "-" + order.getAdrTypeName()));
    table.addCell(labelCell("收货地址："));
    table.addCell(fieldCell(order.getAdrName()));
    table.addCell(labelCell("联系方式："));
    table.addCell(fieldCell(order.getPhone()));
    table.addCell(labelCell("货币："));
    table.addCell(fieldCell(order.getCurrencyCode()));
    table.startNewRow();
    table.addCell(labelCell("大类："));
    table.addCell(fieldCell(order.getSecondCategoryName()));
    table.addCell(labelCell("收货日期："));
    table.addCell(fieldCell(new DateTime(order.getEstimatedDeliveryDate()).toString("yyyy-MM-dd")));
    table.addCell(labelCell("创建人："));
    table.addCell(fieldCell(order.getCreateUserName()));

    return table;
  }


  private Table orderItemTable(List<PmPurchaseOrderItemDto> orderItems) {
    float[] columnWidths = {50, 120, 270, 120, 100, 150, 130, 50, 100, 100, 100};
    Table table = new Table(columnWidths);

    List<String> headers = Lists.newArrayList("行号", "商品编码", "商品名称", "商品条码", "商品规格", "产地", "采购内装数", "单位", "订货数量",
      "订货单价", "订货金额");
    for (String header : headers) {
      table.addCell(headerCell().add(new Paragraph(header).setBold().setFont(labelFont)));
    }
    table.startNewRow();

    for (int i = 0; i < orderItems.size(); i++) {
      PmPurchaseOrderItemDto orderItem = orderItems.get(i);
      table.addCell(bodyCell(String.valueOf(i + 1)));
      table.addCell(bodyCell(orderItem.getProductCode()));
      table.addCell(bodyCell(orderItem.getProductName()));
      table.addCell(bodyCell(orderItem.getInternationalCode()));
      table.addCell(bodyCell(orderItem.getPackingSpecifications()));
      table.addCell(bodyCell(orderItem.getProducePlace()));
      table.addCell(bodyCell(String.valueOf(orderItem.getPurchaseInsideNumber())));
      table.addCell(bodyCell(orderItem.getUnitExplanation()));
      table.addCell(bodyCell(String.valueOf(orderItem.getPurchaseNumber())));
      table.addCell(bodyCell(String.valueOf(orderItem.getPurchasePrice().setScale(2, BigDecimal.ROUND_HALF_UP))));
      table.addCell(bodyCell(String.valueOf(orderItem.getTotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP))));
      if (i != 0 && i != orderItems.size() - 1) {
        table.startNewRow();
      }
    }

    return table;
  }


  private Cell headerCell() {
    return new Cell().setBackgroundColor(new DeviceGray(0.85f)).setTextAlignment(TextAlignment.CENTER);
  }


  private Cell bodyCell(String text) {
    return new Cell().add(new Paragraph(StringUtils.defaultString(text, StringUtils.EMPTY)).setFont(labelFont));
  }


  private Table orderItemTotalTable(PmPurchaseOrderInfoVo order) {
    float[] columnWidths = {100, 80, 100, 100, 50, 50, 150, 330};
    Table table = new Table(columnWidths);
    table.addCell(labelCell("合计数量："));
    table.addCell(fieldCell(order.getTotalNumber().toString()));
    table.addCell(labelCell("合计金额："));
    table.addCell(fieldCell(String.valueOf(order.getTotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP))));
    table.addCell(labelCell(""));
    table.addCell(labelCell(""));
    table.addCell(labelCell("合计金额（大写）："));
    table.addCell(fieldCell(order.getNumberToCN()));

    return table;
  }


  public static PmPurchaseOrderInfoVo order() {
    PmPurchaseOrderInfoVo order = new PmPurchaseOrderInfoVo();
    order.setPurchaseOrderNo(new DateTime().toString("yyyyMMddhh") + RandomUtils.nextInt(10000, 99999));
    order.setSpAdrNo("10001");
    order.setSpAdrName("四川 - 四川鬼爪进口饮料有限公司");
    order.setPurchaseOrderType(0);
    order.setCreateUserName("刘大炮");
    order.setAdrTypeCode("10");
    order.setAdrTypeName("成都子天府仓");
    order.setAdrName("成都市天府新区龙腾大道999号");
    order.setPhone("028-12345678");
    order.setCurrencyCode("CNY");
    order.setSecondCategoryName("饮料");
    order.setEstimatedDeliveryDate(new DateTime().plusDays(RandomUtils.nextInt(10, 90)).toDate());
    order.setBarCodeUrl("group1/M00/01/40/rB4KPVmeli6AWn7nAAABP03fCrA105.png");
    order.setBusinessMode(1);
    order.setPurchaseOrderType(2);


    List<PmPurchaseOrderItemDto> orderItems = Lists.newArrayList();
    BigDecimal total = BigDecimal.ZERO;
    int totalPurchaseNumber = 0;
    for (int i = 0; i < RandomUtils.nextInt(1, 10) * 100; i++) {
      PmPurchaseOrderItemDto orderItem = new PmPurchaseOrderItemDto();
      orderItem.setProductCode(new DateTime().toString("ddhhSSS"));
      orderItem.setProductName("凉茶功能饮料" + new DateTime().toString("SSS"));
      orderItem.setInternationalCode("1234567890");
      orderItem.setPackingSpecifications("500ml");
      orderItem.setProducePlace("美国");
      orderItem.setPurchaseInsideNumber(10);
      orderItem.setUnitExplanation("瓶");
      orderItem.setPurchaseNumber(RandomUtils.nextInt(1, 10) * 10);
      orderItem.setPurchasePrice(new BigDecimal(RandomUtils.nextInt(10000, 100000)));
      orderItem.setTotalAmount(orderItem.getPurchasePrice().multiply(new BigDecimal(orderItem.getPurchaseNumber())).setScale(2, BigDecimal.ROUND_HALF_UP));

//			orderItem.setProductCode("1000401");
//			orderItem.setProductName("桃李天然酵母面包(巧克力味)");
//			orderItem.setInternationalCode("6922222702163");
//			orderItem.setPackingSpecifications("500ml");
//			orderItem.setProducePlace("美国");
//			orderItem.setPurchaseInsideNumber(1);
//			orderItem.setUnitExplanation("个");
//			orderItem.setPurchaseNumber(2647);
//			orderItem.setPurchasePrice(new BigDecimal(2.08));
//			orderItem.setTotalAmount(orderItem.getPurchasePrice().multiply(new BigDecimal(orderItem.getPurchaseNumber())).setScale(2, BigDecimal.ROUND_HALF_UP));

//			orderItem.setProductCode("1000064");
//			orderItem.setProductName("12");
//			orderItem.setInternationalCode("1313");
//			orderItem.setPackingSpecifications("12");
//			orderItem.setProducePlace("12");
//			orderItem.setPurchaseInsideNumber(1);
//			orderItem.setUnitExplanation("包");
//			orderItem.setPurchaseNumber(1000);
//			orderItem.setPurchasePrice(new BigDecimal(1000));
//			orderItem.setTotalAmount(orderItem.getPurchasePrice().multiply(new BigDecimal(orderItem.getPurchaseNumber())).setScale(2, BigDecimal.ROUND_HALF_UP));

      orderItems.add(orderItem);

      total = total.add(orderItem.getTotalAmount());
      totalPurchaseNumber += orderItem.getPurchaseNumber();
    }
//		order.setTotalAmount(new BigDecimal(16178934.14));
    order.setTotalAmount(total);
    order.setTotalNumber(totalPurchaseNumber);
    NumberToCN numberToCN = new NumberToCN();
    order.setNumberToCN(numberToCN.number2CNMontrayUnit(order.getTotalAmount()));
    order.setPmPurchaseOrderItems(orderItems);
    return order;
  }

  public static PmPurchaseRefundVo refundVo() {
    PmPurchaseRefundVo refundVo = new PmPurchaseRefundVo();
    refundVo.setId(10001L);
    refundVo.setPurchaseRefundNo("2017102410001");
    refundVo.setSpId("xprov001");
    refundVo.setSpNo("10001");
    refundVo.setSpName("四川鑫恒创贸易有限公司");
    refundVo.setSpAdrId("1");
    refundVo.setSpAdrNo("1000001");
    refundVo.setSpAdrName("四川-四川鑫恒创贸易有限公司");
    refundVo.setRefundAdrCode("TJXQS");
    refundVo.setRefundAdrName("济南仓山东子公司常温正常仓");
    refundVo.setAdrType(0); // 地点类型为仓库
    refundVo.setRefundAdrCode("TJXQ");
    refundVo.setSecondCategoryId("1001");
    refundVo.setSecondCategoryName("酒水饮料");
    refundVo.setCurrencyCode("CNY");
    refundVo.setBranchCompanyId("10003");
    refundVo.setStatus(0);
    refundVo.setRefundTime(new Date());
    refundVo.setRefundTimeEarly(new Date());
    refundVo.setFailedReason("原因不明");
    refundVo.setCreateUserId("李云");
    refundVo.setCreateTime(new Date());
    refundVo.setModifyUserId("李云1");
    refundVo.setModifyTime(new Date());
    refundVo.setAuditUserId("李云2");
    refundVo.setAuditTime(new Date());
    refundVo.setRemark("暂无");

    List<PmPurchaseRefundItemVo> itemVos = new ArrayList<>();
    Integer totalRefundAmount = 0;
    Integer totalRealRefundAmount = 0;
    BigDecimal totalRefundMoney = BigDecimal.ZERO;
    BigDecimal totalRealRefundMoney = BigDecimal.ZERO;
    for (int i = 0; i < RandomUtils.nextInt(20, 30) * 10; i++) {
      PmPurchaseRefundItemVo itemVo = new PmPurchaseRefundItemVo();
      itemVo.setId(12L);
      itemVo.setPurchaseRefundId("2017102410001");
      itemVo.setPurchaseOrderNo("O10010");
      itemVo.setProductId("10025");
      itemVo.setProductCode("1002568");
      itemVo.setProductName("蓝月亮深层洁净亮白增艳洗衣液自然清香2kg(QSC6205)");
      itemVo.setInternationalCode("6926130531273");
      itemVo.setPackingSpecifications("108g");
      itemVo.setProducePlace("绵阳");
      itemVo.setPurchaseInsideNumber(240);
      itemVo.setUnitExplanation("对");
      itemVo.setInputTaxRate(BigDecimal.valueOf(0.17).setScale(2, BigDecimal.ROUND_HALF_UP));
      itemVo.setPurchasePrice(BigDecimal.valueOf(300.4).setScale(2, BigDecimal.ROUND_HALF_UP));
      itemVo.setPossibleNum(30);
      itemVo.setRefundAmount(800);
      itemVo.setRefundMoney(BigDecimal.valueOf(5912345.67).setScale(2, BigDecimal.ROUND_HALF_UP));
      itemVo.setRefundCost(BigDecimal.valueOf(51).setScale(2, BigDecimal.ROUND_HALF_UP));
      itemVo.setRealRefundAmount(800);
      itemVo.setRealRefundMoney(BigDecimal.valueOf(5912345.67).setScale(2, BigDecimal.ROUND_HALF_UP));
      itemVo.setRefundReason(1);
      itemVo.setCreateTime(new Date());
      itemVo.setCreateUserId("李云");
      itemVo.setModifyTime(new Date());
      itemVo.setModifyUserId("玛云");
      itemVo.setIsValid(1);
      itemVos.add(itemVo);

      totalRefundAmount += itemVo.getRefundAmount();
      totalRealRefundAmount += itemVo.getRealRefundAmount();
      totalRefundMoney = totalRefundMoney.add(itemVo.getRefundMoney());
      totalRealRefundMoney = totalRealRefundMoney.add(itemVo.getRealRefundMoney());
    }
    refundVo.setPmPurchaseRefundItems(itemVos);
    refundVo.setTotalRefundAmount(totalRefundAmount);
    refundVo.setTotalRealRefundAmount(totalRealRefundAmount);
    refundVo.setTotalRefundMoney(totalRefundMoney);
    refundVo.setTotalRealRefundMoney(totalRealRefundMoney);

    return refundVo;
  }

  public void generatePdf(OutputStream ous, PmPurchaseRefundVo refundVo) throws IOException {
    PdfWriter writer = new PdfWriter(ous);

    // Initialize PDF document
    PdfDocument pdfDoc = new PdfDocument(writer);

    // Initialize document
    Document document = new Document(pdfDoc, PageSize.A4.rotate());

    template = new PdfFormXObject(new Rectangle(765, 25, 30, 30));
    PdfCanvas canvas = new PdfCanvas(template, pdfDoc);
    total = new Image(template);
    total.setRole(PdfName.Artifact);
    pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new HeaderHandler());
    pdfDoc.getCatalog().setLang(new PdfString("en-us"));
    PdfDictionary parameters = new PdfDictionary();
    parameters.put(PdfName.ModDate, new PdfDate().getPdfObject());

      Table table = new Table(new float[]{850, 118, 90});
      Image code128Image = null;

      if (null == refundVo) {
        log.error("采购退货信息为空，导出退货单PDF失败！");
        throw new RuntimeException("采购退货信息为空，导出退货单PDF失败！");
      }

    table.addCell(new Cell().setBorder(Border.NO_BORDER).add(
            new Paragraph("雅堂小超退货单").setMarginLeft(290) .setFont(labelFont).setFontSize(24).setFontColor(Color.RED)));
    Barcode128 code128 = new Barcode128(pdfDoc);
    code128.setCode(refundVo.getPurchaseRefundNo());
    code128.setCodeType(Barcode128.CODE128);
    code128Image = new Image(code128.createFormXObject(pdfDoc));
    table.addCell(new Cell().setTextAlignment(TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE)
            .setBorder(Border.NO_BORDER).add(new Paragraph("出库单号：").setFont(labelFont).setFontSize(12)));

      try {
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(code128Image));
      } catch (Exception e) {

        e.printStackTrace();
      } finally {
        table.addCell(new Cell().setBorder(Border.NO_BORDER));
      }

    document.add(table);

    document.add(new Paragraph());
    document.add(refundHeaderTable(refundVo));
    document.add(new Paragraph());
    document.add(refundItemTable(refundVo.getPmPurchaseRefundItems()));
    document.add(new Paragraph());
    document.add(refundItemTotalTable(refundVo));
    document.add(new Paragraph());
    document.add(bottomTable());

    canvas.beginText();
    canvas.setFontAndSize(labelFont, 8);
    canvas.moveText(765, 25);
    canvas.showText(String.format("共 %d 页 ", pdfDoc.getNumberOfPages()));
    canvas.endText();
    canvas.stroke();

    document.close();
    writer.close();
    pdfDoc.close();
  }

  private Table refundHeaderTable(PmPurchaseRefundVo refundVo) {
    float[] columnWidths = {55, 180, 55, 250, 35, 50};
    Table table = new Table(columnWidths);
    table.addCell(labelCell("退货单号：").setFontColor(Color.RED).setFontSize(10));
    table.addCell(fieldCell(refundVo.getPurchaseRefundNo()).setFontSize(10));
    table.addCell(labelCell("供应商：").setFontColor(Color.RED).setFontSize(10));
    table.addCell(fieldCell(refundVo.getSpNo() + "-" + refundVo.getSpName()).setFontSize(10));
    table.startNewRow();
    table.addCell(labelCell("退货单位：").setFontColor(Color.RED).setFontSize(10));
    table.addCell(fieldCell(refundVo.getRefundAdrCode() + "-" + refundVo.getRefundAdrName()).setFontSize(10));
    table.addCell(labelCell("退货地点：").setFontColor(Color.RED).setFontSize(10));
    table.addCell(fieldCell(refundVo.getWarehouseOrStoreAddress()).setFontSize(10));
    table.addCell(labelCell("货币：").setFontColor(Color.RED).setFontSize(10));
    table.addCell(fieldCell(refundVo.getCurrencyCode()).setFontSize(10));
    table.startNewRow();
    table.addCell(labelCell("创建人：").setFontColor(Color.RED).setFontSize(10));
    table.addCell(fieldCell(refundVo.getCreateUserId()).setFontSize(10));
    table.addCell(labelCell("创建时间：").setFontColor(Color.RED).setFontSize(10));
    table.addCell(fieldCell(new SimpleDateFormat("yyyy-MM-dd").format(refundVo.getCreateTime())).setFontSize(10));
    table.startNewRow();
    table.addCell(labelCell("备注：").setFontColor(Color.RED).setFontSize(10));
    table.addCell(fieldCell(refundVo.getRemark()).setFontSize(10));
    return table;
  }

  private Table refundItemTable(List<PmPurchaseRefundItemVo> refundItems) {
    float[] columnWidths = {32, 40, 70, 320, 50, 45, 60, 60, 80, 80, 110};
    Table table = new Table(columnWidths);

    List<String> headers = Lists.newArrayList("行号", "商品编号", "商品条码", "商品名称", "商品规格", "箱装数",
            "退货单价", "退货数量","退货金额 (含税)","实际退货数量","实际退货金额 (含税)");
    for (String header : headers) {
      table.addCell(headerCell().add(new Paragraph(header).setBold().setFont(labelFont).setFontSize(8)));
    }
    table.startNewRow();

    for (int i = 0; i < refundItems.size(); i++) {
      PmPurchaseRefundItemVo fundItem = refundItems.get(i);
      table.addCell(bodyCell(String.valueOf(i + 1)).setTextAlignment(TextAlignment.CENTER).setFontSize(8));
      table.addCell(bodyCell(fundItem.getProductCode()).setTextAlignment(TextAlignment.CENTER).setFontSize(8));
      table.addCell(bodyCell(fundItem.getInternationalCode()).setTextAlignment(TextAlignment.CENTER).setFontSize(8));
      table.addCell(bodyCell(fundItem.getProductName()).setFontSize(8));
      table.addCell(bodyCell(fundItem.getPackingSpecifications()).setFontSize(8));
      table.addCell(bodyCell(String.valueOf(fundItem.getPurchaseInsideNumber())).setTextAlignment(TextAlignment.RIGHT).setFontSize(8));
      table.addCell(bodyCell(String.valueOf(String.valueOf((null == fundItem.getPurchasePrice()) ? "" : fundItem.getPurchasePrice().setScale(2, BigDecimal.ROUND_HALF_UP)))).setTextAlignment(TextAlignment.RIGHT).setFontSize(8));
      table.addCell(bodyCell(String.valueOf(String.valueOf((null == fundItem.getRefundAmount()) ? "" : fundItem.getRefundAmount()))).setTextAlignment(TextAlignment.RIGHT).setFontSize(8));
      table.addCell(bodyCell(String.valueOf(String.valueOf((null == fundItem.getRefundMoney()) ? "" : fundItem.getRefundMoney().setScale(2, BigDecimal.ROUND_HALF_UP)))).setTextAlignment(TextAlignment.RIGHT).setFontSize(8));
      table.addCell(bodyCell(String.valueOf(String.valueOf((null == fundItem.getRealRefundAmount()) ? "" : fundItem.getRealRefundAmount()))).setTextAlignment(TextAlignment.RIGHT).setFontSize(8));
      table.addCell(bodyCell(String.valueOf(String.valueOf((null == fundItem.getRealRefundMoney()) ? "" : fundItem.getRealRefundMoney().setScale(2, BigDecimal.ROUND_HALF_UP)))).setTextAlignment(TextAlignment.RIGHT).setFontSize(8));
      if (i != 0 && i != refundItems.size() - 1) {
        table.startNewRow();
      }
    }
    return table;
  }

  private Table refundItemTotalTable(PmPurchaseRefundVo refundVo) {
    float[] columnWidths = {63, 35, 84, 60, 63, 35, 84, 60, 110, 190};
    Table table = new Table(columnWidths);
    table.addCell(labelCell("合计退货数量：").setFontSize(8));
    table.addCell(fieldCell(refundVo.getTotalRefundAmount().toString()).setFontSize(8));
    table.addCell(labelCell("合计退货金额(含税)：").setFontSize(8));
    table.addCell(fieldCell(String.valueOf(refundVo.getTotalRefundMoney().setScale(2, BigDecimal.ROUND_HALF_UP))).setFontSize(8));
    table.addCell(labelCell("合计实退数量：").setFontSize(8));
    table.addCell(fieldCell(refundVo.getTotalRealRefundAmount().toString()).setFontSize(8));
    table.addCell(labelCell("合计实退金额(含税)：").setFontSize(8));
    table.addCell(fieldCell(String.valueOf(refundVo.getTotalRealRefundMoney().setScale(2, BigDecimal.ROUND_HALF_UP))).setFontSize(8));
    table.addCell(labelCell("合计实退金额(含税)(大写)：").setFontSize(8));
    table.addCell(fieldCell(refundVo.getNumberToCN()).setFontSize(8));
    return table;
  }

  private Table bottomTable() {
    float[] columnWidths = {370, 370};
    Table table = new Table(columnWidths);
    table.addCell(labelCell("退货人：").setFontSize(10));
    table.addCell(labelCell("收货人：").setFontSize(10));
    table.startNewRow();
    table.addCell(labelCell("日期：").setFontSize(10));
    table.addCell(labelCell("日期：").setFontSize(10));
    return table;
  }

  public class HeaderHandler implements IEventHandler {
    @Override
    public void handleEvent(Event event) {
      PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
      PdfPage page = docEvent.getPage();
      int pageNum = docEvent.getDocument().getPageNumber(page);
      PdfCanvas canvas = new PdfCanvas(page);
      canvas.beginText();
      canvas.setFontAndSize(labelFont, 8);
      canvas.beginMarkedContent(PdfName.Artifact);
      canvas.moveText(34, 25);
//      canvas.showText("Test");
      canvas.moveText(703, 0);
      canvas.showText(String.format("第 %d 页 ", pageNum));
      canvas.endText();
      canvas.stroke();
      canvas.addXObject(template, 0, 0);
      canvas.endMarkedContent();
      canvas.release();
    }
  }

}
