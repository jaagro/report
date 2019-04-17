package com.jaagro.report.api.util;


import com.jaagro.report.api.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

/**
 * @author yj
 */
@Slf4j
public class DateUtil {
    private static ThreadLocal<DateFormat> threadLocalDateTime = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private static ThreadLocal<DateFormat> threadLocalDate = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private static ThreadLocal<DateFormat> threadLocalMonth = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM");
        }
    };

    public static String formatDateTime(Date dateTime){
        return threadLocalDateTime.get().format(dateTime);
    }

    public static String formatDate(Date date){
        return threadLocalDate.get().format(date);
    }

    public static String formatMonth(Date date){
        return threadLocalMonth.get().format(date);
    }

    public static Date parseDateTime(String dateTimeStr){
        try {
            return threadLocalDateTime.get().parse(dateTimeStr);
        } catch (ParseException e) {
            log.error("parseDateTime error dateTimeStr="+dateTimeStr,e);
            throw new BusinessException("日期转换出错");
        }
    }

    public static Date parseDate(String dateStr){
        try {
            return threadLocalDate.get().parse(dateStr);
        } catch (ParseException e) {
            log.error("parseDate error dateStr="+dateStr,e);
            throw new BusinessException("日期转换出错");
        }
    }

    public static Date parseMonth(String monthStr){
        try {
            return threadLocalMonth.get().parse(monthStr);
        } catch (ParseException e) {
            log.error("parseMonth error monthStr="+monthStr,e);
            throw new BusinessException("日期转换出错");
        }
    }


    /**
     * 获取当天的零时零分零秒
     * @param date
     * @return
     */
    public static Date truncate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        System.out.println(formatDateTime(new Date()));
        System.out.println(parseDateTime("2019-04-16 16:39:59"));
        System.out.println(truncate(new Date()));
    }
}
