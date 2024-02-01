package com.example.calendarapp;

import java.util.Calendar;

public class GregorianCalendar extends AppCalendar{
    public GregorianCalendar(String calendarName, Visible visibility, CalendarFormat format, User owner) {
        // obtaining the current date and time using the Gregorian calendar.
        super(calendarName,visibility, format, java.util.GregorianCalendar.getInstance(), owner);
    }
}
