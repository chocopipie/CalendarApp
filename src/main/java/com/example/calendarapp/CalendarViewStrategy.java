package com.example.calendarapp;

import java.util.ArrayList;
import java.util.Map;

// Notes for A3: strategy method
public interface CalendarViewStrategy {
    void drawCalendar();

    // Takes a list of events objects and organizes them into
    // a map where the key is <the day of the month>/<time of day>/<month of year> based on interface implementation,
    // and the value is a list of events for that day/time/month.
    Map<Integer, ArrayList<Event>> createCalendarMap(ArrayList<Event> calendarEvents);

    // Get the event list for current month/year/day
    ArrayList<Event> getEventList(ArrayList<Event> events, int time);
}
