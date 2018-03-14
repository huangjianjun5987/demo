package com.yatang.sc.payment.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    /**
     * 由java.util.Date到java.sql.Date的类型转换
     *
     * @param date
     * @return Date
     */
    public static java.sql.Date getSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 由java.util.Date到java.sql.Timestamp的类型转换
     *
     * @param date
     * @return Timestamp
     */
    public static java.sql.Timestamp getSqlTimestamp(Date date) {
        return new java.sql.Timestamp(date.getTime());
    }

    public static Date nowDate() {
        Calendar calendar = Calendar.getInstance();
        return getSqlDate(calendar.getTime());
    }

    /**
     * 获得某一日期的后一天
     *
     * @param date
     * @return Date
     */
    public static Date getNextDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day + 1);
        return getSqlDate(calendar.getTime());
    }

    // 获得某一日期的后n天.
    public static Date getNextNDate(Date begin, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(begin);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day + n);
        return calendar.getTime();
    }

    /**
     * 获取当前日期的前n 天
     *
     * @param begin
     * @param n
     * @return
     */
    public static Date getPreNDate(Date begin, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(begin);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day - n);
        return calendar.getTime();
    }

    /**
     * 获得某一日期的前一天
     *
     * @param date
     * @return Date
     */
    public static Date getPreviousDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day - 1);
        return getSqlDate(calendar.getTime());
    }

    /**
     * 获得某年某月第一天的日期
     *
     * @param year
     * @param month
     * @return Date
     */
    public static Date getFirstDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        return getSqlDate(calendar.getTime());
    }

    /**
     * 获得某年某月最后一天的日期
     *
     * @param year
     * @param month
     * @return Date
     */
    public static Date getLastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        return getPreviousDate(getSqlDate(calendar.getTime()));
    }

    /**
     * 获得某一日期的后n月的日期
     *
     * @return Date
     */
    public static Date getDayNextNMonth(int year, int month, int day, int nMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month + nMonth - 1);
        calendar.set(Calendar.DATE, day);
        return getSqlDate(calendar.getTime());
    }

    /**
     * 由年月日构建java.sql.Date类型
     *
     * @param year
     * @param month
     * @param date
     * @return Date
     */
    public static Date buildDate(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date);
        return getSqlDate(calendar.getTime());
    }

    /**
     * 取得某月的天数
     *
     * @param year
     * @param month
     * @return int
     */
    public static int getDayCountOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 0);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获得某年某季度的最后一天的日期
     *
     * @param year
     * @param quarter
     * @return Date
     */
    public static Date getLastDayOfQuarter(int year, int quarter) {
        int month = 0;
        if (quarter > 4) {
            return null;
        } else {
            month = quarter * 3;
        }
        return getLastDayOfMonth(year, month);
    }

    /**
     * 获得某年某季度的第一天的日期
     *
     * @param year
     * @param quarter
     * @return Date
     */
    public static Date getFirstDayOfQuarter(int year, int quarter) {
        int month = 0;
        if (quarter > 4) {
            return null;
        } else {
            month = (quarter - 1) * 3 + 1;
        }
        return getFirstDayOfMonth(year, month);
    }

    /**
     * 获得某年的第一天的日期
     *
     * @param year
     * @return Date
     */
    public static Date getFirstDayOfYear(int year) {
        return getFirstDayOfMonth(year, 1);
    }

    /**
     * 获得某年的最后一天的日期
     *
     * @param year
     * @return Date
     */
    public static Date getLastDayOfYear(int year) {
        return getLastDayOfMonth(year, 12);
    }

    /**
     * 功能：根据输入的日期字符串和日期格式转换为DATE类型 参数1：日期字符串 如1982-12-24 参数X：字符串格式 如yyyy-MM-dd 添加人：孙延来
     */
    public static Date toDate(String dateStr, String formatStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 功能：将Date类型转换为自定义格式的字符串样式 参数1：date 参数X：格式 如yyyy-MM-dd
     */
    public static String getFormatDate(Date date, String format) {
        String fdate = "";
        format = format != null ? format : "yyyy-MM-dd hh:mm:ss";
        try {
            SimpleDateFormat f = new SimpleDateFormat(format);
            fdate = f.format(date);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fdate;
    }

    /**
     * 功能：将Date类型转换为自定义格式Date
     */
    public static Date getsimpleDate(Date date, String format) {
        format = format != null ? format : "yyyy-MM-dd hh:mm:ss";
        try {
            SimpleDateFormat f = new SimpleDateFormat(format);
            return f.parse(f.format(date));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 功能：通过输入的日期算出该人的年龄 参数1：生日串 参数X：格式 如yyyy-MM-dd 添加人：孙延来
     */
    public static int getPeopleAge(String date, String formatStr) {
        // Calendar.YEAR
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) - Integer.parseInt(date.substring(formatStr.indexOf("y"), formatStr.lastIndexOf("y") + 1));
    }

    /*
     * 计算两个日期的相差天数
     */
    public static int dataCount(String from_Date, String to_Date) {
        int dataCount = 0;
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long data_mm;
            Date from_D = s.parse(from_Date);
            Date to_D = s.parse(to_Date);

            data_mm = from_D.getTime() - to_D.getTime();
            dataCount = (int) (data_mm / 1000 / 60 / 60 / 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataCount;
    }

    /*
     * 计算两个日期的相差天数
     */
    public static int dataCount(Date from_Date, Date to_Date) {
        try {

            long data_mm = from_Date.getTime() - to_Date.getTime();
            return (int) (data_mm / 1000 / 60 / 60 / 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*
     * 检查日期字符串有效性 日期字符串为10位YYYY-MM-DD
     */
    public static boolean checkDate(String str) {
        int year;
        int month;
        int day;
        int days[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (null == str || "".equals(str)) {
            return true;
        }

        if (!str.matches("\\d{4}-[01]\\d-[0123]\\d")) {
            return false;
        }
        year = Integer.parseInt(str.substring(0, 4));
        month = Integer.parseInt(str.substring(5, 7));
        day = Integer.parseInt(str.substring(8, 10));

        // Judge leap year.
        if (((0 == year % 4) && (0 != year % 100)) || (0 == year % 400)) {
            days[1]++;
        }
        // process month
        if (month < 1 || month > 12) {
            return false;
        }
        // process day
        if (day < 1 || day > days[month - 1]) {
            return false;
        }
        return true;
    }

    public static Date convertToDate(XMLGregorianCalendar cal) throws Exception {
        GregorianCalendar ca = cal.toGregorianCalendar();
        return ca.getTime();
    }

    public static XMLGregorianCalendar convertToCal(Date date) throws DatatypeConfigurationException {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException e) {
            throw new DatatypeConfigurationException();
        }
        return gc;

    }

    public static String getDateNow() {
        Date now = new Date();
        DateFormat d1 = DateFormat.getDateInstance();
        String str1 = d1.format(now);
        return str1;
    }

    public static Long getDateInSeconds(Date date) {
        if (date != null) {
            long longDate = date.getTime();
            return (long) (longDate / 1000);
        }
        return null;
    }

    public static boolean isDatesEqual(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return true;
        }
        if (date1 != null && date2 != null && date1.compareTo(date2) == 0) {
            return true;
        }
        return false;
    }

    /**
     * Description:格式化日期,String字符串转化为Date
     *
     * @param date
     * @param dtFormat 例如:yyyy-MM-dd HH:mm:ss yyyyMMdd
     * @return
     * @author 孙钰佳 @since：2007-7-10 上午11:24:00
     */
    public static String fmtDateToStr(Date date, String dtFormat) {
        if (date == null)
            return "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dtFormat);
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Description:按指定格式 格式化日期
     *
     * @param date
     * @param dtFormat
     * @return
     * @author 孙钰佳 @since：2007-12-10 上午11:25:07
     */
    public static Date fmtStrToDate(String date, String dtFormat) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dtFormat);
            return dateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
