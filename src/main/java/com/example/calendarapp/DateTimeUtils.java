package com.example.calendarapp;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class DateTimeUtils {
    public static ZonedDateTime stringToZonedDateTimeParser(String datetime, TimeZone timeZone) {
        // Implement logic to parse a string into ZonedDateTime
        // Append a default time zone if not present in the input string
        // Get the raw offset in milliseconds
        int OffsetInMilliseconds = timeZone.getOffset(System.currentTimeMillis());
        // Convert milliseconds to hours
        String offsetString = offsetToString(OffsetInMilliseconds);

        //if (!datetime.contains("Z") && !datetime.contains("+") && !datetime.contains("-")) {
        datetime += offsetString; // Assuming UTC if no time zone is specified
        //}

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return ZonedDateTime.parse(datetime, formatter);
    }

    // Convert a time zone offset in milliseconds to its string representation
    private static String offsetToString(int offsetMillis) {
        int hours = offsetMillis / (60 * 60 * 1000);
        int minutes = Math.abs((offsetMillis / (60 * 1000)) % 60);

        // Construct the offset string in the format ±HH:mm
        return (hours > 0 ? String.format("+%02d:%02d", hours, minutes) : String.format("%03d:%02d", hours, minutes));
    }
}
