package com.molistore.application.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ofPattern;

@Slf4j
public class DateTimeUtil {
    public static final DateTimeFormatter DATETIME_TIMEZONE_FORMATTER =
            ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US);
    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    public static Calendar toCalendar(LocalDate from) {
        Date date = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Calendar toCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date atEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date atStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static LocalDateTime atStartOfDayLDT(Date date) {
        return date
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .atStartOfDay();
    }

    public static LocalDate toLocalDate(Calendar calendar) {
        if (calendar == null) return null;
        return calendar.getTime().toInstant().atZone(systemDefault()).toLocalDate();
    }

    public static LocalDate toLocalDate(Object obj) {
        if (obj instanceof Calendar) {
            Calendar date = (Calendar) obj;
            return LocalDateTime.ofInstant(date.toInstant(), date.getTimeZone().toZoneId()).toLocalDate();
        } else if (obj instanceof Timestamp) {
            Timestamp date = (Timestamp) obj;
            return date.toLocalDateTime().toLocalDate();
        } else if (obj instanceof Date) {
            Date date = (Date) obj;
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else {
            return null;
        }
    }

    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null)
            return null;
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }

    public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null)
            return null;
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()), TimeZone.getDefault().toZoneId());
    }

    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static Date localDateToDate(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Calendar yesterday() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar;
    }

    public static Calendar addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days); //minus number would decrement the days
        return calendar;
    }

    public static LocalDate convertTimestampToLocalDateOrElseNull(Optional<Timestamp> deleteDateByBusinessKey) {
        return deleteDateByBusinessKey
                .map(Timestamp::toLocalDateTime)
                .map(LocalDateTime::toLocalDate)
                .orElse(null);
    }

    public static LocalDateTime convertTimestampToLocalDateTimeOrElseNull(Optional<Timestamp> deleteDateByBusinessKey) {
        return deleteDateByBusinessKey
                .map(Timestamp::toLocalDateTime)
                .orElse(null);
    }

    public static Calendar getCalendar(String executionDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(executionDate);
        } catch (ParseException e) {
            log.error("Fail to parse a String that is ought to have a special format: {}", executionDate);
            e.printStackTrace();
            return null;
        }
        Calendar calendarExecutionDate = Calendar.getInstance();
        calendarExecutionDate.setTime(date);
        return calendarExecutionDate;
    }


    public static long localDateTimePlusDays(LocalDateTime localCurrentDate, Long day) {
        return localCurrentDate
                .plusDays(day)
                .atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli();
    }

    public static LocalDateTime datePlusDays(Date date, Long day) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .plusDays(day);
    }

}
