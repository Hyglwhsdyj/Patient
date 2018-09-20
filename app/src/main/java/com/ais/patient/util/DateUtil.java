package com.ais.patient.util;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by ewu on 2016/5/13.
 */
public class DateUtil {

    public final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * getCurrentDateString
     * <p>
     * Description: Get the data string based on the given pattern
     * <p>
     * @date 2014-11-10
     * @author ewu
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern)
    {
        if (null == date || TextUtils.isEmpty(pattern))
        {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(date);
    }

    public static Date string2Date(String dateStr, String formatStr) {
        DateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取两个日期之间的间隔天数
     * @return
     */
    public static int getGapDays(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    public static Date addDateDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }

    public static Date addDateHours(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }


    public static Calendar Date2Calendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Date Calendar2Date(Calendar cal) {
        Date date = cal.getTime();
        return date;
    }

    public static boolean isBetweenDate(Date objDate, Date startDate,
                                        Date endDate) {
        long obj = Long.valueOf(formatter.format(objDate));
        long obj1 = Long.valueOf(formatter.format(startDate));
        long obj2 = Long.valueOf(formatter.format(endDate));
        return obj >= obj1 && obj <= obj2;
    }

}
