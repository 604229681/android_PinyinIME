package com.android.inputmethod.pinyin.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <h3>日期工具类</h3> <br>
 * <h5>主要实现了日期的常用操作</h5>
 *
 * @author HuangBing
 * @mail huangb@ivollo.com
 * @date 2015-7-28
 */
@SuppressLint("SimpleDateFormat")
public final class DateUtil {

    /**
     * yyyy-MM-dd HH:mm:ss字符串
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy/MM/dd HH字符串
     */
    public static final String DEFAULT_DATE_HOUR = "yyyy/MM/dd HH";

    /**
     * yyyyMMddHH字符串
     */
    public static final String DEFAULT_DATE_HOUR_FORMAT = "yyyyMMddHH";

    /**
     * yyyyMMddHHmmss字符串
     */
    public static final String DEFAULT_DATE_SECOND_FORMAT = "yyyyMMddHHmmss";

    /**
     * yyyy-MM-dd字符串
     */
    public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";

    /**
     * yyyyMMdd字符串
     */
    public static final String DEFAULT_DATE = "yyyyMMdd";

    /**
     * yyyyMMdd HHmmss字符串
     */
    public static final String DEFAULT_TIME = "yyyyMMdd HHmmss";

    /**
     * yyyy/MM/dd字符串
     */
    public static final String DEFAULT_DATE_SLASH = "yyyy/MM/dd";

    /**
     * HH:mm:ss字符串
     */
    public static final String DEFAULT_FORMAT_TIME = "HH:mm:ss";

    /**
     * HH:mm字符串
     */
    public static final String DEFAULT_FORMAT_HOUR = "HH:mm";

    /**
     * yyyy-MM-dd HH:mm:ss格式
     */
    public static SimpleDateFormat defaultDateTimeFormat = new SimpleDateFormat(
            DEFAULT_DATE_TIME_FORMAT);


    /**
     * yyyy/MM/dd HH格式
     */
    public static SimpleDateFormat defaultDateHour = new SimpleDateFormat(
            DEFAULT_DATE_HOUR);


    /**
     * yyyyMMddHH格式
     */
    public static SimpleDateFormat defaultDateHourFormat = new SimpleDateFormat(
            DEFAULT_DATE_HOUR_FORMAT);

    /**
     * yyyyMMddHHmmss格式
     */
    public static SimpleDateFormat defaultDateSecondFormat = new SimpleDateFormat(
            DEFAULT_DATE_SECOND_FORMAT);


    /**
     * yyyyMMdd格式
     */
    public static SimpleDateFormat defaultDateDay = new SimpleDateFormat(
            DEFAULT_DATE);


    /**
     * yyyyMMdd HHmmss格式
     */
    public static SimpleDateFormat defaultTime = new SimpleDateFormat(
            DEFAULT_TIME);


    /**
     * yyyy-MM-dd格式
     */
    public static SimpleDateFormat defaultDateFormat = new SimpleDateFormat(
            DEFAULT_FORMAT_DATE);

    /**
     * yyyyMMdd格式
     */
    public static SimpleDateFormat defaultDate = new SimpleDateFormat(
            DEFAULT_DATE);

    /**
     * yyyy/MM/dd格式
     */
    public static SimpleDateFormat defaultSlashDate = new SimpleDateFormat(
            DEFAULT_DATE_SLASH);

    /**
     * HH:mm:ss格式
     */
    public static SimpleDateFormat defaultTimeFormat = new SimpleDateFormat(
            DEFAULT_FORMAT_TIME);

    /**
     * HH:mm格式
     */
    public static SimpleDateFormat defaultHourFormat = new SimpleDateFormat(DEFAULT_FORMAT_HOUR);

    private DateUtil() {
    }

    /**
     * 将long时间转成yyyy-MM-dd HH:mm:ss字符串<br>
     *
     * @param timeInMillis 时间long值
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeFromMillis(long timeInMillis) {
        return getDateTimeFormat(new Date(timeInMillis));
    }


    /**
     * 将long时间转成yyyy-MM-dd字符串<br>
     *
     * @param timeInMillis
     * @return yyyy-MM-dd
     */
    public static String getDateFromMillis(long timeInMillis) {
        return getDateFormat(new Date(timeInMillis));
    }

    /**
     * 将long时间转成HH:mm:ss字符串<br>
     *
     * @param timeInMillis
     * @return HH:mm
     */
    public static String getDateHourFromMillis(long timeInMillis) {
        return getHourFormat(new Date(timeInMillis));
    }

    /**
     * 将date转成yyyy-MM-dd HH:mm:ss字符串 <br>
     *
     * @param date Date对象
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeFormat(Date date) {
        return dateSimpleFormat(date, defaultDateTimeFormat);
    }


    /**
     * 将年月日的int转成yyyy-MM-dd的字符串
     *
     * @param year  年
     * @param month 月 1-12
     * @param day   日 注：月表示Calendar的月，比实际小1 对输入项未做判断
     */
    public static String getDateFormat(int year, int month, int day) {
        return getDateFormat(getDate(year, month, day));
    }

    /**
     * 将date转成yyyy-MM-dd字符串<br>
     *
     * @param date Date对象
     * @return yyyy-MM-dd
     */
    public static String getDateFormat(Date date) {
        return dateSimpleFormat(date, defaultDateFormat);
    }


    /**
     * 将date转成yyyy/MM/dd字符串<br>
     *
     * @param date Date对象
     * @return yyyy/MM/dd
     */
    public static String getSlashDateFormat(Date date) {
        return dateSimpleFormat(date, defaultSlashDate);
    }

    /**
     * 获得HH:mm:ss的时间
     *
     * @param date
     * @return
     */
    public static String getTimeFormat(Date date) {
        return dateSimpleFormat(date, defaultTimeFormat);
    }

    /**
     * 获得HH:mm的时间
     *
     * @param date
     * @return
     */
    public static String getHourFormat(Date date) {
        return dateSimpleFormat(date, defaultHourFormat);
    }


    /**
     * 格式化日期显示格式
     *
     * @param sdate  原始日期格式 "yyyy-MM-dd"
     * @param format 格式化后日期格式
     * @return 格式化后的日期显示
     */
    public static String dateFormat(String sdate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        java.sql.Date date = java.sql.Date.valueOf(sdate);
        return dateSimpleFormat(date, formatter);
    }

    /**
     * 格式化日期显示格式
     *
     * @param date   Date对象
     * @param format 格式化后日期格式
     * @return 格式化后的日期显示
     */
    public static String dateFormat(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return dateSimpleFormat(date, formatter);
    }

    /**
     * 将date转成字符串
     *
     * @param date   Date
     * @param format SimpleDateFormat <br>
     *               注： SimpleDateFormat为空时，采用默认的yyyy-MM-dd HH:mm:ss格式
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String dateSimpleFormat(Date date, SimpleDateFormat format) {
        if (format == null)
            format = defaultDateTimeFormat;
        return (date == null ? "" : format.format(date.getTime()));
    }

    /**
     * 将"yyyy-MM-dd HH:mm:ss" 格式的字符串转成Date
     *
     * @param strDate 时间字符串
     * @return Date
     */
    public static Date getDateByDateTimeFormat(String strDate) {
        return getDateByFormat(strDate, defaultDateTimeFormat);
    }

    /**
     * 将"yyyy-MM-dd" 格式的字符串转成Date
     *
     * @param strDate 将"yyyy-MM-dd " 格式的字符串
     * @return Date
     */
    public static Date getDateByDateFormat(String strDate) {
        return getDateByFormat(strDate, defaultDateFormat);
    }

    /**
     * "yyyy-MM-dd HH:mm:ss" 转化long mills
     *
     * @param strDate 将"yyyy-MM-dd HH:mm:ss" 格式的字符串
     * @return
     */
    public synchronized static long getMilliseByDateTimeFormat(String strDate) {
        return getDateByFormat(strDate, defaultDateTimeFormat).getTime();
    }

    /**
     * "yyyy-MM-dd" 转化long mills
     *
     * @param strDate 将"yyyy-MM-dd" 格式的字符串
     * @return
     */
    public static long getMilliseByDateFormat(String strDate) {
        return getDateByFormat(strDate, defaultDateFormat).getTime();
    }

    /**
     * "yyyyMMddHH" 转化long mills
     *
     * @param strDate 将"yyyyMMddHH" 格式的字符串
     * @return
     */
    public static long getMilliseByDateHourFormat(String strDate) {
        return getDateByFormat(strDate, defaultDateHourFormat).getTime();
    }

    /**
     * "yyyyMMddHHmmss" 转化long mills
     *
     * @param strDate 将"yyyyMMddHHmmss" 格式的字符串
     * @return
     */
    public static long getMilliseByDateSecondFormat(String strDate) {
        return getDateByFormat(strDate, defaultDateSecondFormat).getTime();
    }

    /**
     * "yyyyMMdd" 转化long mills
     *
     * @param strDate 将"yyyyMMdd" 格式的字符串
     * @return
     */
    public static long getMilliseByDateDayFormat(String strDate) {
        return getDateByFormat(strDate, defaultDateDay).getTime();
    }

    /**
     * "yyyyMMdd HHmmss" 转化long mills
     *
     * @param strDate 将"yyyyMMdd HHmmss" 格式的字符串
     * @return
     */
    public static long getMilliseByTimeFormat(String strDate) {
        return getDateByFormat(strDate, defaultTime).getTime();
    }


    /**
     * 将指定格式的时间字符串转成Date对象
     *
     * @param strDate 时间字符串
     * @param format  格式化字符串
     * @return Date
     */
    public static Date getDateByFormat(String strDate, String format) {
        return getDateByFormat(strDate, new SimpleDateFormat(format));
    }

    /**
     * 将String字符串按照一定格式转成Date<br>
     * 注： SimpleDateFormat为空时，采用默认的yyyy-MM-dd HH:mm:ss格式
     *
     * @param strDate 时间字符串
     * @param format  SimpleDateFormat对象
     * @throws ParseException 日期格式转换出错
     */
    private  synchronized static Date getDateByFormat(String strDate, SimpleDateFormat format) {
        if (format == null)
            format = defaultDateTimeFormat;
        try {
            return format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将年月日的int转成date
     *
     * @param year  年
     * @param month 月 1-12
     * @param day   日 注：月表示Calendar的月，比实际小1
     */
    public static Date getDate(int year, int month, int day) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(year, month - 1, day);
        return mCalendar.getTime();
    }

    /**
     * 求两个日期相差天数
     *
     * @param strat 起始日期，格式yyyy-MM-dd
     * @param end   终止日期，格式yyyy-MM-dd
     * @return 两个日期相差天数
     */
    public static long getIntervalDays(String strat, String end) {
        return ((java.sql.Date.valueOf(end)).getTime() - (java.sql.Date
                .valueOf(strat)).getTime()) / (3600 * 24 * 1000);
    }

    /**
     * 获得当前年份
     *
     * @return year(int)
     */
    public static int getCurrentYear() {
        Calendar mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.YEAR);
    }

    /**
     * 获得当前月份
     *
     * @return month(int) 1-12
     */
    public static int getCurrentMonth() {
        Calendar mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.MONTH);
    }

    /**
     * 获得当月几号
     *
     * @return day(int)
     */
    public static int getDayOfMonth() {
        Calendar mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得今天的日期(格式：yyyy-MM-dd)
     *
     * @return yyyy-MM-dd
     */
    public static String getToday() {
        Calendar mCalendar = Calendar.getInstance();
        return getDateFormat(mCalendar.getTime());
    }

    /**
     * 获取日期时间(格式：yyyy-MM-dd HH:mm:ss)
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateTime() {
        Calendar mCalendar = Calendar.getInstance();
        return getDateTimeFormat(mCalendar.getTime());
    }

    /**
     * 获得昨天的日期(格式：yyyy/MM/dd)
     *
     * @return yyyy/MM/dd
     */
    public static String getYesterday() {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE, -1);
        return getSlashDateFormat(mCalendar.getTime());
    }

    /**
     * 获得前天的日期(格式：yyyy-MM-dd)
     *
     * @return yyyy-MM-dd
     */
    public static String getBeforeYesterday() {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE, -2);
        return getDateFormat(mCalendar.getTime());
    }

    /**
     * 获得几天之前或者几天之后的日期(格式：yyyy-MM-dd)
     *
     * @param diff 差值：正的往后推，负的往前推
     * @return
     */
    public static String getOtherDay(int diff) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE, diff);
        return getDateFormat(mCalendar.getTime());
    }

    /**
     * 取得给定日期加上一定天数后的日期对象.
     *
     * @param sDate  给定的日期对象
     * @param amount 需要添加的天数，如果是向前的天数，使用负数就可以.
     * @return Date 加上一定天数以后的Date对象.
     */
    public static String getCalcDateFormat(String sDate, int amount) {
        Date date = getCalcDate(getDateByDateFormat(sDate), amount);
        return getDateFormat(date);
    }

    /**
     * 取得给定日期加上一定天数后的日期对象.
     *
     * @param date   给定的日期对象
     * @param amount 需要添加的天数，如果是向前的天数，使用负数就可以.
     * @return Date 加上一定天数以后的Date对象.
     */
    public static Date getCalcDate(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, amount);
        return cal.getTime();
    }

    /**
     * 根据指定的年月日小时分秒，返回一个java.Util.Date对象。
     *
     * @param year      年
     * @param month     月 0-11
     * @param date      日
     * @param hourOfDay 小时 0-23
     * @param minute    分 0-59
     * @param second    秒 0-59
     * @return 一个Date对象
     */
    public static Date getDate(int year, int month, int date, int hourOfDay,
                               int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date, hourOfDay, minute, second);
        return cal.getTime();
    }

    /**
     * 获得年月日数据
     *
     * @param sDate yyyy-MM-dd格式
     * @return arr[0]:年， arr[1]:月 0-11 , arr[2]日
     */
    public static int[] getYearMonthAndDayFrom(String sDate) {
        return getYearMonthAndDayFromDate(getDateByDateFormat(sDate));
    }

    /**
     * 获得年月日数据
     *
     * @return arr[0]:年， arr[1]:月 0-11 , arr[2]日
     */
    public static int[] getYearMonthAndDayFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int[] arr = new int[3];
        arr[0] = calendar.get(Calendar.YEAR);
        arr[1] = calendar.get(Calendar.MONTH);
        arr[2] = calendar.get(Calendar.DAY_OF_MONTH);
        return arr;
    }

    /**
     * 将long时间转成yyyy/MM/dd HH字符串<br>
     *
     * @param timeInMillis
     * @return yyyy/MM/dd HH
     */
    public static String getHourFromMillis(long timeInMillis) {
        return dateSimpleFormat(new Date(timeInMillis), defaultDateHour);
    }


    /**
     * 将long时间转成yyyyMMdd字符串<br>
     *
     * @param timeInMillis
     * @return yyyyMMdd
     */
    public static String getDetaultDateFromMillis(long timeInMillis) {
        return dateSimpleFormat(new Date(timeInMillis), defaultDate);
    }


    /**
     * 将long时间转成yyyyMMddHH字符串<br>
     *
     * @param timeMinllis
     * @returnyyyyMMddHH
     */
    public static String getHourDateFromMillis(long timeMinllis) {
        return dateSimpleFormat(new Date(timeMinllis), defaultDateHourFormat);
    }


    /**
     * 将long时间转成yyyy/MM/dd字符串<br>
     *
     * @param timeInMillis
     * @return yyyy/MM/dd
     */
    public static String getDetaultFormatDateFromMillis(long timeInMillis) {
        return dateSimpleFormat(new Date(timeInMillis), defaultSlashDate);
    }

    /**
     * 获取days的MILLISECONDS
     *
     * @param days days可以是,0,1,2,3.。。。
     * @return
     */
    public static long getDayMillise(int days) {
        return TimeUnit.DAYS.toMillis(days);
    }

    /**
     * 获取小时的MILLISECONDS
     *
     * @param hours
     * @return
     */
    public static long getHourMillis(int hours) {
        return TimeUnit.HOURS.toMillis(hours);
    }


    /**
     * 获取当天的yyyy-MM-dd 00:00:00 的毫秒数
     * 注明：2016-04-12 00:00:00 的毫秒数
     *
     * @return
     */
    public static long getZeroDayMillise() {
        return getDate(getCurrentYear(), getCurrentMonth(), getDayOfMonth(), 0, 0, 0).getTime();
    }

    /**
     * 获取当天任意的小时的long型值
     *
     * @param day    天
     * @param hour   小时
     * @param minute 分钟
     * @param second 秒
     * @return
     */
    public static long getHourMillise(int day, int hour, int minute, int second) {
        return getDate(getCurrentYear(), getCurrentMonth(), getDayOfMonth() + day, hour, minute, second).getTime();
    }

    /**
     * 获取当天的yyyy-MM-dd 12:00:00 的毫秒数
     * 注明：2016-07-28 12:00:00 的毫秒数
     *
     * @return
     */
    public static long get12HourDayMillise(int day) {
        return getDate(getCurrentYear(), getCurrentMonth(), getDayOfMonth() - day, 12, 0, 0).getTime();
    }


    /**
     * 获得几天之前或者几天之后的凌晨日期(格式：yyyy-MM-dd 00:00:00)
     *
     * @param diff 差值：正的往后推，负的往前推
     * @return
     */
    public static long getZeroOtherDayMillise(int diff) {
        return getMilliseByDateTimeFormat(getOtherDay(diff) + " 00:00:00");
    }


    /**
     * 得到当前日期是星期几。
     *
     * @return 当为周日时，返回0，当为周一至周六时，则返回对应的1-6。
     */
    public static final int getCurrentDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 得到当前日期是一个月几号。
     *
     * @return 返回当前日期。
     */
    public static final int getCurrentDayOfMonth() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取每周的周一
     *
     * @param day 0表示当周的星期一，1表示上周的星期一，2表示上上周的星期一...等
     * @return 返回星期七的时间yyyy-MM-dd
     */
    public static String getMonday(int day) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
        cal.add(Calendar.DATE, -1 * 7 * day);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String monday = new SimpleDateFormat(DEFAULT_DATE_SLASH).format(cal.getTime());
        return monday;
    }


    /**
     * 获取每周的周天
     *
     * @param day 0表示当周的星期天，1表示上周的星期天，2表示上上周的星期天...等
     * @return 返回星期七的时间yyyy-MM-dd
     */
    public static String getSunday(int day) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
        cal.add(Calendar.DATE, -1 * 7 * day);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String sunday = new SimpleDateFormat(DEFAULT_DATE_SLASH).format(cal.getTime());
        return sunday;
    }

    /**
     * 获取一个月的第一天
     *
     * @param day 0表示本月的第一，负数表示上月的第一天，正数表示下月的第一天..等
     * @return yyyy-MM-dd
     */
    public static String getfirstDayofMonth(int day) {

        String firstDayofMonth;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, day);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        firstDayofMonth = new SimpleDateFormat(DEFAULT_DATE_SLASH).format(calendar.getTime());
        return firstDayofMonth;
    }


    /**
     * 获取一个月的最后一天
     *
     * @param day 0表示上月的最后一天，1表示本月的最后一天，2表示下月的最后一天..等
     * @return yyyy-MM-dd
     */
    public static String getlastDayofMonth(int day) {
        String lastDayofMonth;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH) * day);
        lastDayofMonth = new SimpleDateFormat(DEFAULT_DATE_SLASH).format(calendar.getTime());
        return lastDayofMonth;
    }

    /**
     * 获取一个月有多少天
     *
     * @param date yyyy-MM-dd
     * @return 一个月天数
     */
    public static int getMonthManyDay(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateByFormat(date, defaultDateFormat));
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 换算成小时 分钟
     *
     * @param millise
     * @return
     */
    public static synchronized String getHourMinuteByMillise(long millise) {
        StringBuffer timeBuff = new StringBuffer();
        long ss = millise / 1000; //共计秒数
        int MM = (int) ss / 60;   //共计分钟数
        int hh = (int) ss / 3600;  //共计小时数

        if (hh > 0 && hh < 60) {
            timeBuff.append(hh + " 小时");
            MM = ((int) ss % 3600) / 60;
            if (MM > 0) {
                timeBuff.append(MM + " 分钟");
            }
        } else if (MM > 0 && MM < 60) {
            timeBuff.append(MM + " 分钟");
        }

        return timeBuff.toString();
    }


    /**
     * 换算成小时 分钟
     *
     * @param minute
     * @return
     */
    public static synchronized String getHourMinuteByMinute(int minute) {
        StringBuffer timeBuff = new StringBuffer();
        int hh = (int) minute / 60;  //共计小时数
        int MM = ((int) minute % 60);
        if (hh > 0 && hh < 60) {
            timeBuff.append(hh + " 小时");
            if (MM > 0) {
                timeBuff.append(MM + " 分钟");
            }
        } else {
            if (MM > 0) {
                timeBuff.append(MM + " 分钟");
            }
        }
        return timeBuff.toString();
    }

}
