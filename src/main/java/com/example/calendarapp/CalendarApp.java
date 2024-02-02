package com.example.calendarapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CalendarApp extends Application {

    private static CalendarAppData calendarAppData;

    @Override
    public void start(Stage stage) throws IOException {
        // create the calendarAppData
        createCalendarAppData();

        FXMLLoader loginLoader = new FXMLLoader(CalendarApp.class.getResource("login-view.fxml"));
        Scene loginScene = new Scene(loginLoader.load(), 315, 195);


        LoginController loginController = loginLoader.getController();
        loginController.setCalendarAppData(calendarAppData);
        stage.setTitle("Login");
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
        // Create users
//        User user1 = new User("User1");
//        User user2 = new User("User2");
//
//
//        // Create calendars
//        AppCalendar calendar1 = new GregorianCalendar(Visible.PUBLIC, CalendarFormat.MONTH, user1);
//        AppCalendar calendar2 = new GregorianCalendar(Visible.PRIVATE, CalendarFormat.WEEK, user2);
//        AppCalendar calendar3 = new GregorianCalendar(Visible.PRIVATE, CalendarFormat.WEEK, user2);
//
//        // Add calendars to users
//        user1.addCalendar(calendar1, Visible.PUBLIC);
//        user2.addCalendar(calendar2, Visible.PRIVATE);
//
//        // Add events to calendars
//        calendar1.addEvent("Meeting 1", "2024-01-28T10:00", "2024-01-28T12:00");
//        calendar2.addEvent("Appointment", "2024-01-30T15:00", "2024-01-30T16:30");
//
//        // Share an event from user2's calendar with user1
//        user2.shareEvent(calendar2.getCalendarID(), calendar2.getEvents().get(0).getEventID(), user1);
//
//        calendar1.addEvent("Meeting 2", "2024-02-14T10:00", "2024-02-28T12:00");
//        calendar1.getEventById(3).updateEndTime("2026-02-28T12:00");
//
//        user2.addCalendar(calendar3, Visible.PUBLIC);
//        calendar3.addEvent("School", "2024-02-15T15:00", "2024-02-16T16:30");
//        calendar3.removeEvent(4);
//        user2.removeCalendar(calendar3);
//
//        // Display user calendars and events
//        displayUserDetails(user1);
//        displayUserDetails(user2);
    }

    public void createCalendarAppData() {
        // code to test
        // initialize the calendarApp
        calendarAppData = new CalendarAppData();

        // Create user 1
        User user1 = new User("User1", "123456");

        calendarAppData.addUser(user1);

        // Create calendars 1
        AppCalendar calendar1 = new GregorianCalendar("Calendar 1", Visible.PRIVATE, CalendarFormat.MONTH, user1);

        // Add calendars to users
        user1.addCalendar(calendar1, Visible.PRIVATE);

        // Add events to calendars
        calendar1.addEvent("Meeting 1", "2024-02-28T10:00", "2024-02-28T12:00");
        calendar1.addEvent("Appointment", "2024-01-30T15:00", "2024-01-30T16:30");
        calendar1.addEvent("School", "2024-01-15T15:00", "2024-01-16T16:30");
        calendar1.addEvent("Hangout", "2024-01-15T17:00", "2024-01-16T17:30");


        // Create calendar 2
        AppCalendar calendar2 = new GregorianCalendar("Calendar 2", Visible.PRIVATE, CalendarFormat.MONTH, user1);

        // Add calendars to users
        user1.addCalendar(calendar2, Visible.PRIVATE);

        // Add events to calendars
        calendar2.addEvent("Go shopping", "2024-02-15T08:00", "2024-02-28T10:00");
        calendar2.addEvent("Meeting with Jones", "2024-01-31T15:00", "2024-01-31T16:30");
        calendar2.addEvent("Lab Meeting", "2024-02-05T15:00", "2024-02-05T16:30");
        calendar2.addEvent("Conference", "2024-02-15T17:00", "2024-02-16T17:30");


        // Create user 2
        User user2 = new User("User2", "123456");

        calendarAppData.addUser(user2);

        // Create calendars 1
        AppCalendar calendar3 = new GregorianCalendar("My schedule", Visible.PRIVATE, CalendarFormat.MONTH, user2);

        // Add calendars to users
        user2.addCalendar(calendar3, Visible.PRIVATE);

        // Add events to calendars
        calendar3.addEvent("Meeting 1", "2024-02-28T10:00", "2024-02-28T12:00");
        calendar3.addEvent("Appointment", "2024-01-30T15:00", "2024-01-30T16:30");
        calendar3.addEvent("School", "2024-01-15T15:00", "2024-01-16T16:30");
        calendar3.addEvent("Hangout", "2024-02-15T17:00", "2024-02-16T17:30");
        calendar3.addEvent("Dance class", "2024-02-13T15:00", "2024-02-13T16:30");
        calendar3.addEvent("Work", "2024-02-15T15:00", "2024-02-15T16:30");
        calendar3.addEvent("Hangout", "2024-02-16T17:00", "2024-02-16T17:30");


        // Create calendar 2
        AppCalendar calendar4 = new GregorianCalendar("Empty Calendar", Visible.PRIVATE, CalendarFormat.MONTH, user2);

        // Add calendars to users
        user2.addCalendar(calendar4, Visible.PRIVATE);

        // test code on CLI
        // user2 shares the first event on calendar3 to user1
        user2.shareEvent(calendar3.getCalendarID(), calendar3.getEvents().get(0).getEventID(), user1);
        System.out.println(user1.getSharedEvents().get(0).toString());

    }
}