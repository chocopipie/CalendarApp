package com.example.calendarapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Notes for A3: strategy method
public class MonthlyViewStrategy implements CalendarViewStrategy{
    private final CalendarController calendarController;

    public MonthlyViewStrategy(CalendarController calendarController) {
        this.calendarController = calendarController;
    }
    @Override
    public void drawCalendar() { calendarController.drawMonthCalendar(); }

    // Takes a list of CalendarEventForMonth objects and organizes them into
    // a map where the key is the day of the month,
    // and the value is a list of events for that day.
    @Override
    public Map<Integer, ArrayList<Event>> createCalendarMap(ArrayList<Event> calendarEventsForMonth) {
        Map<Integer, ArrayList<Event>> calendarEventMap = new HashMap<>();

        for (Event event: calendarEventsForMonth) {
            // get the day of month for the event
            int eventDate = event.getStartTime().getDayOfMonth();
            calendarEventMap.computeIfAbsent(eventDate, k -> new ArrayList<>()).add(event);
        }
        return calendarEventMap;
    }

    // Get the event list for current month
    @Override
    public ArrayList<Event> getEventList(ArrayList<Event> events, int month) {
        // create event list for current month
        ArrayList<Event> eventsForMonth = new ArrayList<>();

        for (Event event: events) {
            if (event.getStartTime().getMonth().getValue() == month) {
                eventsForMonth.add(event);
            }
        }
        return eventsForMonth;
    }
}
