package com.polystone.tools.date;

import com.polystone.tools.common.StringUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日期处理工具类
 *
 * @author jimmy
 * @version V1.0, 2017/7/14
 * @copyright
 */
public class DateUtil {

    public static final SimpleDateFormat YYYYMMDDHHMM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat YYYYMMDDHHMMSS_0 = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat YYYYMMDDHHMMSS_1 = new SimpleDateFormat("yyyyMMdd:HHmmss");
    public static final SimpleDateFormat YYYYMMDDHHMMSSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");
    public static final SimpleDateFormat MMDD = new SimpleDateFormat("MM-dd");
    public static final SimpleDateFormat YYYYMM = new SimpleDateFormat("yyyyMM");
    public static final SimpleDateFormat YYYYMM_0 = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat YYYYMMDD_0 = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat YYYYMMDDHH_0 = new SimpleDateFormat("yyyyMMddHH");
    public static final SimpleDateFormat YYMMDDHHMMSS_0 = new SimpleDateFormat("yyMMddHHmmss");
    public static final SimpleDateFormat HHMMSS = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat HHMMSS_0 = new SimpleDateFormat("HHmmss");


    private static ThreadLocal<Map<String, Object>> localDateFormat = new ThreadLocal<>();

    public static SimpleDateFormat getLocalFormater(String pattern) {
        Map<String, Object> map = localDateFormat.get();
        if (map == null) {
            map = new HashMap<>();
        }
        SimpleDateFormat df = (SimpleDateFormat) map.get(pattern);
        if (df == null) {
            df = new SimpleDateFormat(pattern);
            map.put(pattern, df);
            localDateFormat.set(map);
        }
        return df;
    }

    public static Calendar getLocalCalendar() {
        Map<String, Object> map = localDateFormat.get();
        if (map == null) {
            map = new HashMap<>();
        }
        Calendar calendar = (Calendar) map.get("calendar");
        if (calendar == null) {
            calendar = Calendar.getInstance();
            map.put("calendar", calendar);
            localDateFormat.set(map);
        }
        return calendar;
    }

    private static DateUtil me;
    private Calendar calendar;

    private DateUtil() {
        calendar = Calendar.getInstance();
    }

    private static DateUtil getIntance() {
        return null == me ? me = new DateUtil() : me;
    }

    /**
     * 获取日期的开始时间 例如：2016-06-16 00:00:00:000
     *
     * @return [说明]
     */
    public static Date getStartDate(Date param) {
        return new Date(getStartTime(param));
    }

    /**
     * 时间戳转换为开始时间
     *
     * @param longTime （毫秒）
     * @return [说明]
     */
    public static Date getStartDate(long longTime) {
        return new Date(getStartTime(longTime));
    }

    /**
     * 时间戳转换为相隔几天的开始时间
     *
     * @param longTime （毫秒）
     * @param day 相差天数 正数为往后推几天 ，负数往前退几天
     * @return [说明]
     */
    public static Date getStartDate(long longTime, int day) {
        return new Date(getStartTime(longTime, day));
    }

    public static long getStartTime(Date param) {
        return getStartTime(param.getTime());
    }

    /**
     * 获取开始时间的时间戳（毫秒）
     *
     * @return [说明]
     */
    public static long getStartTime(long longTime) {
        return getIntance()._getStartTime(longTime, 0);
    }

    /**
     * 获取相隔几天的开始时间的时间戳（毫秒）
     *
     * @param longTime （毫秒）
     * @param day 相差天数 正数为往后推几天 ，负数往前退几天
     * @return [说明]
     */
    public static long getStartTime(long longTime, int day) {
        return getIntance()._getStartTime(longTime, day);
    }

    /**
     * 获取相隔几天的开始时间的时间戳（毫秒）
     *
     * @param longTime （毫秒）
     * @param day 相差天数 正数为往后推几天 ，负数往前退几天
     * @return [说明]
     */
    public long _getStartTime(long longTime, int day) {
        calendar.setTimeInMillis(longTime);
        if (0 != day) {
            calendar.add(Calendar.DAY_OF_YEAR, day);
        }
        setTime(0, 0, 0, 0);
        return calendar.getTimeInMillis();
    }

    private void setTime(int hour, int minute, int second, int millisecond) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
    }

    /**
     * 获取日期结束时间 例如：2016-06-16 23:59:59:999
     *
     * @return [说明]
     */
    public static Date getEndDate(Date param) {
        return new Date(getEndTime(param));
    }

    /**
     * 时间戳转换为结束时间
     *
     * @return [说明]
     */
    public static Date getEndDate(long longTime) {
        return new Date(getEndTime(longTime));
    }

    /**
     * 时间戳转换为相隔几天的结束时间
     *
     * @param day 相差天数 正数为往后推几天 ，负数往前退几天
     * @return [说明]
     */
    public static Date getEndDate(long longTime, int day) {
        return new Date(getEndTime(longTime, day));
    }

    /**
     * 日期转换为结束时间戳
     *
     * @return [说明]
     */
    public static long getEndTime(Date param) {
        return getEndTime(param.getTime());
    }

    /**
     * 获取结束时间戳
     *
     * @return [说明]
     */
    public static long getEndTime(long longTime) {
        return getIntance()._getEndTime(longTime, 0);
    }

    /**
     * 时间戳转换为相隔几天的结束时间
     *
     * @param day 相差天数 正数为往后推几天 ，负数往前退几天
     * @return [说明]
     */
    public static long getEndTime(long longTime, int day) {
        return getIntance()._getEndTime(longTime, day);
    }

    /**
     * 时间戳转换为相隔几天的结束时间
     *
     * @param day 相差天数 正数为往后推几天 ，负数往前退几天
     * @return [说明]
     */
    private long _getEndTime(long longTime, int day) {
        calendar.setTimeInMillis(longTime);
        if (0 != day) {
            calendar.add(Calendar.DAY_OF_YEAR, day);
        }
        setTime(23, 59, 59, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * 返回月份第一天
     *
     * @param month 1-12
     * @return [说明]
     */
    public static String getStartMonthDate(int year, int month) {
        return getIntance()._getStartMonthDate(year, month);
    }

    /**
     * 返回月份第一天
     *
     * @param month 1-12
     * @return [说明]
     */
    private String _getStartMonthDate(int year, int month) {
        calendar.set(year, month - 1, calendar.getMinimum(Calendar.DAY_OF_MONTH));
        setTime(0, 0, 0, 0);
        return YYYYMMDDHHMMSS.format(calendar.getTimeInMillis());
    }

    /**
     * 返回月份最后一天
     *
     * @param month 1-12
     * @return [说明]
     */
    public static String getEndMonthDate(int year, int month) {
        return getIntance()._getEndMonthDate(year, month);
    }

    /**
     * 返回月份最后一天
     *
     * @param month 1-12
     * @return [说明]
     */
    private String _getEndMonthDate(int year, int month) {
        calendar.set(year, month - 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setTime(23, 59, 59, 999);
        return YYYYMMDDHHMMSS.format(calendar.getTimeInMillis());
    }

    /**
     * 设置小时分钟
     *
     * @return [参数说明]
     */
    public static Date getDateHM(int hour, int minute) {
        return getIntance()._getDateHM(System.currentTimeMillis(), hour, minute);
    }

    /**
     * 设置小时分钟
     *
     * @return [参数说明]
     */
    public static Date getDateHM(long longTime, int hour, int minute) {
        return getIntance()._getDateHM(longTime, hour, minute);
    }

    /**
     * 设置小时分钟
     *
     * @return [参数说明]
     */
    private Date _getDateHM(long longTime, int hour, int minute) {
        calendar.setTimeInMillis(longTime);
        setTime(hour, minute, 0, 0);
        return calendar.getTime();
    }

    /**
     * 获取相隔几天的时间戳
     *
     * @param longTime （毫秒）
     * @param day 相差天数 正数为往后推几天 ，负数往前退几天
     * @return [说明]
     */
    public static long day(long longTime, int day) {
        return getIntance()._day(longTime, day);
    }

    /**
     * 获取日期中的day 例如：2016-06-20 00:00:00 day是20
     *
     * @return [说明]
     */
    public static int day(long longTime) {
        return getIntance()._day(longTime);
    }

    /**
     * 获取日期中的day 例如：2016-06-20 13:00:00 hour是13
     *
     * @return [说明]
     */
    public static int hour(long longTime) {
        return getIntance()._hour(longTime);
    }

    /**
     * 日期格式化
     *
     * @return [说明]
     */
    public static Date format(SimpleDateFormat dateFormat, String source, Date def) {
        return getIntance()._format(dateFormat, source, def);
    }

    /**
     * 日期格式化
     */
    public static Date format(SimpleDateFormat dateFormat, String source) {
        return getIntance()._format(dateFormat, source);
    }

    /**
     * 日期格式化
     *
     * @return [说明]
     */
    public static String format(SimpleDateFormat dateFormat1, SimpleDateFormat dateFormat2, String source) {
        return getIntance()._format(dateFormat1, dateFormat2, source);
    }

    /**
     * String 转 Date
     */
    public static Date format(String source) {
        return format(YYYYMMDDHHMMSS, source);
    }

    /**
     * String 转 Date
     *
     * @param source 日期字符串
     */
    public static Date formatSimpleDate(String source) {
        return format(YYYYMMDD, source);
    }

    /**
     * 日期格式化 yyyy-MM-dd HH:mm:ss 参数source为空时，返回def
     *
     * @return [说明]
     */
    public static long longTime(String source, long def) {
        return longTime(YYYYMMDDHHMMSS, source, def);
    }

    public static long longTime(SimpleDateFormat dateFormat, String source, long def) {
        try {
            return StringUtil.isNotEmpty(source) ? dateFormat.parse(source).getTime() : def;
        } catch (ParseException e) {
            return def;
        }
    }

    /**
     * 日期格式化 自定义日期格式 String类型参数为空时 返回def
     *
     * @return [说明]
     */
    private Date _format(SimpleDateFormat dateFormat, String source, Date def) {
        try {
            return StringUtil.isNotTrimEmpty(source) ? dateFormat.parse(source) : def;
        } catch (ParseException e) {
            return def;
        }
    }

    /**
     * 日期格式化 自定义日期格式 错误抛异常
     */
    private Date _format(SimpleDateFormat dateFormat, String source) {
        try {
            return StringUtil.isNotTrimEmpty(source) ? dateFormat.parse(source) : null;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期格式化，返回dateFormat2日期类型，source为空时，返回当前日期
     *
     * @return [说明]
     */
    private String _format(SimpleDateFormat dateFormat1, SimpleDateFormat dateFormat2, String source) {
        Date now = new Date();
        try {
            return dateFormat2.format(StringUtil.isNotEmpty(source) ? dateFormat1.parse(source) : now);
        } catch (ParseException e) {
            return dateFormat2.format(now);
        }
    }

    /**
     * 根据longtime设置日历中当前日期，然后在当前日期中增加天数，返回毫秒
     *
     * @return [说明]
     */
    private long _day(long longTime, int day) {
        calendar.setTimeInMillis(longTime);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTimeInMillis();
    }

    /**
     * 根据longtime设置当前日期，返回当前日期在当月的天数
     *
     * @return [说明]
     */
    public int _day(long longTime) {
        calendar.setTimeInMillis(longTime);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据longtime设置日历中当前日期,返回当天小时数
     *
     * @return [说明]
     */
    public int _hour(long longTime) {
        calendar.setTimeInMillis(longTime);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 日期格式化 YYYYMMDDHHMMSS 转 YYYYMMDD
     *
     * @param dateTime 要格式话的日期
     * @return 返回值
     */
    public static String formatDate(String dateTime) {
        try {
            return YYYYMMDD.format(YYYYMMDDHHMMSS.parse(dateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断日期格式
     *
     * @return [参数1] [参数1说明]
     */
    public static boolean isDate(String str_input, String rDateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
        formatter.setLenient(false);
        try {
            formatter.format(formatter.parse(str_input));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Date 转 String YYYYMMDDHHMMSS
     *
     * @return [参数1] [参数1说明]
     */
    public static String dateTimeToString(Date date) {
        return YYYYMMDDHHMMSS.format(date);
    }

    /**
     * Date 转 String YYYYMMDD
     *
     * @return [参数1] [参数1说明]
     */
    public static String dateToString(Date date) {
        return YYYYMMDD.format(date);
    }

    /**
     * Date 转 String YYYYMMDD
     *
     * @return [参数1] [参数1说明]
     */
    public static String dateToStringNo(Date date) {
        return YYYYMMDD_0.format(date);
    }

    /**
     * 计算两个日期的相差天数
     *
     * @return [参数说明]
     */
    public static int dateDiff(Date start, Date end) {
        double over = (end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000.0);
        return (int) (over > 0 ? Math.ceil(over) : Math.floor(over));
    }

    /**
     * 获取data周第一天
     *
     * @return [参数1] [参数1说明]
     */
    public static String firstWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        // 所在周开始日期
        String firstWeekDay = YYYYMMDD.format(cal.getTime());
        return firstWeekDay + " 00:00:00";
    }

    /**
     * 获取data周最后一天
     *
     * @return [参数1] [参数1说明]
     */
    public static String lastWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        // 所在周结束日期
        String lastWeekDay = YYYYMMDD.format(cal.getTime());
        return lastWeekDay + " 23:59:59";
    }

    /***
     * convert Date to cron ,eg. "0 07 10 15 1 ? 2016"
     *
     * @param date
     *            : 时间点
     * @return
     */
    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }

    /***
     * 功能描述：日期转换cron表达式
     *
     * @param date
     * @param dateFormat
     *            : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * D1和D2时间对比
     */
    public static boolean compareDate(Date DATE1, Date DATE2) {
        try {
            if (DATE1.getTime() < DATE2.getTime()) {
                return true;
            } else if (DATE1.getTime() >= DATE2.getTime()) {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * 获取几分钟前的时间
     *
     * @param diff 分钟
     * @return 日期
     */
    public static Date getDiffMinute(int diff) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        if (0 != diff) {
            calendar.add(Calendar.MINUTE, diff);
        }
        return calendar.getTime();
    }

    /**
     * 获取当前时间 YYYY-MM-DD
     */
    public static Date getNowDate() throws ParseException {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        Date currentTime_2 = formatter.parse(dateString);
        return currentTime_2;

    }

    /**
     * @param oldTime 较小的时间
     * @param newTime 较大的时间 (如果为空 默认当前时间 ,表示和当前时间相比)
     * @return -1 ：同一天. 0：昨天 . 1 ：至少是前天.
     * @throws ParseException 转换异常
     * @author LuoB.
     */
    public static int isYeaterday(Date oldTime, Date newTime) throws ParseException {
        if (newTime == null) {
            newTime = new Date();
        }
        // 将下面的 理解成 yyyy-MM-dd 00：00：00 更好理解点
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = format.format(newTime);
        Date today = format.parse(todayStr);
        // 昨天 86400000=24*60*60*1000 一天
        if ((today.getTime() - oldTime.getTime()) > 0 && (today.getTime() - oldTime.getTime()) <= 86400000) {
            return 0;
            // 至少是今天
        } else if ((today.getTime() - oldTime.getTime()) <= 0) {
            return -1;
        } else {
            // 至少是前天
            return 1;
        }

    }

    /**
     * 获取一天还剩的秒
     *
     * @return 返回值
     */
    public static long dayOverTime() {
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
    }

    /**
     * 得到几天后的时间
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DAY_OF_MONTH) + day);
        return now.getTime();
    }

    /***
     * 日期减一天、加一天
     *
     * @param option
     *            传入类型 pro：日期减一天，next：日期加一天
     * @param _date
     *            2014-11-24
     * @return 减一天：2014-11-23或(加一天：2014-11-25)
     */
    public static String checkOption(String option, int day, String _date) {
        Calendar cl = Calendar.getInstance();
        Date date = null;
        try {
            date = YYYYMMDDHHMMSS.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cl.setTime(date);
        if ("pre".equals(option)) {
            // 时间减一天
            cl.add(Calendar.DAY_OF_MONTH, -day);

        } else if ("next".equals(option)) {
            // 时间加一天
            cl.add(Calendar.DAY_OF_YEAR, day);
        } else {
            // do nothing
        }
        date = cl.getTime();
        return YYYYMMDDHHMMSS.format(date);
    }

    /**
     * 得到一段时间之后(之前)的时间
     *
     * @param distance 时间跨度
     * @param unit 时间单位 如:Calendar.SECOND
     * @return [参数说明]
     */
    public static Date getAfterSomeDistanceDate(Date day, int distance, int unit) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        switch (unit) {
            case Calendar.SECOND:
                cal.add(Calendar.SECOND, distance);
                break;
            case Calendar.MINUTE:
                cal.add(Calendar.MINUTE, distance);
                break;
            case Calendar.DAY_OF_MONTH:
                cal.add(Calendar.DAY_OF_MONTH, distance);
                break;
            case Calendar.MONTH:
                cal.add(Calendar.MONTH, distance);
                break;
            case Calendar.DAY_OF_YEAR:
                cal.add(Calendar.DAY_OF_YEAR, distance);
                break;
            case  Calendar.HOUR_OF_DAY:
                cal.add(Calendar.HOUR_OF_DAY, distance);
            default:
                break;

        }
        return cal.getTime();
    }

    /**
     * 获取YYYYMMDD格式
     */
    public static String getDays() {
        return YYYYMMDD_0.format(new Date());
    }

    /**
     * 获得某个月的月底时间
     *
     * @throws ParseException [参数说明]
     */
    public static Date getMaxMonthDate(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 某个月的第一天
     *
     * @return [参数说明]
     */
    public static Date getMinMonthDate(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 某个月的几号
     *
     * @return [参数说明]
     */
    public static Date getMonthDate(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 往后延几个月的几号
     *
     * @return [参数说明]
     */
    public static Date getMonthDate(int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取日期的月
     *
     * @param date 日期
     * @return 返回值
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static Integer getDayNum(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year * 10000 + month * 100 + day;
    }

    //获取当前时间前后几天
    public static String getBeforeDay(String pattern, Integer amount) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 单个数据按日期补零(格式：2017-01-01)
     *
     * @param startTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @param concent
     *            数据名称
     * @return
     */
    public static List<Map<String, Object>> supplementByDay(String startTime, String endTime, String dateName,String concent) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        try {
            start = simpleDateFormat.parse(startTime);
            end = simpleDateFormat.parse(endTime);
        } catch (ParseException e) {

            return null;
        }
        Integer days = compare(start, end);
        List<Map<String, Object>> reusltList = new ArrayList<>();
        for (int i = 0; i < days + 1; i++) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            String time = simpleDateFormat.format(start);
            map1.put(dateName, time);
            map1.put(concent, 0);
            reusltList.add(map1);
            start = cal.getTime();
        }
        return reusltList;
    }

    public static int compare(Date one, Date two) {
        Long num1 = one.getTime();
        Long num2 = two.getTime();
        return (int) ((num2 - num1) / (24 * 3600 * 1000));
    }

    public static Date getDateAfterSecond(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

}
