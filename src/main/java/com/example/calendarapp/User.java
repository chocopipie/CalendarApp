package com.example.calendarapp;

import java.util.ArrayList;

public class User {
    private String username;
    private ArrayList<Event> sharedEvents;
    private ArrayList<AppCalendar> ownedCalendars;
    private AppSettings appSettings;

    public User(String username) {
        this.username = username;
        this.sharedEvents = new ArrayList<Event>();
        this.ownedCalendars = new ArrayList<AppCalendar>();
        this.appSettings = new AppSettings();
    }

    // getters and setters
    public void setUsername(String newUsername) {
        this.username = newUsername;
    }
    public String getUsername() {
        return username;
    }
    public AppSettings getAppSettings() {
        return appSettings;
    }

    public ArrayList<AppCalendar> getOwnedCalendars() {
        return ownedCalendars;
    }

    public ArrayList<Event> getSharedEvents() {
        return sharedEvents;
    }

    // other classes
    public void addCalendar(AppCalendar newCalendar, Visible visibility) {
        newCalendar.setCalendarVisibility(visibility);
        newCalendar.setCalendarOwner(this);
        ownedCalendars.add(newCalendar);
    }

    public void removeCalendarById(int calendarId) {
        ownedCalendars.removeIf(cal -> cal.getCalendarID() == calendarId);
    }

    public void removeCalendar(AppCalendar calendarToRemove) {
        ownedCalendars.remove(calendarToRemove);
    }

    public AppCalendar getCalendarById(int calendarId) {
        return  ownedCalendars.stream()
                .filter(cal -> cal.getCalendarID() == calendarId)
                .findFirst()
                .orElse(null);
    }

    // this method is called when an event is shared to this user
    public void addSharedEvent(Event sharedEvent) {
        sharedEvents.add(sharedEvent);
    }

    // this method is called when a user wants to share an event to other user
    public void shareEvent(int calendarId, int eventId, User targetUser) {
        // retrieve the calendar that has the event to share to other user
        AppCalendar sourceCalendar = getCalendarById(calendarId);

        if (sourceCalendar != null) {
            // retrieve the event in the source calendar
            Event sharedEvent = sourceCalendar.getEventById(eventId);

            if (sharedEvent != null) {
                // share the event with the target user
                // add that event to target user's sharedEvents list
                targetUser.addSharedEvent(sharedEvent);
            }
        }
    }

}
