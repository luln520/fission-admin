package net.lab1024.sa.common.common.util;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author dingzhiwei jmdhappy@126.com
 * @version V1.0
 * @Description: 日期时间工具类
 * @date 2017-07-05
 * @Copyright: www.xxpay.org
 */
public class DateUtil {

    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddhhmmssSSS";
    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddhhmmss";
    public static final String FORMAT_YYYYMMddhhMMSS = "yyyyMMddHHmmss";

    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static String getCurrentDate() {
        String formatPattern_Short = "yyyyMMddhhmmss";
        SimpleDateFormat format = new SimpleDateFormat(formatPattern_Short);
        return format.format(new Date());
    }

    public static String getSeqString() {
        SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss"); // "yyyyMMdd G
        return fm.format(new Date());
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取当前时间，格式为 yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurrentTimeStr(String format) {
        format = StringUtils.isBlank(format) ? FORMAT_YYYY_MM_DD_HH_MM_SS : format;
        Date now = new Date();
        return date2Str(now, format);
    }

    public static String date2Str(Date date) {
        return date2Str(date, FORMAT_YYYY_MM_DD_HH_MM_SS);
    }


    /**
     * 获取当前时间，格式为 yyyyMMddHHmmss
     *
     * @return
     */
    public static Date getCurrentTimeSDate(String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS);
        return dateFormat.parse(format);
    }


    /**
     * 时间转换成 Date 类型
     *
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format) {
        if ((format == null) || format.equals("")) {
            format = FORMAT_YYYY_MM_DD_HH_MM_SS;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null) {
            return sdf.format(date);
        }
        return "";
    }

    /***
     * string 日期转换 long
     * @param date
     * @param format
     * @return
     * @throws ParseException
     */
    public static long str2long(String date, String format) throws ParseException {
        if ((format == null) || format.equals("")) {
            format = FORMAT_YYYYMMDDHHMMSS;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null) {
            return sdf.parse(date).getTime();
        }
        return 0L;
    }

    public static Date str2DateTime(String date) {
        return str2DateTime(date, FORMAT_YYYY_MM_DD_HH_MM_SS);
    }
    public static Date str2DateDay(String date) {
        return str2DateTime(date, FORMAT_YYYY_MM_DD);
    }

    public static Date str2DateTime(String date, String format) {
        try {
            if ((format == null) || format.equals("")) {
                format = FORMAT_YYYY_MM_DD_HH_MM_SS;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String long2Str(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return long2Str(time, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    public static String long2Str(long time, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        String dateString = new SimpleDateFormat(format).format(cal.getTime());
        return dateString;
    }

    /**
     * 获取批量付款预约时间
     *
     * @return
     */
    public static String getRevTime() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        String dateString = new SimpleDateFormat(DateUtil.FORMAT_YYYYMMDDHHMMSS).format(cal.getTime());
        //System.out.println(dateString);
        return dateString;
    }

    /**
     * @return
     */
    public static String getFORMAT_YYYYMMDDHHMMSS() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //cal.add(Calendar.DATE, 1);
        String dateString = new SimpleDateFormat(DateUtil.FORMAT_YYYYMMddhhMMSS).format(cal.getTime());
        return dateString;
    }

    public static Date getBeferTimeHour() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, 2);
        return cal.getTime();
    }

    /**
     * 时间比较
     *
     * @param date1
     * @param date2
     * @return DATE1>DATE2返回1，DATE1<DATE2返回-1,等于返回0
     */
    public static int compareDate(String date1, String date2, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 把给定的时间减掉给定的分钟数
     *
     * @param date
     * @param minute
     * @return
     */
    public static Date minusDateByMinute(Date date, int minute) {
        Date newDate = new Date(date.getTime() - (minute * 60 * 1000));
        return newDate;
    }

    /**
     * 把给定的时间减掉给定的秒数
     *
     * @return
     */
    public static String secondsDateBySeconds(Date date, int seconds) {

        // 创建一个Calendar对象，并将其设置为当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 使用Calendar对象减去秒数
        calendar.add(Calendar.SECOND, -seconds);

        // 获取减去指定秒数后的Date对象
        Date resultDate = calendar.getTime();

        return date2Str(resultDate);
    }

    public static Date secondsDateAddSeconds(Date date, int seconds) {

        // 创建一个Calendar对象，并将其设置为当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 使用Calendar对象减去秒数
        calendar.add(Calendar.SECOND, seconds);

        // 获取减去指定秒数后的Date对象
         return calendar.getTime();

    }

    public static Date dateToDate(Date date, int time,String type) {

        // 创建一个Calendar对象，并将其设置为当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if(type.equals("S")){    //秒
            calendar.add(Calendar.SECOND, time);
        }
        if(type.equals("M")){     //分钟
            calendar.add(Calendar.MINUTE, time);
        }
        if(type.equals("H")){     //小时
            calendar.add(Calendar.HOUR, time);
        }
        if(type.equals("DAY")){     //天
            calendar.add(Calendar.DAY_OF_YEAR, time);
        }
        if(type.equals("WEEK")){     //周
            calendar.add(Calendar.WEEK_OF_YEAR, time);
        }
        if(type.equals("MON")){    //月
            calendar.add(Calendar.MONTH, time);
        }
        if(type.equals("YEAR")){   //年
            calendar.add(Calendar.YEAR, time);
        }

        // 获取减去指定秒数后的Date对象
        return calendar.getTime();

    }

    /**
     * 两个时间相差描述
     * @return
     */

    public static int dateToSeconds(Date  date2) {
        // 创建两个 Date 对象
        Date date1 = new Date(); // 当前时间

        // 获取两个 Date 对象的毫秒数
        long time1 = date1.getTime();
        long time2 = date2.getTime();

        // 计算毫秒差异
        long timeDifference = time2 - time1;

        // 将毫秒差异转换为秒数
        long secondsDifference = timeDifference / 1000;
        int second = (int) secondsDifference;
        System.out.println(second);
        return second;
    }


    public static List<String> getBetweenTime(Date startDate, Date endDate) {
        List<String> betweenTime = new ArrayList<>();
        try {
            SimpleDateFormat outformat = new SimpleDateFormat("yyyy-MM-dd");

            Calendar sCalendar = Calendar.getInstance();
            sCalendar.setTime(startDate);
            int year = sCalendar.get(Calendar.YEAR);
            int month = sCalendar.get(Calendar.MONTH);
            int day = sCalendar.get(Calendar.DATE);
            sCalendar.set(year, month, day, 0, 0, 0);

            Calendar eCalendar = Calendar.getInstance();
            eCalendar.setTime(endDate);
            year = eCalendar.get(Calendar.YEAR);
            month = eCalendar.get(Calendar.MONTH);
            day = eCalendar.get(Calendar.DATE);
            eCalendar.set(year, month, day, 0, 0, 0);

            while (sCalendar.before(eCalendar)) {
                betweenTime.add(outformat.format(sCalendar.getTime()));
                sCalendar.add(Calendar.DAY_OF_YEAR, 1);
            }
            betweenTime.add(outformat.format(eCalendar.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return betweenTime;
    }
    /**
     * 当前日期加上天数后的日期
     *
     * @param num 为增加的天数
     * @return
     */
    public static Date plusDay(Date date, int num) throws ParseException {
        System.out.println("date----?" + date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        date = ca.getTime();
        return date;
    }


}
