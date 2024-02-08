package com.example.calendarapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

// Notes for A3: singleton method
public class CalendarAppData {
    private static CalendarAppData calendarAppData;
    private ArrayList<User> users;

    private CalendarAppData() {
        this.users = new ArrayList<>();
    }

    public static CalendarAppData getInstance() {
        if (calendarAppData == null) {
            calendarAppData = new CalendarAppData();
        }
        return calendarAppData;
    }
    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getUserByUsername(String userName) {
        return users.stream()
                .filter(user -> Objects.equals(user.getUsername(), userName))
                .findFirst()
                .orElse(null);
    }

}
