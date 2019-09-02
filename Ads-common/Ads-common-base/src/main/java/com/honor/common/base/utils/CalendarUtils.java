package com.honor.common.base.utils;

import com.honor.common.base.enums.DateFormatEnum;
import com.honor.common.base.exception.CommonException;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class CalendarUtils {

    /**
     * 将时间格式化日期格式(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(DateFormatEnum.DEFAULT_PATTERN.getCode());
        return formatDateByFormat(date, FORMAT_DATE);
    }

    /**
     * 输入格式
     *
     * @param date
     * @param dateFormatEnum
     * @return
     */
    public static String formatDate(Date date, DateFormatEnum dateFormatEnum) {
        SimpleDateFormat FORMAT_DATE_TIME2 = new SimpleDateFormat(dateFormatEnum.getCode());
        return formatDateByFormat(date, FORMAT_DATE_TIME2);
    }

    /**
     * 将时间格式化为指定格式
     *
     * @param date
     * @param format
     * @return
     */
    public final static String formatDateByFormat(Date date, SimpleDateFormat format) {
        String result = "";
        if (date != null) {
            result = format.format(date);
        }
        return result;
    }

    /**
     * 获取年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期(一个月中的哪一天)
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取小时(24小时制)
     *
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取分钟
     *
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 获取秒
     *
     * @param date
     * @return
     */
    public static int getSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * 获取毫秒
     *
     * @param date
     * @return
     */
    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 获取指定时间是星期几
     *
     * @param date
     * @return
     */
    public static int getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        return dayOfWeek;
    }

    /**
     * 日期相加
     *
     * @param date Date
     * @param day  int
     * @return Date
     */
    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 日期相加
     *
     * @param date Date
     * @param year int
     * @return Date
     */
    public static Date addYear(Date date, int year) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date));
        c.add(Calendar.YEAR, year);
        return c.getTime();
    }

    /**
     * 日期相加
     *
     * @param date  Date
     * @param month int
     * @return Date
     */
    public static Date addMonth(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date));
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

    public static Date addMinute(Date date, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) minute) * 60 * 1000);
        return c.getTime();
    }

    @SuppressWarnings("deprecation")
    public static Date formtMinute(Date date, int interval) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) - ((long) date.getMinutes() % interval) * 60 * 1000);
        return c.getTime();
    }

    /**
     * 日期相减
     *
     * @param date  Date
     * @param date1 Date
     * @return int
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    public static int diffMinute(Date endDate, Date starDate) {
        return (int) ((getMillis(endDate) - getMillis(starDate)) / (60 * 1000));
    }

    /**
     * diffMonth:计算两个日期间的月数. <br/>
     *
     * @param starDate
     * @param endDate
     * @return
     * @author William
     * @since JDK 1.7
     */
    public static int diffMonth(Date starDate, Date endDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(starDate);
        int m1 = c.get(Calendar.MONTH);
        int y1 = c.get(Calendar.YEAR);
        int d1 = c.get(Calendar.DAY_OF_MONTH);
        long t1 = c.getTimeInMillis();
        c.setTime(endDate);
        int m2 = c.get(Calendar.MONTH);
        int y2 = c.get(Calendar.YEAR);
        int d2 = c.get(Calendar.DAY_OF_MONTH);
        long t2 = c.getTimeInMillis();
        // 开始日期若小月结束日期
        if (t1 < t2) {
            int year = y2 - y1;
            if (d2 >= d1 - 1) {
                return year * 12 + m2 - m1;
            } else {
                return year * 12 + m2 - m1 - 1;
            }
        } else {
            int year = y1 - y2;
            if (d1 >= d2 - 1) {
                return year * 12 + m1 - m2;
            } else {
                return year * 12 + m1 - m2 - 1;
            }
        }
    }

    /**
     * 将字符串转换为日期格式的Date类型(yyyy-MM-dd)
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date) {
        try {
            SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
            return FORMAT_DATE.parse(date);
        } catch (Exception e) {
            log.error("日期解析异常" + e.getMessage(), e);
            throw new RuntimeException("日期解析异常");
        }
    }

    public static Date parseDate(String date, DateFormatEnum dateFormatEnum) {
        try {
            SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(dateFormatEnum.getCode());
            return FORMAT_DATE.parse(date);
        } catch (Exception e) {
            log.error("日期解析异常" + e.getMessage(), e);
            throw new RuntimeException("日期解析异常");
        }
    }


    public static boolean compareDateAndWeekDay(Date date, int weekDay) {
        if (weekDay == getWeek(date)) {
            return true;
        }
        return false;
    }

    /**
     * 获得当前时间
     *
     * @return
     */
    public static Date getCurrentDateTime() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取时间当前当天的结束时间
     *
     * @return
     */
    public static Date getCurrentDateLastTime() {
        return CalendarUtils.getDateLastTime(CalendarUtils.getCurrentDateTime());
    }

    /**
     * 获取时间当前当天的开始时间
     *
     * @return
     */
    public static Date getCurrentDateBeginTime() {
        return CalendarUtils.getDateBeginTime(CalendarUtils.getCurrentDateTime());
    }

    /**
     * 获取时间当天的结束时间
     *
     * @param date
     * @return
     */
    public static Date getDateLastTime(Date date) {
        String day = CalendarUtils.formatDate(date, DateFormatEnum.yyyy_MM_dd) + " 23:59:59";
        return CalendarUtils.parseDate(day, DateFormatEnum.DEFAULT_PATTERN);
    }

    /**
     * 获取时间当天的开始时间
     *
     * @param date
     * @return
     */
    public static Date getDateBeginTime(Date date) {
        String day = CalendarUtils.formatDate(date, DateFormatEnum.yyyy_MM_dd) + " 00:00:00";
        return CalendarUtils.parseDate(day, DateFormatEnum.DEFAULT_PATTERN);
    }

    /**
     * 获取时间当月开始时间
     *
     * @return
     */
    public static Date getMonthBeginTime() {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(getCurrentDateBeginTime());
            c.add(Calendar.MONTH, 0);
            c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
            System.out.println("===============本月first day:" + c.getTime());
        } catch (Exception e) {
            throw CommonException.ERROR("获取当月开始时间失败！");
        }
        return c.getTime();
    }

    /**
     * 时间比较yyyy-MM-dd
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compareDate(Date date1, Date date2, DateFormatEnum dateFormatEnum) {
        String str1 = formatDate(date1, dateFormatEnum);
        String str2 = formatDate(date2, dateFormatEnum);
        if (str1.equals(str2)) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否是有效的日期， yyyy-MM-dd, yyyy-MM-d, yyyy-M-dd, yyyy-M-d yyyy/MM/dd
     * yyyy/MM/d yyyy/M/dd yyyy/M/d yyyyMMdd
     *
     * @param date 日期字符串
     * @return 是则返回true，否则返回false
     */
    public static boolean isValidDate(String date) {
        if ((date == null) || (date.length() < 8)) {
            return false;
        }
        try {
            boolean result = false;
            SimpleDateFormat formatter;
            char dateSpace = date.charAt(4);
            String format[];
            if ((dateSpace == '-') || (dateSpace == '/')) {
                format = new String[4];
                String strDateSpace = Character.toString(dateSpace);
                format[0] = "yyyy" + strDateSpace + "MM" + strDateSpace + "dd";
                format[1] = "yyyy" + strDateSpace + "MM" + strDateSpace + "d";
                format[2] = "yyyy" + strDateSpace + "M" + strDateSpace + "dd";
                format[3] = "yyyy" + strDateSpace + "M" + strDateSpace + "d";
            } else {
                format = new String[1];
                format[0] = "yyyyMMdd";
            }

            for (int i = 0; i < format.length; i++) {
                String aFormat = format[i];
                formatter = new SimpleDateFormat(aFormat);
                formatter.setLenient(false);
                String tmp = formatter.format(formatter.parse(date));
                if (date.equals(tmp)) {
                    result = true;
                    break;
                }
            }
            return result;
        } catch (ParseException e) {
            return false;
        }
    }

    public static SimpleDateFormat getSimpleDateFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 日期回滚day天
     *
     * @param day
     * @return
     * @author yangaobiao
     * @since JDK 1.7
     */
    public static String getPastDate(Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - day);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(today);

    }

    /**
     * isSameWeek:判断两个日期是否在同一个星期里面 <br/>
     *
     * @param date1
     * @param date2
     * @return
     * @author ljx
     * @since JDK 1.7
     */
    public static boolean isSameWeek(Date date1, Date date2) {

        if (date1 == null) {
            return false;
        }
        if (date2 == null) {
            return false;
        }

        // 0.先把Date类型的对象转换Calendar类型的对象
        Calendar todayCal = Calendar.getInstance();
        Calendar dateCal = Calendar.getInstance();

        // 如果相差7天直接退出（绝对不会再一个周期内）
        if (diffDate(date1, date2) > 6 || diffDate(date1, date2) < -6) {
            return false;
        }

        todayCal.setTime(date1);
        dateCal.setTime(date2);

        // 1.比较两时间周数是否相同
        if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR)) {
            return true;
        } else {
            return false;
        }
    }

}
