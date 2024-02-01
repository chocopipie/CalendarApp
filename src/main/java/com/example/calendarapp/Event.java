package com.example.calendarapp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class Event {
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private String eventName;
    private User eventOwner;

    private static final AtomicInteger nextId = new AtomicInteger(1);
    private final int eventID;

    public Event(String eventName, ZonedDateTime startDateTime, ZonedDateTime endDateTime, User eventOwner) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.eventName = eventName;
        this.eventID = nextId.getAndIncrement();
        this.eventOwner = eventOwner;
    }

    public ZonedDateTime getStartTime() {
        return startDateTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startDateTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endDateTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endDateTime = endTime;
    }

    public String getName() {
        return eventName;
    }

    public void setName(String name) {
        this.eventName = name;
    }

    public int getEventID() {
        return eventID;
    }

    public void updateStartTime(String startTime) {
        startDateTime = stringToZonedDateTimeParser(startTime);
    }

    public void updateEndTime(String endTime) {
        endDateTime = stringToZonedDateTimeParser(endTime);
    }

    private ZonedDateTime stringToZonedDateTimeParser(String datetime) {
        // Implement logic to parse a string into ZonedDateTime
        // Append a default time zone if not present in the input string
        TimeZone ownerTimeZone = eventOwner.getAppSettings().getTimeZone();
        // Get the raw offset in milliseconds
        int OffsetInMilliseconds = ownerTimeZone.getOffset(System.currentTimeMillis());
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

    public String printTimes() {
        return "Event: " + eventName + "\nStart Time: " + startDateTime + "\nEnd Time: " + endDateTime;
    }

    public void updateTimeZone(String newTimeZone) {
        String currentTimeZone = startDateTime.getZone().getId();

        // convert the event times to the new time zone
        ZonedDateTime newStartTime = startDateTime.withZoneSameInstant(ZoneId.of(newTimeZone));
        ZonedDateTime newEndTime = endDateTime.withZoneSameInstant(ZoneId.of(newTimeZone));

        // update the event with new time zone
        this.startDateTime = newStartTime;
        this.endDateTime = newEndTime;

    }

    @Override
    public String toString() {
        return "Event ID: " + eventID +
                "\nEvent Name: " + eventName +
                "\nStart Time: " + startDateTime +
                "\nEnd Time: " + endDateTime + "\n" +
                "\nDate: " + startDateTime.getDayOfMonth() + "/" + startDateTime.getMonth();
    }
}
