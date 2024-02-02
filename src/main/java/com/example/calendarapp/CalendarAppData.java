package com.example.calendarapp;

import java.util.ArrayList;
import java.util.Objects;

public class CalendarAppData implements Authentication {
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
