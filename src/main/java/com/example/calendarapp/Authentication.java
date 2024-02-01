package com.example.calendarapp;

public interface Authentication {
    User loginUser(String username, String password);

    void logoutUser();
}
