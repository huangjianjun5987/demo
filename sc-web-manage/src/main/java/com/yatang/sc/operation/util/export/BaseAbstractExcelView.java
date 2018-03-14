package com.yatang.sc.operation.util.export;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.capitalize;

/**
 * @描述:Excel导出公共类
 * @作者: kangdong
 * @创建时间: 2017/5/25 11:46
 * @版本: v1.0
 */
@Slf4j
public class BaseAbstractExcelView<T> extends AbstractXlsView {

	private static final String	EXTENSION			= ".xls";

	private static final String	DEFAULT_FILENAME	= "default.xls";

	private Class<T>			clazz;

	private List<T>				elements;

	private String				fileName;



	public BaseAbstractExcelView(Class<T> clazz, List<T> elements) {
		this.clazz = clazz;
		this.elements = elements;
		initialize();
	}

	private Map<String, AnnotationData> annotationDataMap = Maps.newLinkedHashMap();



	protected void initialize() {

		Assert.notNull(clazz, "View class must not be null");

		ExportModel exportModel = clazz.getAnnotation(ExportModel.class);

		if (null == exportModel) {
			throw new IllegalArgumentException("No found exportModel annotation");
		}

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {

			ExportField exportField = field.getAnnotation(ExportField.class);
			if (null == exportField) {
				continue;
			}
			annotationDataMap.put(field.getName(),
					new AnnotationData(field.getName(), exportField.colName(), exportField.index()));
		}

		fileName = StringUtils.isNotBlank(exportModel.fileName()) ? exportModel.fileName().concat(EXTENSION)
				: DEFAULT_FILENAME;

	}



	private void setCellValue(HSSFRow row, Object result, int index) {

		if (result instanceof Double) {

			row.createCell(index).setCellValue((Double) result);

		} else if (result instanceof String) {

			row.createCell(index).setCellValue((String) result);

		} else if (result instanceof Boolean) {

			row.createCell(index).setCellValue((Boolean) result);

		} else if (result instanceof Date) {

			row.createCell(index).setCellValue(format.format((Date) result));

		} else if (result instanceof Integer) {

			row.createCell(index).setCellValue((Integer) result);

		} else if (result instanceof Long) {

			row.createCell(index).setCellValue((Long) result);

		} else if (result instanceof BigDecimal) {

			row.createCell(index).setCellValue(((BigDecimal) result).doubleValue());

		} else if (result instanceof Float) {

			row.createCell(index).setCellValue((Float) result);

		}

	}

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    protected String encodeFilename(String filename, HttpServletRequest request) {
        String agent = request.getHeader("USER-AGENT");
        try {
        	if (agent==null){
        		throw new RuntimeException("请求出错");
			}
            if (-1 != agent.indexOf("MSIE")) {
                String newFileName = URLEncoder.encode(filename, "UTF-8");
                return newFileName;
            }
            if (agent.indexOf("Firefox")>0){

                return new String(filename.getBytes("UTF-8"),"ISO8859-1");
            }
            return URLEncoder.encode(filename, "UTF-8");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return filename;
        }
    }




    @Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HSSFSheet sheet = (HSSFSheet) workbook.createSheet();

		HSSFRow header = sheet.createRow(0);

		int headerCellNum = 0;

		for (String key : annotationDataMap.keySet()) {
			header.createCell(headerCellNum++).setCellValue(annotationDataMap.get(key).colName);
		}

		int rowNum = 1;

		for (T value : elements) {

			HSSFRow row = sheet.createRow(rowNum++);

			for (String key : annotationDataMap.keySet()) {

				AnnotationData data = annotationDataMap.get(key);

				Object result = MethodUtils.invokeMethod(value, "get" + capitalize(key));

				setCellValue(row, result, data.index);

			}
		}

		response.setHeader("Content-disposition", "attachment;filename=" + encodeFilename(fileName, request));

	}

	@Data
	@AllArgsConstructor
	class AnnotationData {

		private String	name;

		private String	colName;

		private Integer	index;

	}
}
