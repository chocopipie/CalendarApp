package com.example.calendarapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EditEventPopUpController {
    private CalendarController mainController;

    @FXML
    private TextField eventNameField;

    @FXML
    private Text eventDateText;

    @FXML
    private TextField startTimeField;

    @FXML
    private TextField endTimeField;

    @FXML
    private Button updateButton;

    private static int eventID;

    public void setMainController(CalendarController mainController) {
        this.mainController = mainController;
    }

    public void setEventData(Event event) {
        // get event ID
        eventID = event.getEventID();

        // Set the initial values in TextFields based on the current event
        eventNameField.setText(event.getName());
        eventDateText.setText(event.getStartTime().toLocalDate().toString());

        String startTime = event.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        String endTime = event.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        startTimeField.setText(startTime);
        endTimeField.setText(endTime);
    }

    public void handleUpdateButton() {
        // Get values from the input fields
        String eventName = eventNameField.getText();
        String eventDate = eventDateText.getText();
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();

        // Validate input (you may want to add more thorough validation)
        if (eventName.isEmpty() || eventDate == null || startTime.isEmpty() || endTime.isEmpty()) {
            // Handle invalid input (show an error message, etc.)
            System.out.println("Please fill in all fields.");
        } else {
            // Call the method in the main controller to add the event
            mainController.editEvent(eventID, eventName, eventDate, startTime, endTime);
        }
    }

    public void handleDeleteEventButton() {
        mainController.deleteEvent(eventID);
    }
}
