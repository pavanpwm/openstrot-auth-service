package com.openstrot.auth.util;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    // Format for displaying date and time
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // Constant for UTC zone
    private static final ZoneId UTC_ZONE = ZoneId.of("UTC");

    // Private constructor to prevent instantiation
    private DateTimeUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Get the current UTC time as a LocalDateTime.
     *
     * @return Current UTC time as LocalDateTime
     */
    public static LocalDateTime getCurrentUtcTime() {
        return LocalDateTime.ofInstant(Instant.now(), UTC_ZONE);
    }

    /**
     * Get the current UTC time as epoch milliseconds.
     *
     * @return Current UTC time as epoch milliseconds
     */
    public static long getCurrentUtcTimeMillis() {
        return Instant.now().toEpochMilli();
    }

    /**
     * Format a LocalDateTime as a string using the specified format.
     *
     * @param dateTime LocalDateTime to format
     * @return Formatted string
     */
    public static String formatUtcDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    /**
     * Format epoch milliseconds as a string using the specified format.
     *
     * @param epochMillis Epoch milliseconds to format
     * @return Formatted string
     */
    public static String formatUtcDateTime(long epochMillis) {
        return formatUtcDateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), UTC_ZONE));
    }

    // Other methods...

    /**
     * Convert epoch milliseconds to a LocalDateTime.
     *
     * @param epochMillis Epoch milliseconds to convert
     * @return LocalDateTime
     */
    public static LocalDateTime convertMillisToUtcDateTime(long epochMillis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), UTC_ZONE);
    }

    /**
     * Convert a LocalDateTime to epoch milliseconds.
     *
     * @param dateTime LocalDateTime to convert
     * @return Epoch milliseconds
     */
    public static long convertUtcDateTimeToMillis(LocalDateTime dateTime) {
        return dateTime.atZone(UTC_ZONE).toInstant().toEpochMilli();
    }
}
