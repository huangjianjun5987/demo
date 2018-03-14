package com.yatang.sc.web.common.excel.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yatang.sc.web.common.util.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Excel导入
 *
 * Created by JiangHuaJun on 2017/12/5.
 */
@Slf4j
public class ExcelImport{

    public static final String IMPORT_CONFIG_FILE_PATH = "config/import/importExcel.json";// 导入配置信息

    private static final Map<String, List<Map<String, Object>>> config;

    /**
     * 初始化读取映射配置
     */
    static {
        config = loadImportConfigFile();
    }

    public static <T> List<T> excelImport(InputStream inputStream, String configName, Class<T> c) throws Exception {
        Workbook wb = WorkbookGen(inputStream);

        List<T> result = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>(); // Map<index, 表列名>
        Map<String, String> fieldMap = fieldMaping(configName); // Map<Excel列名, POJO字段名>
        Map<String, String> objectMap = new HashMap<>(); // Map<POJO字段名, 表数据>

        Sheet sheet = wb.getSheetAt(0);
        Row excelNameRow = sheet.getRow(0);
        // 读取表头列名
        for (int i = 0; excelNameRow.getCell(i) != null; i++){
            Cell cell = excelNameRow.getCell(i);
            map.put(i, cell.getStringCellValue());
        }
        // 加载表数据生成对象
        for (int i = 1; sheet.getRow(i) != null; i++){
            Row row = sheet.getRow(i);
            for (int j = 0; j < map.size(); j++){
                Cell cell = row.getCell(j);
                if (cell == null){
                    String fieldName = fieldMap.get(map.get(j));
                    objectMap.put(fieldName, null);
                    continue;
                }
                if (cell.getCellTypeEnum() != CellType.STRING){
                    cell.setCellType(CellType.STRING);
                }
                String fieldName = fieldMap.get(map.get(j));
                objectMap.put(fieldName, cell.getStringCellValue());
                objectMap.put("lineNum", String.valueOf(row.getRowNum() + 1));   // 行号
            }
            // 判断是否每一个cell都是null 或 ""
            Map<String, String> valmap = new HashMap<>(objectMap);
            valmap.remove("lineNum");
            List<String> values = Lists.newArrayList(valmap.values());
            boolean allEmpty = true;
            for (String val: values){
                if (!(StringUtils.isBlank(val) || "lineNum".equals(val))){
                    allEmpty = false;
                    break;
                }
            }
            if (!allEmpty) {
                result.add(JSONUtils.toObject(objectMap, c));
            }
        }
        return result;
    }

    /**
     * 通过HttpServletRequest获取文件流生成Workbook
     * @param inputStream
     * @return
     */
    private static Workbook WorkbookGen(InputStream inputStream) throws Exception{
        try {
            return WorkbookFactory.create(inputStream);
        } catch (IOException | InvalidFormatException e) {
            log.error("上传Excel文件读取失败:\n{}", ExceptionUtils.getFullStackTrace(e));
            throw new Exception("上传Excel文件读取失败", e);
        }
    }

    /**
     * 取出配置文件中配置的Excel字段名与其映射的POJO字段名
     * Map<Excel字段名, POJO字段名>
     * @return
     */
    private static Map<String, String> fieldMaping(String configName){
        if (config == null){
            throw new RuntimeException("Excel导入配置文件异常, 无法导入!");
        }
        Map<String, String> conf = new HashMap<>();
        List<Map<String, Object>> list = config.get(configName);
        for (Map<String, Object> fieldConf: list){
            conf.put(String.valueOf(fieldConf.get("displayName")).trim(),
                    String.valueOf(fieldConf.get("name")).trim());
        }
        return conf;
    }

    /**
     * 获取配置文件数据,用于填充excelColList
     *
     * @throws IOException
     */
    private static Map<String, List<Map<String, Object>>> loadImportConfigFile() {
        try {
            InputStream in = ExcelImport.class.getClassLoader().getResourceAsStream(IMPORT_CONFIG_FILE_PATH);
            return new ObjectMapper().readValue(in, new TypeReference<Map<String, List<Map<String, Object>>>>(){});
        } catch (IOException e){
            log.error("Excel导入配置文件读取失败:\n{}", ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }

}
