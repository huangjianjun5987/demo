package com.yatang.sc.common.staticvalue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileType {

    public static final Map<String,List<String>> fileTypeMap = new HashMap<>();
    static {
        List<String> excelTypeList = new ArrayList<>();
        excelTypeList.add("application/vnd.ms-excel");
        excelTypeList.add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        excelTypeList.add("application/vnd.openxmlformats-officedocument.spreadsheetml.template");
        excelTypeList.add("application/vnd.ms-excel.sheet.macroEnabled.12");
        excelTypeList.add("application/vnd.ms-excel.template.macroEnabled.12");
        excelTypeList.add("application/vnd.ms-excel.addin.macroEnabled.12");
        excelTypeList.add("application/vnd.ms-excel.sheet.binary.macroEnabled.12");

        fileTypeMap.put("excel",excelTypeList);
    }
}
