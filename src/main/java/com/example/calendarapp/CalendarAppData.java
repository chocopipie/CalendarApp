package com.example.calendarapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class CalendarAppData implements Authentication {
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

    @Override
    public User loginUser(String username, String password) {
        // authenticate user
        for (User user: users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void logoutUser() {
        // implement later
    }
}
