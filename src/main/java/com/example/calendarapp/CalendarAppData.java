package com.example.calendarapp;

import java.util.ArrayList;
import java.util.Objects;

public class CalendarAppData {
    private ArrayList<User> users;

    public CalendarAppData() {
        this.users = new ArrayList<>();
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
