package com.yatang.sc.web.common.excel.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yatang.sc.web.common.excel.annotation.ExcelColConverter;
import com.yatang.sc.web.common.excel.converter.AbstractConverter;
import com.yatang.sc.web.common.excel.converter.UnionValuesConverter;
import com.yatang.sc.web.common.excel.service.IExcelExportService;
import com.yatang.sc.web.common.util.JSONUtils;

@Service
public class ExcelExportService implements IExcelExportService {

	private ApplicationContext applicationContext;
	private String configFilePath;// 导出配置信息
	protected List<ExcelCol> excelColList = new ArrayList<>();// 导出列信息
	protected Map<String, List<Map<String, Object>>> excelConfigData;// 导出配置信息数据

	protected Map<String, AbstractConverter> dataConverterMap = new HashMap<>();// 转换器信息

	/**
	 * 扫描注解，获取并填充转化器信息
	 */
	@PostConstruct
	protected void fillConverterMap() {
		Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(ExcelColConverter.class);
		Set<Entry<String, Object>> entrySet = beansWithAnnotation.entrySet();
		for (Entry<String, Object> entry:entrySet) {
			Object bean = entry.getValue();
			if (bean instanceof AbstractConverter) {
				String type = bean.getClass().getAnnotation(ExcelColConverter.class).type();
				boolean repacle = bean.getClass().getAnnotation(ExcelColConverter.class).repacle();
				if (dataConverterMap.get(type) == null || repacle) {
					dataConverterMap.put(type, (AbstractConverter) bean);
				}
			}
		}
	}

	/**
	 * 填充列信息并通过列order排序
	 * 
	 * @param name
	 */
	protected void fillExcelColList(String name) {
		excelColList.clear();
		// 获取配置文件
		Map<String, List<Map<String, Object>>> loadExportConfigFile = loadExportConfigFile();
		if (loadExportConfigFile == null) {
			return;
		}
		// 填充excelColList
		Set<Entry<String, List<Map<String, Object>>>> entrySet =loadExportConfigFile.entrySet();
		for (Entry<String, List<Map<String, Object>>> entry:entrySet) {
			String key = entry.getKey();
			List<Map<String, Object>> colList = entry.getValue();
			if (key.equals(name)) {
				for(Map<String, Object> col:colList){
					String cName = (String) col.get("name");
					String cDisplayName = (String) col.get("displayName");
					String cProperty = (String) col.get("property");
					Integer cOrder = (Integer) col.get("order");
					String converterName = (String) col.get("converterName");
					Integer cWidth = (Integer) col.get("width");
					String cProperties = (String) col.get("properties");
					String cDisplayFormat= (String) col.get("displayFormat");
					excelColList
							.add(new ExcelCol(cName,cDisplayName, cProperty, cOrder, converterName, cWidth != null ? cWidth : 0,cProperties,cDisplayFormat));
				}
			}
		}
		// 排序excelColList
		Collections.sort(excelColList, new Comparator<ExcelCol>() {
			@Override
			public int compare(ExcelCol o1, ExcelCol o2) {
				return o1.getOrder() - o2.getOrder();
			}
		});

	}

	/**
	 * 获取配置文件数据,用于填充excelColList
	 * 
	 * @throws IOException
	 */
	protected Map<String, List<Map<String, Object>>> loadExportConfigFile() {
		// if (excelConfigData != null) {
		// return excelConfigData;
		// }
		if (StringUtils.isEmpty(configFilePath)) {
			configFilePath = "config/export/exportExcel.json";
		}
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(configFilePath);
		ObjectMapper mapper = new ObjectMapper();

		try {
			excelConfigData = mapper.readValue(in, new TypeReference<Map<String, List<Map<String, Object>>>>() {
			});
		} catch (IOException e) {
			throw new RuntimeException("Erorr happen when load file", e);
		}
		return excelConfigData;
	}

	 /**
     * 导出Excel模板
     * @param name
     * @param response
     * @throws Exception
     */
	@Override
    public  void excelExport(String name, List exportData, HttpServletResponse response) throws Exception{
		List<Map<String, Object>> data = JSONUtils.toObject(JSONUtils.toJson(exportData), new TypeReference<List<Map<String,Object>>>(){});
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName =name+ df.format(new Date());
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ fileName +".xlsx");
        XSSFWorkbook workBook;
		workBook = excelExport(name, data);
		try{
	        workBook.write(response.getOutputStream());
		}finally{
			workBook.close();
		}
    }
    
	/**
	 * 根据数据生成Excel工作簿
	 * 
	 * @param name
	 * @param exportData
	 * @return
	 */
	public XSSFWorkbook excelExport(String name, List<Map<String, Object>> exportData) {
		Assert.notNull(name);
		fillExcelColList(name);
		try {
			XSSFWorkbook createExportWorkBook = createExportWorkBook(exportData);
			return createExportWorkBook;
		} catch (Exception e) {
			throw new RuntimeException("Create Excel Error");
		}
	}

	protected XSSFWorkbook createExportWorkBook(List<Map<String, Object>> exportData)
			throws Exception {
		List<String> keyList = getExportColmunKey();
		Map<String,String> displaynameMap =  getExportDisplayName();
		// 第一步，创建一个webbook，对应一个Excel文件
		XSSFWorkbook wb = new XSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = wb.createSheet(getSheetName());
		for (ExcelCol ec : excelColList) {
			if (ec.getWidth() != 0) {
				sheet.setColumnWidth(ec.getOrder() - 1, ec.getWidth() * 256);
			}
		}
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		XSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		// 建立表头
		for (int index = 0; index < keyList.size(); index++) {
			XSSFCell cell = row.createCell((short) index);
			String displayName = displaynameMap.get(keyList.get(index));
			cell.setCellValue(StringUtils.isEmpty(displayName)?keyList.get(index):displayName);
			cell.setCellStyle(style);
		}
		// 建立行数据
		if (exportData != null && !exportData.isEmpty()) {
			for (int valueIndex = 0; valueIndex < exportData.size(); valueIndex++) {
				Map valueMap = exportData.get(valueIndex);
				row = sheet.createRow(valueIndex + 1);
				int keyIndex = 0;
				for (ExcelCol ec : excelColList) {
					String key = StringUtils.isNoneBlank(ec.getProperty()) ? ec.getProperty() : ec.getName();
					Object value = valueMap.get(key);
					AbstractConverter converter = dataConverterMap.get(ec.getConverterName());
					
					if(converter != null&&converter instanceof UnionValuesConverter){
						UnionValuesConverter uvc=(UnionValuesConverter)converter;
						row.createCell(keyIndex).setCellValue(uvc.exportValue(valueMap,ec.getProperties(),ec.getDisplayFormat()));
						keyIndex++;
						continue;
					}
					
					if (value == null) {
						keyIndex++;
						continue;
					}
					
					if (converter != null) {
						row.createCell(keyIndex).setCellValue(converter.exportValue(value));
						keyIndex++;
						continue;
					}
					row.createCell(keyIndex).setCellValue(value.toString());
					keyIndex++;
				}
			}
		}
		return wb;
	}

	protected List<String> getExportColmunKey() {
		List<String> keyList = new ArrayList<>();
		for(ExcelCol excelCol:excelColList){
			keyList.add(excelCol.getName());

		}
		return keyList;
	}
	
	protected Map<String,String> getExportDisplayName() {
		Map<String,String> map = new HashMap<>();
		for(ExcelCol excelCol:excelColList){
			map.put(excelCol.getName(),excelCol.getDisplayName());
		}
		return map;
	}

	public List<Map<String, Object>> getExportData() {
		return null;
	};

	public String getSheetName() {
		return "data";
	}

	public void setExcelColList(List<ExcelCol> excelColList) {
		this.excelColList = excelColList;
	}

	
	public class ExcelCol {
		private String name;
		private String displayName;
		private String property;
		private int order;
		private String converterName;
		private int width;
		private String properties;
		private String displayFormat;
		ExcelCol(String name,String displayName, String property, int order, String converterName, int width,String properties,String displayFormat) {
			this.name = name;
			this.property = property;
			this.order = order;
			this.converterName = converterName;
			this.width = width;
			this.displayName=displayName;
			this.properties = properties;
			this.displayFormat=displayFormat;
		}

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getOrder() {
			return order;
		}

		public void setOrder(int order) {
			this.order = order;
		}

		public String getConverterName() {
			return converterName;
		}

		public void setConverterName(String converterName) {
			this.converterName = converterName;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public String getProperty() {
			return property;
		}

		public void setProperty(String property) {
			this.property = property;
		}

		public String getProperties() {
			return properties;
		}

		public void setProperties(String properties) {
			this.properties = properties;
		}

		public String getDisplayFormat() {
			return displayFormat;
		}

		public void setDisplayFormat(String displayFormat) {
			this.displayFormat = displayFormat;
		}

	}

	public String getConfigFilePath() {
		return configFilePath;
	}

	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}