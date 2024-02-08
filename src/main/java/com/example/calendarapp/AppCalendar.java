package com.example.calendarapp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AppCalendar {
    protected String calendarName;
    protected ArrayList<Event> events;
    protected Visible calendarVisibility;
    protected Calendar calendarType;
    protected CalendarFormat format;
    protected User calendarOwner;
    // initializes a static and final AtomicInteger named nextId
    // with an initial value of 1.
    // This variable will be shared across all instances of the AppCalendar class.
    private static final AtomicInteger nextId = new AtomicInteger(1);
    private final int calendarID;

    public AppCalendar(String calendarName, Visible visibility, CalendarFormat format, Calendar calendarType, User owner) {
        this.calendarName = calendarName;
        this.events = new ArrayList<>();
        this.calendarVisibility = visibility;
        this.format = format;
        this.calendarType = calendarType;
        this.calendarOwner = owner;
        this.calendarID = nextId.getAndIncrement();
    }

    // getters and setters
    public String getCalendarName() { return calendarName;}

    public void setCalendarName(String calendarName) { this.calendarName = calendarName;}
    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public Visible getCalendarVisibility() {
        return calendarVisibility;
    }

    public void setCalendarVisibility(Visible calendarVisibility) {
        this.calendarVisibility = calendarVisibility;
    }

    public User getCalendarOwner() {
        return calendarOwner;
    }

    public void setCalendarOwner(User calendarOwner) {
        this.calendarOwner = calendarOwner;
    }

    public CalendarFormat getFormat() {
        return format;
    }

    public void setFormat(CalendarFormat format) {
        this.format = format;
    }

    public Calendar getCalendarType() {
        return calendarType;
    }

    public void setCalendarType(Calendar calendarType) {
        this.calendarType = calendarType;
    }

    public int getCalendarID() {
        return calendarID;
    }

    // other methods

    // this method switch to other type of calendar
    public void switchCalendarType(Calendar newCalendarType) {
        this.calendarType = newCalendarType;
    }
    public String printAllEvents() {
        StringBuilder result = new StringBuilder("Events in Calendar:\n");
        for (Event event : events) {
            result.append(event.toString()).append("\n");
        }
        return result.toString();
    }

    public void changeVisibility(Visible visibility) {
        this.calendarVisibility = visibility;
    }

    public void changeFormat(CalendarFormat newFormat) {
        this.format = newFormat;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void addEvent(String eventName, String startTime, String endTime) {
        Event newEvent = new Event(eventName, stringToZonedDateTimeParser(startTime), stringToZonedDateTimeParser(endTime), calendarOwner);
        events.add(newEvent);
    }

    public void removeEvent(int eventId) {
            events.removeIf(event -> event.getEventID() == eventId);
    }

    public Event getEventById(int eventID) {
        return events.stream()
                .filter(event -> event.getEventID() == eventID)
                .findFirst()
                .orElse(null);
    }


    private ZonedDateTime stringToZonedDateTimeParser(String datetime) {
        TimeZone ownerTimeZone = calendarOwner.getAppSettings().getTimeZone();
        return DateTimeUtils.stringToZonedDateTimeParser(datetime, ownerTimeZone);
    }
}
