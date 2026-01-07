package ${package}.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期时间工具类
 * <p>
 * 基于Java 8新的日期时间API实现
 * 遵循阿里巴巴Java开发规范：
 * 1. 使用Java 8的日期时间API替代旧的Date类
 * 2. 日期格式化使用yyyy-MM-dd HH:mm:ss
 * 3. 工具类方法必须是静态的
 *
 * @author ${author}
 * @since 1.0.0
 */
public class DateUtils {

    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 默认时间格式
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    /**
     * 日期时间格式化器
     */
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);

    /**
     * 日期格式化器
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);

    /**
     * 时间格式化器
     */
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT);

    private DateUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // ==================== 格式化 ====================

    /**
     * 格式化日期时间（默认格式）
     *
     * @param localDateTime 日期时间
     * @return 格式化后的字符串
     */
    public static String formatDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.format(DATETIME_FORMATTER);
    }

    /**
     * 格式化日期（默认格式）
     *
     * @param localDate 日期
     * @return 格式化后的字符串
     */
    public static String formatDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return localDate.format(DATE_FORMATTER);
    }

    /**
     * 格式化时间（默认格式）
     *
     * @param localTime 时间
     * @return 格式化后的字符串
     */
    public static String formatTime(LocalTime localTime) {
        if (localTime == null) {
            return null;
        }
        return localTime.format(TIME_FORMATTER);
    }

    /**
     * 格式化日期时间（自定义格式）
     *
     * @param localDateTime 日期时间
     * @param pattern       格式
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        if (localDateTime == null || pattern == null || pattern.isEmpty()) {
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 格式化Date类型为字符串
     *
     * @param date Date对象
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return formatDateTime(toLocalDateTime(date));
    }

    // ==================== 解析 ====================

    /**
     * 解析日期时间字符串（默认格式）
     *
     * @param dateTimeStr 日期时间字符串
     * @return LocalDateTime
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
    }

    /**
     * 解析日期字符串（默认格式）
     *
     * @param dateStr 日期字符串
     * @return LocalDate
     */
    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * 解析时间字符串（默认格式）
     *
     * @param timeStr 时间字符串
     * @return LocalTime
     */
    public static LocalTime parseTime(String timeStr) {
        if (timeStr == null || timeStr.isEmpty()) {
            return null;
        }
        return LocalTime.parse(timeStr, TIME_FORMATTER);
    }

    /**
     * 解析日期时间字符串（自定义格式）
     *
     * @param dateTimeStr 日期时间字符串
     * @param pattern     格式
     * @return LocalDateTime
     */
    public static LocalDateTime parse(String dateTimeStr, String pattern) {
        if (dateTimeStr == null || dateTimeStr.isEmpty() || pattern == null || pattern.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    // ==================== 转换 ====================

    /**
     * Date转LocalDateTime
     *
     * @param date Date对象
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime LocalDateTime对象
     * @return Date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate转Date
     *
     * @param localDate LocalDate对象
     * @return Date
     */
    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // ==================== 获取当前时间 ====================

    /**
     * 获取当前日期时间
     *
     * @return 当前日期时间
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    public static LocalDate today() {
        return LocalDate.now();
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static LocalTime currentTime() {
        return LocalTime.now();
    }

    /**
     * 获取当前时间戳（秒）
     *
     * @return 时间戳
     */
    public static long currentSeconds() {
        return Instant.now().getEpochSecond();
    }

    /**
     * 获取当前时间戳（毫秒）
     *
     * @return 时间戳
     */
    public static long currentMillis() {
        return System.currentTimeMillis();
    }

    // ==================== 时间计算 ====================

    /**
     * 增加天数
     *
     * @param localDateTime 日期时间
     * @param days          天数
     * @return 新的日期时间
     */
    public static LocalDateTime plusDays(LocalDateTime localDateTime, long days) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.plusDays(days);
    }

    /**
     * 增加小时数
     *
     * @param localDateTime 日期时间
     * @param hours         小时数
     * @return 新的日期时间
     */
    public static LocalDateTime plusHours(LocalDateTime localDateTime, long hours) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.plusHours(hours);
    }

    /**
     * 增加分钟数
     *
     * @param localDateTime 日期时间
     * @param minutes       分钟数
     * @return 新的日期时间
     */
    public static LocalDateTime plusMinutes(LocalDateTime localDateTime, long minutes) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.plusMinutes(minutes);
    }

    /**
     * 减少天数
     *
     * @param localDateTime 日期时间
     * @param days          天数
     * @return 新的日期时间
     */
    public static LocalDateTime minusDays(LocalDateTime localDateTime, long days) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.minusDays(days);
    }

    /**
     * 计算两个日期时间之间的天数
     *
     * @param start 开始日期时间
     * @param end   结束日期时间
     * @return 天数
     */
    public static long betweenDays(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }
        return java.time.Duration.between(start, end).toDays();
    }

    /**
     * 计算两个日期时间之间的小时数
     *
     * @param start 开始日期时间
     * @param end   结束日期时间
     * @return 小时数
     */
    public static long betweenHours(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }
        return java.time.Duration.between(start, end).toHours();
    }

    // ==================== 判断 ====================

    /**
     * 判断是否是今天
     *
     * @param localDateTime 日期时间
     * @return true-是，false-否
     */
    public static boolean isToday(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return false;
        }
        return localDateTime.toLocalDate().equals(LocalDate.now());
    }

    /**
     * 判断日期时间是否在指定范围内
     *
     * @param current 当前日期时间
     * @param start   开始日期时间
     * @param end     结束日期时间
     * @return true-在范围内，false-不在范围内
     */
    public static boolean isBetween(LocalDateTime current, LocalDateTime start, LocalDateTime end) {
        if (current == null || start == null || end == null) {
            return false;
        }
        return !current.isBefore(start) && !current.isAfter(end);
    }
}
