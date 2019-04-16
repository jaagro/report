package com.jaagro.report.api.util;


import lombok.extern.slf4j.Slf4j;

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
public class DateUtil {

    public static String format(Date date,String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateToLocalDateTime(date).format(formatter);
    }

    public static Date parse(String dateStr,String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
        return localDateTimeToDate(localDateTime);
    }
    /**
     * 将Date转换为LocalDatetime
     * @param date
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
       //1.从日期获取ZonedDateTime并使用其方法toLocalDateTime（）获取LocalDateTime
      //2.使用LocalDateTime的Instant（）工厂方法
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }

    /**
     *  将LocalDateTime转换回java.util.Date：
     * 1.使用atZone（）方法将LocalDateTime转换为ZonedDateTime
     2.将ZonedDateTime转换为Instant，并从中获取Date
     * @param localDateTime
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    public static void main(String[] args) {
        System.out.println(format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        System.out.println(parse("2019-04-16 16:39:59","yyyy-MM-dd HH:mm:ss"));
    }
}
