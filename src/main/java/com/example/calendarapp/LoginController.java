package com.example.calendarapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class LoginController implements Authentication{
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorMessage;

    private ArrayList<User> appUsersList;

    public void setCalendarAppData() {
        this.appUsersList = CalendarAppData.getInstance().getUsers();
    }
    public void loginUser(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User authenticatedUser = authenticateUser(username, password);

        usernameField.clear();
        passwordField.clear();

        if (authenticatedUser != null) {
            openCalendarScene(authenticatedUser);
        } else {
            errorMessage.setText("Incorrect username or password.");
        }
    }

    private void openCalendarScene(User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CalendarApp.class.getResource("hello-view.fxml"));
        Parent loaderContent = fxmlLoader.load();
        Region root = (Region) loaderContent;
        Scene scene = new Scene(loaderContent, root.getPrefWidth(), root.getPrefHeight());

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Light.css")).toExternalForm());
        CalendarController calendarController = fxmlLoader.getController();
        calendarController.setUpUserCalendarData(user);
        calendarController.setLoginStage((Stage)(loginButton.getScene().getWindow()));

        Stage stage = new Stage();
        stage.setTitle("CalendarApp");
        stage.setScene(scene);
        stage.show();

        // Close the login window if needed
        ((Stage) loginButton.getScene().getWindow()).close();
    }


    @Override
    public User authenticateUser(String username, String password) {
        // authenticate user
        for (User user: appUsersList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void logoutUser() {

    }
}
