package com.zhenxuan.tradeapi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Date & Time formatter and converter.
 *
 * @author leimingshan
 * @anthor heyijie
 * @since 16/6/21
 */
public class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 根据给定日期格式转换成时间字符串
     *
     * @param date    @see java.util.Date
     * @param pattern the pattern describing the date and time format
     *
     * @return the formatted time string.
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 根据给定时间戳及其日期格式转换成时间字符串
     *
     * @param timestamp the milliseconds since January 1, 1970, 00:00:00 GMT.
     * @param pattern   the pattern describing the date and time format
     *
     * @return the formatted time string.
     */
    public static String format(Long timestamp, String pattern) {
        Date date = new Date(timestamp);
        return format(date, pattern);
    }

    /**
     * 获取date
     *
     * @param dateString the formatted time string.
     * @param pattern    the pattern describing the date and time format
     *
     * @return Date object @see java.util.Date
     *
     * @throws ParseException
     */
    public static Date getDateByPatten(String dateString, String pattern) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            logger.trace("Parse error", e);
        }
        return date;
    }

    /**
     * Convert one date string of specified pattern to unix timestamp.
     *
     * @param dateString the formatted time string.
     * @param pattern    the pattern describing the date and time format
     *
     * @return unit timestamp value in long
     */
    public static Long toUnixTimestamp(String dateString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime time = LocalDateTime.parse(dateString, formatter);
        return time.toEpochSecond(ZoneOffset.ofHours(8));
    }

    /**
     * 将LocalDate 转为 Date
     */
    public static Date convertLocalDate2Date(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * unix timestamp to date
     */
    public static Date convertTimestamp2Date(String unixTimestamp) {
        Long timestamp = Long.parseLong(unixTimestamp) * 1000;
        return new Date(timestamp);
    }

}
