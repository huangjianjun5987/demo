package com.yatang.sc.common.utils;

import java.util.Properties;

/**
 * Created by qiugang on 11/2/2017.
 */
public class AreaToWarehouseConfig {

    private static Properties areaToWarehouse = new Properties();

    private static Properties warehouseToBranchCompanyId = new Properties();

    static {
        areaToWarehouse.put("四川", "901200411");
        areaToWarehouse.put("四川省","901200411");
        areaToWarehouse.put("重庆市","902200421");
        areaToWarehouse.put("重庆","902200421");
        areaToWarehouse.put("湖北省","904200501");
        areaToWarehouse.put("湖北","904200501");
        areaToWarehouse.put("湖南省","903200451");
        areaToWarehouse.put("湖南","903200451");

        areaToWarehouse.put("广东", "905200461");
        areaToWarehouse.put("广东省","905200461");
        areaToWarehouse.put("山东","906200471");
        areaToWarehouse.put("山东省","906200471");
        areaToWarehouse.put("浙江","908200551");
        areaToWarehouse.put("浙江省","908200551");
        areaToWarehouse.put("河北","909200441");
        areaToWarehouse.put("河北省","909200441");

        areaToWarehouse.put("河南", "911200561");
        areaToWarehouse.put("河南省","911200561");
        areaToWarehouse.put("陕西", "912200521");
        areaToWarehouse.put("陕西省","912200521");
        areaToWarehouse.put("北京","907200431");
        areaToWarehouse.put("北京市","907200431");
        areaToWarehouse.put("江西","913200611");
        areaToWarehouse.put("江西省","913200611");


        warehouseToBranchCompanyId.put("901200411", "10000");
        warehouseToBranchCompanyId.put("902200421", "10005");
        warehouseToBranchCompanyId.put("904200501", "10014");
        warehouseToBranchCompanyId.put("903200451", "10004");

        warehouseToBranchCompanyId.put("905200461", "10021");
        warehouseToBranchCompanyId.put("906200471", "10007");
        warehouseToBranchCompanyId.put("908200551", "10020");
        warehouseToBranchCompanyId.put("909200441", "10008");

        warehouseToBranchCompanyId.put("911200561", "10012");
        warehouseToBranchCompanyId.put("912200521", "10010");
        warehouseToBranchCompanyId.put("907200431", "10006");
        warehouseToBranchCompanyId.put("913200611", "100045");

        areaToWarehouse.put("香港特别行政区","WLL");
        areaToWarehouse.put("台湾特别行政区","YT901");
        areaToWarehouse.put("台湾","YT901");
        areaToWarehouse.put("西藏自治区","TJXQ");

        warehouseToBranchCompanyId.put("WLL", "10004");
        warehouseToBranchCompanyId.put("YT901", "10000");
        warehouseToBranchCompanyId.put("TJXQ", "10003");

    }

    public static String getWarehouseCode(String province){
        return areaToWarehouse.getProperty(province);
    }

    public static String getBranchCompanyId(String warehouseCode){
        return warehouseToBranchCompanyId.getProperty(warehouseCode);
    }


}
