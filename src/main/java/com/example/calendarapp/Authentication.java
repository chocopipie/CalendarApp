package com.example.calendarapp;

public interface Authentication {
    User authenticateUser(String username, String password);

    void logoutUser();
}
