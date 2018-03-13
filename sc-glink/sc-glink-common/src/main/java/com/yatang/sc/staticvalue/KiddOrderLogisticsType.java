package com.yatang.sc.staticvalue;

/**
 * @描述: 第三方对接常量
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/11/8 17:57
 * @版本: v1.0
 */
public class KiddOrderLogisticsType {

	/***************** 公用 *******************/
	public static String	KIDD_XSCK			= "KIDDXSCK";		// kidd销售出库
	public static String	KIDD_CGTH			= "CGTH";			// CGTH=采购退货
	public static String	KIDD_CGRK			= "CGRK";			// CGTH=采购入库
	public static String	KIDD_CGTHCK			= "CGTHCK";			// CGTHCH=采购退货出库
	public static String	KIDD_CANCELED		= "CANCELED";		// 取消成功
	public static String	KIDD_CANCELEDFAIL	= "CANCELEDFAIL";	// 取消失败
	public static String	KIDD_SIGNED			= "SIGNED";			// 签收
	public static String	KIDD_THRK			= "THRK";			// 退货入库
	public static String	KIDD_HHRK			= "HHRK";			// 换货入库
	public static String	KIDD_FULFILLED		= "FULFILLED";		// 完成状态
	public static String	KIDD_READY			= "READY";			// 待提货
	public static String	KIDD_DELIVERED		= "DELIVERED";		// 完全发货
	public static String	KIDD_HHCK			= "HHCK";			// 换货出库
    public static String   KIDD_PARTDELIVERED  = "PARTDELIVERED";//采购退货状态（）
	public static String	KIDD_OTHER			= "OTHER";			// 其他
	public static String	KIDD_ACCEPT			= "ACCEPT";			// 接收
	public static String	KIDD_JYCK			= "JYCK";			// 一般交易出库单

	public static String   KIDD_ORDER_STATUS_NOTIFY="KiddOrderStatusNotify";// kidd 销售订单通用
	/******************** 心怡 *******************/
	public static String	XY_PTCK				= "PTCK";			// 心怡PTCK=普通出库单

	public static String	XY_SIGN				= "SIGN";			// 心怡签收

	/******************** 际链 *******************/
	public static String	JL_FHCK				= "FHCK";			// 心怡FHCK=销售退货单取消

}
