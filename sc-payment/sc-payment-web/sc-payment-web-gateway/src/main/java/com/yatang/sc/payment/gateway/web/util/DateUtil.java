package com.yatang.sc.payment.gateway.web.util;


import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @描述: 日期帮助类.
 * @作者: huangjianjun
 * @创建时间: 2017年5月19日-下午2:26:38 .
 * @版本: 1.0 .
 * @param <T>
 */
public class DateUtil {
	/**
	 * 提醒时间间隔
	 * 单位：毫秒
	 * */
	public static long TIME_INTERVAL = 3600000;
	/**
	 * 毫秒转换分钟公式
	 * 单位分钟
	 * */
	public static long CONVERSION_FORMULA_MINUTE =60000;
	/**
	 * 日期格式
	 * 格式yyyy-MM-dd HH:mm
	 * */
	public final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 
	 /** 短日期格式
	  * yyyy-MM-dd **/
	 public static String FORMAT_DATE = "yyyy-MM-dd";
	 
	 /** 长日期格式 
	  * yyyy-MM-dd HH:mm:ss**/
	 public static String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";
	
	 /**
	  * 将日期格式的字符串转换为长整型
	  * @author huqw
	  * @date 2016年7月22日下午2:59:57
	  * @param date
	  * @param format
	  * @return
	  */
	 public static long convert2long(String date) {
		 try {
			 if (StringUtils.isEmpty(date)) {
				 SimpleDateFormat sf = new SimpleDateFormat(FORMAT_TIME);
				 return sf.parse(date).getTime()/1000;
			 }
		 } catch (ParseException e) {
			 e.printStackTrace();
		 }
		 return 0l;
	 }
	 
	 /**
	  * 将长整型数字转换为日期格式的字符串
	  * @author huqw
	  * @date 2016年7月22日下午3:04:00
	  * @param time
	  * @return
	  */
	 public static String convert2String(long time) {
		 if (time > 0l) {
			 SimpleDateFormat sf = new SimpleDateFormat(FORMAT_TIME);
			 Date date = new Date(time*1000);
			 return sf.format(date);
		 }
		 return "";
	 }
	 
	 /**
	  * 获取当前系统时间
	  * @author huqw
	  * @date 2016年7月22日下午2:59:28
	  * @return
	  */
	 public static long curTimeMillis() {
		 return System.currentTimeMillis()/1000;
	 }

	/**
	 * 时间进行四舍五入。 发单时间在0分-30分之间，系统设置为30分； 发单时间在31分-59分，系统设置为0分，增加1小时。 天数不增加，系统需要
	 * 
	 * @param time
	 *            原始时间
	 * @return time 取整后的时间
	 * @throws ParseException
	 */
	public static Date DateInt(Date time) throws ParseException {
		String timestr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
		String timeMM = timestr.substring(14, 16);
		// 如果是整点时间，则不需要需要进行四舍五入
		if (timeMM.equals("00")) {
			return time;
		} else if (Integer.parseInt(timeMM) > 0 && Integer.parseInt(timeMM) < 30  ) {
			timeMM = "30";
			timestr = timestr.substring(0, 14) + timeMM;
		} else {
			timeMM = "00";
			int hour = Integer.parseInt(timestr.substring(11, 13));
			
			if (hour == 23) {
				timestr = timestr.substring(0, 11) + "00:00";
			// System.out.println("time>>>>"+time);
				try {
					return getNextDay(df.parse(timestr));
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}else {
				timestr = timestr.substring(0, 11) + String.valueOf(hour + 1) + ":" + timeMM;
			} 
		}
		return df.parse(timestr);

	}

	/**
	 * 根据输入时间增加一天，显示日期
	 * 
	 * @param 输入时间
	 * @return 增加一天后时间
	 **/
	public static Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +1);// +1今天的时间加一天
		date = calendar.getTime();
		return date;
	}

	/**
	 * 根据输入时间和小时增加指定小时，显示日期
	 * 
	 * @param 输入时间
	 * @return 增加指定小时后时间
	 **/
	public static Date getNextHour(Date date, Integer hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, +hour);// +1今天的时间加一天
		date = calendar.getTime();
		return date;
	}

	/**
	 * 订单的时间超时检测，包含订单超时、提货超时、安装超时
	 * 
	 * @param systemTime
	 *            系统时间
	 * @param createTime
	 *            订单创建时间
	 * @param period
	 *            有效期（单位小时）
	 * @return ture/false
	 */
	public static boolean dateCompare(Date systemTime, Date createTime, Integer period) {
		boolean result = false;
		try {
			//System.out.println("sysTime------------------" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(systemTime));
			// 数据库系统时间
			Date sysTime = DateInt(systemTime);
			//System.out.println("sysTime>>>" + sysTime);
			// 订单创建时间
			Date cTime = DateInt(createTime);
			// System.out.println("cTime>>>"+cTime);
			// 有效期时间，时间单位是小时，转换为毫秒
			Date timeOut = getNextHour(cTime, period);
			 //System.out.println("timeOut>>>"+timeOut);
			 //System.out.println("cti>>>"+cti);
			long sys = sysTime.getTime();
			 //System.out.println("sys>>>"+sys);
			long tme = timeOut.getTime();
			 //System.out.println("tme>>>"+tme);
			// 时间差
			long dispersion = Math.abs(sys - tme);
			// 消息发送条件必须满足dispersion小于或等于半小时和系统时间在超时时间之前
			if (dispersion > 0 && dispersion <= TIME_INTERVAL && sys <= tme  ) {

				System.out.println("满足消息通知条件");
				result = true;
			}
		} catch (Exception e) {

		}
		return result;
	}

	public static final String DATETIME_FORMAT = "yyyyMMddHHmmss";
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATETIME_FORMAT);
    public static final String DATE_FORMAT = "yyyyMMdd";
    public static final String SHOW_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SHOW_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 获取当前时间串，格式为：yyyy-MM-dd HH:mm:ss
     * @author lixuefeng
     * @date:2016年7月21日下午2:18:43
     * @return
     */
    public static final String getCurrDatetime() {
        return format(new Date(), SHOW_DATETIME_FORMAT);
    }

    /**
     * 获取当前日期串，格式为yyyymmdd
     * @author lixuefeng
     * @date:2016年7月21日下午2:18:49
     * @return
     */
    public static final String getCurrDate() {
        return format(new Date(), DATE_FORMAT);
    }

    public static final String getCurrDate(String formatString){
    	return format(new Date(), formatString);
    }
    
    /**
     * 日期格式化
     * @author lixuefeng
     * @date:2016年7月21日下午2:27:25
     * @param date
     * @param formatStr
     * @return
     */
    public static String format(Date date, String formatStr) {
    	if(null == date){
    		return null;
    	}
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(date);
    }
    
    /**
     * 格式化日期
     * @author lixuefeng
     * @date:2016年7月21日下午2:29:27
     * @param date yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String format(Date date){
    	if(null == date){
    		return null;
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat(SHOW_DATETIME_FORMAT);
    	return sdf.format(date);
    }
    
    /**
     * 格式化日期
     * @author lixuefeng
     * @date:2016年7月21日下午2:35:38
     * @param dateTime
     * @return
     */
    public static String format(Long dateTime){
    	if(null == dateTime){
    		return null;
    	}
    	if(dateTime.toString().length() == 10){
    		dateTime = dateTime*1000;
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat(SHOW_DATETIME_FORMAT);
    	return sdf.format(new Date(dateTime));
    }
    
    /**
     * 格式化日期
     * @author lixuefeng
     * @date:2016年7月21日下午2:35:38
     * @param dateTime
     * @return
     */
    public static String format(String fromatStr,Long dateTime){
    	if(null == dateTime){
    		return null;
    	}
    	if(dateTime.toString().length() == 10){
    		dateTime = dateTime*1000;
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat(fromatStr);
    	return sdf.format(new Date(dateTime));
    }



    
    /**
     * 取得日期是一年中的第几天
     * @author lixuefeng
     * @date:2016年7月21日下午2:26:26
     * @param date
     * @return
     */
    public static int getDayNumByYear(Date date){
    	 if(null == date){
     		throw new IllegalArgumentException("参数不能为空");
     	}
    	Calendar ca = Calendar.getInstance();
    	ca.setTime(date);
    	return ca.get(Calendar.DAY_OF_YEAR);
    }
    
    /**
     * 功能说明：获取当前时间的时间戳
     * @author lixuefeng
     * @date:2016年7月21日下午2:26:42
     * @return
     */
    public static int getNowTimeStamp(){
    	long nowLong = System.currentTimeMillis();
    	nowLong = nowLong/1000;
    	return (int)nowLong;
    }
	
	public static void main(String[] args) {
		// DateUtils utl=new DateUtils();
		System.out.println(DateUtil.TIME_INTERVAL / DateUtil.CONVERSION_FORMULA_MINUTE);
		String str = convert2String(1469252617);
		System.out.println(str);
//		try {
//			// System.out.println(DateUtils.getNextHour(df.parse("2016-02-29
//			// 18:30:00"), 1));
//			
//			int i = 0;
//			while (i < 10) {
//				Date test = df.parse("2016-02-29 23:00:00");
//				System.out.println(i);
//				test = getNextHour(test, i);
//				i++;
//				boolean result = DateUtils.dateCompare(test, df.parse("2016-02-29 18:12:00"), 13);
//
//			}
//
//			// System.out.println("result>>"+result);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
	}

}
