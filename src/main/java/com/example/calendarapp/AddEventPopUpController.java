package com.example.calendarapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddEventPopUpController {
    private CalendarController mainController;

    @FXML
    private TextField eventNameField;

    @FXML
    private DatePicker dateDatePicker;

    @FXML
    private TextField startTimeField;

    @FXML
    private TextField endTimeField;

    @FXML
    private Button addButton;

    public void setMainController(CalendarController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        // Get values from the input fields
        String eventName = eventNameField.getText();
        LocalDate eventDate = dateDatePicker.getValue();
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();

        // Validate input (you may want to add more thorough validation)
        if (eventName.isEmpty() || eventDate == null || startTime.isEmpty() || endTime.isEmpty()) {
            // Handle invalid input (show an error message, etc.)
            System.out.println("Please fill in all fields.");
        } else {
            // Call the method in the main controller to add the event
            mainController.addEvent(eventName, eventDate, startTime, endTime);
        }
    }
}
