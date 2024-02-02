package com.example.calendarapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorMessage;

    private CalendarAppData calendarAppData;

    public void setCalendarAppData(CalendarAppData calendarAppData) {
        this.calendarAppData = calendarAppData;
    }
    public void loginUser(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User authenticatedUser = calendarAppData.loginUser(username, password);
        
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
        Scene scene = new Scene(fxmlLoader.load(), 898, 642);

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


}
