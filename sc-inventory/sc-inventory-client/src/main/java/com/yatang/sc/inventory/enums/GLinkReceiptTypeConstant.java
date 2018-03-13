package com.yatang.sc.inventory.enums;

/**
 * 际链调整单类型
 */

public class GLinkReceiptTypeConstant {

    private GLinkReceiptTypeConstant() {
    }

    public static final String RECEIPT_TYPE_WLDS = "WLDS";//物流丢失
    public static final String RECEIPT_TYPE_CKBY = "CKBY";//仓库报溢
    public static final String RECEIPT_TYPE_CKBS = "CKBS";//仓库报损
    public static final String RECEIPT_TYPE_YWTZ = "YWTZ";//业务调增
    public static final String RECEIPT_TYPE_YWTJ = "YWTJ";//业务调减
    public static final String RECEIPT_TYPE_CKTBZ = "CKTBZ";//仓库同步调增
    public static final String RECEIPT_TYPE_CKTBJ = "CKTBJ";//仓库同步调减
}
