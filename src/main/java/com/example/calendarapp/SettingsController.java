package com.example.calendarapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SettingsController implements Initializable {
    private CalendarController mainController;
    private Scene mainScene;

    private User currentUser;

    @FXML
    private ComboBox<String> timeZoneComboBox;
    @FXML
    private RadioButton darkThemeRadioButton;
    @FXML
    private RadioButton lightThemeRadioButton;
    @FXML
    private Button saveButton;
    @FXML
    private ToggleGroup themeToggleGroup;

    @FXML
    private VBox settingsVBox; // Make sure to match the ID with the one in your FXML


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set padding for the VBox programmatically
        settingsVBox.setPadding(new Insets(10, 30, 30, 30));

        // Set up ToggleGroup for theme radio buttons
        themeToggleGroup = new ToggleGroup();
        darkThemeRadioButton.setToggleGroup(themeToggleGroup);
        lightThemeRadioButton.setToggleGroup(themeToggleGroup);

        // Initialize the ComboBox with time zones (you need to populate it with actual time zones)
        List<String> timeZones = Arrays.asList(TimeZone.getAvailableIDs());
        timeZoneComboBox.getItems().addAll(timeZones);
    }
    public void setUserSetting(User currentUser) {
        this.currentUser = currentUser;

        // set current user's theme setting
        if (Objects.equals(currentUser.getAppSettings().getTheme().toString(), "LIGHT")) {
            lightThemeRadioButton.setSelected(true);
        } else  {
            darkThemeRadioButton.setSelected(true);
        }

        // set current user's timezone
        timeZoneComboBox.setValue(currentUser.getAppSettings().getTimeZone().getID());
    }

    public void setMainController(CalendarController mainController, Scene mainScene) {

        this.mainController = mainController;
        this.mainScene = mainScene;

    }

    @FXML
    private void saveSettings() throws IOException {
        // Handle saving settings logic here

//        String selectedTimeZone = timeZoneComboBox.getValue();
//        String selectedTheme = themeComboBox.getValue();
//
//        System.out.println("Selected Time Zone: " + selectedTimeZone);
//        System.out.println("Selected Theme: " + selectedTheme);

        // Apply the selected settings to your application
        // You might want to store these settings for future sessions
        changeTheme();
        changeTimeZone();

        mainController.saveSettings();
    }

    private void changeTheme() {
        //RadioButton selectedTheme = (RadioButton) themeToggleGroup.getSelectedToggle();

        //System.out.println(selectedTheme);
        // change the Theme setting for current user in logic
        if (lightThemeRadioButton.isSelected()) {
            currentUser.getAppSettings().setTheme(Theme.LIGHT);
            applyThemeStyles("Light.css");
        } else if (darkThemeRadioButton.isSelected()) {
            currentUser.getAppSettings().setTheme(Theme.DARK);
            applyThemeStyles("Dark.css");
        }
    }

    private void changeTimeZone() {
        // Get the selected time zone from the combo box
        String selectedTimeZone = timeZoneComboBox.getValue();

        // Update the user's time zone setting
        currentUser.getAppSettings().setTimeZone(TimeZone.getTimeZone(selectedTimeZone));

        // Iterate through each calendar and update events' time zones
        for (AppCalendar calendar : currentUser.getOwnedCalendars()) {
            for (Event event : calendar.getEvents()) {
                // Update the event's time zone
                event.updateTimeZone(selectedTimeZone);
            }
        }
    }

    private void applyThemeStyles(String themeStylesheet) {
        // Get the scene of hello-view.fxml and apply the theme stylesheet
        mainScene.getStylesheets().clear();
        mainScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(themeStylesheet)).toExternalForm());
    }
}
