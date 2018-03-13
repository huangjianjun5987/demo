package com.yatang.sc.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>@description:日期相关的工具类</p>
 *
 * @author: shuangyang
 * @date: 17-10-21 下午11:21
 */
public class DateUtil {


    public static Integer TIME_UNIT = 1;//默认时间单元

    public static String DATE_STRING_YYYY_MM_DD_HMS = "yyyy-MM-dd HH:mm:ss";

    public static String DATE_STRING_YYYY_MM_DD_00 = "yyyy-MM-dd 00:00:00";
    public static String DATE_STRING_YYYY_MM_DD_59 = "yyyy-MM-dd 23:59:59";
    public static String DATE_STRING_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 判断是否到达截止时间
     *
     * @param createDate
     * @param day
     * @return
     */
    public static boolean isDeadLine(Date createDate, Integer day) {

        Calendar deadLine = addDay2Calendar(createDate, day * TIME_UNIT);//获取累加时间
        Calendar currentCr = getCalendar(new Date());//获取当前时间
        return compareCalendar(currentCr, deadLine);
    }

    /**
     * 时间转换
     *
     * @param date
     * @return
     */
    public static Calendar getCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    /**
     * 累加时间
     *
     * @param date
     * @param dayUnit
     * @return
     */
    public static Calendar addDay2Calendar(Date date, Integer dayUnit) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, dayUnit);// 今天+1天
        return c;
    }

    /**
     * 减去时间
     *
     * @param date
     * @param dayUnit
     * @return
     */
    public static Calendar subDay2Calendar(Date date, Integer dayUnit) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -dayUnit);
        return c;
    }

    /**
     * 截止日期小于等于当前日期(true),else(false)
     *
     * @param current  当前日期
     * @param deadLine 截止日期
     * @return
     */
    public static boolean compareCalendar(Calendar current, Calendar deadLine) {
        int currentYear = current.get(Calendar.YEAR);
        int deadLineYear = deadLine.get(Calendar.YEAR);
        if (deadLineYear > currentYear) {//
            return true;
        } else {
            int currentMonth = current.get(Calendar.MONTH);
            int deadLineMonth = deadLine.get(Calendar.MONTH);
            if (deadLineMonth > currentMonth) {
                return true;
            } else {
                int currentDay = current.get(Calendar.DAY_OF_MONTH);
                int deadLineDay = deadLine.get(Calendar.DAY_OF_MONTH);
                if (deadLineDay >= currentDay) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @Description: 按yyyy-MM-dd格式化日期
     */
    public static String formatDateYMD(Date date) {
        if (date == null)
            return "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_STRING_YYYY_MM_DD);
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @Description: 按yyyy-MM-dd HH:mm:ss格式化日期
     */
    public static String formatDateYMDhms(Date date) {
        if (date == null)
            return "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_STRING_YYYY_MM_DD_HMS);
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @Description: 转换为yyyy-MM-dd HH:mm:ss格式的日期
     */
    public static Date parseDateYMDhms(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_STRING_YYYY_MM_DD_HMS);
            return dateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description: 按yyyy-MM-dd 00:00:00格式化日期
     */
    public static String formatDateYMD00(Date date) {
        if (date == null)
            return "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_STRING_YYYY_MM_DD_00);
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @Description: 转换为yyyy-MM-dd 00:00:00格式的日期
     */
    public static Date parseDateYMD00(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_STRING_YYYY_MM_DD_00);
            return dateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description: 按yyyy-MM-dd 23:59:59格式化日期
     */
    public static String formatDateYMD59(Date date) {
        if (date == null)
            return "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_STRING_YYYY_MM_DD_59);
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @Description: 转换为yyyy-MM-dd 23:59:59格式的日期
     */
    public static Date parseDateYMD59(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_STRING_YYYY_MM_DD_59);
            return dateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     *  比较第一个日期是否大于或者等于第二个日期
     * @param dateOne 第一个日期
     * @param dateTwo 第二个日期
     * @author yangshuang
     * @return
     */
    public static Boolean compareDate(Date dateOne,Date dateTwo){
        if(null == dateOne || null == dateTwo){
            return false;
        }
        Calendar cal1 =getCalendarYMD(dateOne);
        Calendar cal2 =getCalendarYMD(dateTwo);
        return  cal1.getTime().getTime()>=cal2.getTime().getTime();


    }

    /**
     *  比较第一个日期大于第二个日期
     * @param dateOne 第一个日期
     * @param other 第二个日期
     * @author yangshuang
     * @return
     */
    public static Boolean compareBiggerThanOther(Date dateOne,Date other){
        if(null == dateOne || null == other){
            return false;
        }
        Calendar cal1 =getCalendarYMD(dateOne);
        Calendar cal2 =getCalendarYMD(other);
        return  cal1.getTime().getTime()>cal2.getTime().getTime();


    }



    public static  Calendar getCalendarYMD(Date date){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        return cal1;

    }





}
