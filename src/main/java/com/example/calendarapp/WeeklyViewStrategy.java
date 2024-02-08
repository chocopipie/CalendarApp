package com.example.calendarapp;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WeeklyViewStrategy implements CalendarViewStrategy {
    private final CalendarController calendarController;
    private static int currentYear;

    public WeeklyViewStrategy(CalendarController calendarController) {
        this.calendarController = calendarController;
    }
    @Override
    public void drawCalendar() {
        calendarController.drawWeekCalendar();
    }

    // Takes a list of CalendarEventForWeek objects and organizes them into
    // a map where the key is the day of the week,
    // and the value is a list of events for that day.
    @Override
    public Map<Integer, ArrayList<Event>> createCalendarMap(ArrayList<Event> calendarEventsForWeek) {
        Map<Integer, ArrayList<Event>> calendarEventMap = new HashMap<>();

        for (Event event: calendarEventsForWeek) {
            // get the day of week for the event
            DayOfWeek eventDayOfWeek = event.getStartTime().getDayOfWeek();
            int eventDayValue = eventDayOfWeek.getValue();
            calendarEventMap.computeIfAbsent(eventDayValue, k -> new ArrayList<>()).add(event);
        }

        return calendarEventMap;
    }

    // Get the event list for current week
    @Override
    public ArrayList<Event> getEventList(ArrayList<Event> events, int weekOfYear) {
        // create event list for current week
        ArrayList<Event> eventsForWeek = new ArrayList<>();

        for (Event event: events) {
            ZonedDateTime eventStartTime = event.getStartTime();
            int eventWeekOfYear = eventStartTime.get(WeekFields.ISO.weekOfYear());
            int eventYear = eventStartTime.getYear();

            if (eventWeekOfYear == weekOfYear && eventYear == currentYear) {
                eventsForWeek.add(event);
            }
        }
        return eventsForWeek;
    }

    // call this method first to get the current year to get event list for week
    public static void setCurrentYear(int year) {
        currentYear = year;
    }
}
