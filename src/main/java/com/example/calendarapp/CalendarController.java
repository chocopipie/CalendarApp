package com.example.calendarapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;


public class CalendarController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    private ArrayList<AppCalendar> calendarList;
    private User currentUser;
    private AppCalendar currentCalendar;
    private CalendarViewStrategy viewStrategy; // view strategy for current calendar (day/month/year)

    // store the reference to the pop-up stage
    private Stage addEventPopupStage;
    private Stage editEventPopupStage;
    private Stage settingsStage;
    private Stage loginStage;

    @FXML
    private ToggleGroup calendarToggleGroup;

    @FXML
    private VBox calendarRadioBox; // Add VBox to hold radio buttons

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private Text hyphen;

    @FXML
    private FlowPane calendar;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    @FXML
    private ToggleButton visibility;


    // METHODS FOR SETTING UP THE SCENE

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        today = ZonedDateTime.now();
        dateFocus = ZonedDateTime.now();
        // set default viewStrategy to month view
        viewStrategy = new MonthlyViewStrategy(this);
    }

    // this method is called when the app starts
    public void setUpUserCalendarData(User currentUser) {
        // setup
        this.currentUser = currentUser;
        this.calendarList = currentUser.getOwnedCalendars();
        this.currentCalendar = calendarList.get(0);

        // setup visibility button
        setUpVisibility();
        // Set up a listener for the visibility toggle button
        visibility.setOnAction(event -> handleVisibilityToggle());

        // Set up calendar list and display current calendar
        setUpCalendarRadioButtons();

        // draw the calendar
        drawMonthCalendar();
    }

    // this method sets the view strategy (day/month/year)
    public void setViewStrategy(CalendarViewStrategy viewStrategy) {
        this.viewStrategy = viewStrategy;
    }

    private void setUpVisibility() {
        String visibilityText = currentCalendar.calendarVisibility.toString();
        // set visibility button to current calendar visibility
        visibility.setText(visibilityText);

        // set button color
        if (Objects.equals(visibilityText, "PUBLIC"))
            visibility.setStyle(
                    "-fx-background-color: #b0c4b1;" +
                            "-fx-text-fill: black");
        else
            visibility.setStyle("-fx-background-color: #9e2a2b;" +
                                    "-fx-text-fill: white;");
    }

    private void setUpCalendarRadioButtons() {
        calendarToggleGroup = new ToggleGroup();
        for (AppCalendar calendar : calendarList) {
            RadioButton radioButton = new RadioButton(calendar.getCalendarName());
            radioButton.setToggleGroup(calendarToggleGroup); // add to toggle group
            radioButton.setOnAction(event -> handleCalendarRadioButton(calendar));

            calendarRadioBox.getChildren().add(radioButton);
        }

        // Select the first calendar by default
        calendarToggleGroup.selectToggle(calendarToggleGroup.getToggles().get(0));
    }

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    // METHODS TO HANDLE EVENTS

    @FXML
    public void changeToWeekView() {
        viewStrategy = new WeeklyViewStrategy(this); // Notes for A3: strategy method

        prevButton.setOnAction(event -> moveToPrevWeek(null));
        nextButton.setOnAction(event -> moveToNextWeek(null));
        calendar.getChildren().clear();
        viewStrategy.drawCalendar();  // Notes for A3: strategy method
    }
    @FXML
    public void changeToMonthView() {
        viewStrategy = new MonthlyViewStrategy(this); // Notes for A3: strategy method

        prevButton.setOnAction(this::moveToPrevMonth);
        nextButton.setOnAction(this::moveToNextMonth);
        calendar.getChildren().clear();
        viewStrategy.drawCalendar();  // Notes for A3: strategy method
    }

    @FXML
    public void handleVisibilityToggle() {
        // Handle the logic to switch between private and public calendar visibility
        if (Objects.equals(visibility.getText(), "PUBLIC")) {
            currentCalendar.setCalendarVisibility(Visible.PRIVATE);

        } else {
            currentCalendar.setCalendarVisibility(Visible.PUBLIC);
        }

        setUpVisibility();
    }

    @FXML
    private void handleCalendarRadioButton(AppCalendar selectedCalendar) {
        // Handle the logic when a calendar radio button is selected
        currentCalendar = selectedCalendar;

        calendar.getChildren().clear();
        // re-draw the calendar with the selected calendar data
        viewStrategy.drawCalendar();
    }

    @FXML
    void moveToPrevMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        // draw calendar for new month
        drawMonthCalendar();
    }

    @FXML
    void moveToNextMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        // draw calendar for new month
        drawMonthCalendar();
    }

    @FXML
    void moveToPrevWeek(ActionEvent event) {
        dateFocus = dateFocus.minusWeeks(1);
        calendar.getChildren().clear();
        // draw calendar for new month
        drawWeekCalendar();
    }

    @FXML
    void moveToNextWeek(ActionEvent event) {
        dateFocus = dateFocus.plusWeeks(1);
        calendar.getChildren().clear();
        // draw calendar for new month
        drawWeekCalendar();
    }

    @FXML
    void logOut(ActionEvent event) {
        // close the current calendar Stage
        Stage stage = (Stage) calendar.getScene().getWindow();
        stage.close();

        // show the login stage
        if (loginStage != null) {
            loginStage.show();
        }
    }

    // Clicking addEventButton calls showAddEventPopup
    @FXML
    private void showAddEventPopup() throws IOException {
        // Load the FXML file for the pop-up
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEventPopUp.fxml"));
        Parent popupContent = loader.load();
        Region root = (Region) popupContent;

        // Create a new Stage (pop-up window)
        addEventPopupStage = new Stage();
        addEventPopupStage.setTitle("Add New Event");

        // Set the FXML controller for the pop-up
        AddEventPopUpController popupController = loader.getController();
        popupController.setMainController(this);

        // Notes for A3: replaced magic literals
        Scene popupScene = new Scene(popupContent, root.getPrefWidth(), root.getPrefHeight());

        //set theme for popup scene that matches current theme
        String currentThemeStyleSheet = currentUser.getAppSettings().getCurrentThemeStylesheet();
        popupScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(currentThemeStyleSheet)).toExternalForm());

        addEventPopupStage.setScene(popupScene);

        // Show the pop-up window
        addEventPopupStage.show();
    }

    // Clicking each event button calls configureEventButton
    // configureEventButton will call this method to open up the edit event popup
    @FXML
    private void showEditEventPopup(Event event) throws IOException {
        // Load the FXML file for the pop-up
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditEventPopUp.fxml"));
        Parent popupContent = loader.load();
        Region root = (Region) popupContent;

        // Create a new Stage (pop-up window)
        editEventPopupStage = new Stage();
        editEventPopupStage.setTitle("Edit Event");

        // Set the FXML controller for the pop-up
        EditEventPopUpController popupController = loader.getController();
        popupController.setMainController(this);

        // pass the event data to the popupController
        popupController.setEventData(event);

        // Notes for A3: replaced magic literals
        Scene popupScene = new Scene(popupContent, root.getPrefWidth(), root.getPrefHeight());

        //set theme for popup scene that matches current theme
        String currentThemeStyleSheet = currentUser.getAppSettings().getCurrentThemeStylesheet();
        popupScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(currentThemeStyleSheet)).toExternalForm());
        editEventPopupStage.setScene(popupScene);

        // Show the pop-up window
        editEventPopupStage.show();
    }

    // Clicking settingsButton calls handleSettingsButton
    @FXML
    void handleSettingsButton() throws IOException {
        // Load the FXML file for the pop-up
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        Parent popupContent = loader.load();
        Region root = (Region) popupContent;

        // Create a new Stage (pop-up window)
        settingsStage = new Stage();
        settingsStage.setTitle("Settings");

        // Set the FXML controller for the pop-up
        SettingsController settingsController = loader.getController();

        // setup for setting scene
        settingsController.setMainController(this, calendar.getScene());
        settingsController.setUserSetting(currentUser);

        // Notes for A3: replaced magic literals
        Scene popupScene = new Scene(popupContent, root.getPrefWidth(), root.getPrefHeight());
        settingsStage.setScene(popupScene);

        // Show the pop-up window
        settingsStage.show();
    }

    // METHODS TO CONTROL LOGICS

    // creates a visual representation of calendar activities,
    // displaying up to two activities and providing an ellipsis for additional activities
    // The visual representation is styled and added to a container (StackPane)
    private void CreateCalendarEvent(ArrayList<Event> calendarEvents, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox eventBox = new VBox();

        // Display up to 2 activities
        for (int k = 0; k < Math.min(calendarEvents.size(),2); k++) {
            Event currentEvent = calendarEvents.get(k);

            Button eventButton = createStyledButton(currentEvent.getName());
            configureEventButton(eventButton, currentEvent); // Method to configure button behavior

            eventBox.getChildren().add(eventButton);
        }

        //
        if (calendarEvents.size() > 2) {
            Text moreActivities = new Text("...");
            eventBox.getChildren().add(moreActivities);
        }

        eventBox.setTranslateY((rectangleHeight / 2) * 0.20);
        eventBox.setMaxWidth(rectangleWidth * 0.8);
        eventBox.setMaxHeight(rectangleHeight * 0.65);
        stackPane.getChildren().add(eventBox);
    }

    // Method to configure individual event buttons
    private void configureEventButton(Button button, Event event) {
        button.setOnAction(actionEvent -> {
            // On Button click for an event
            System.out.println(event.getName());
            // You can add more behavior as needed
            try {
                showEditEventPopup(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void drawMonthCalendar() {
        hyphen.setVisible(false); // rm hyphen to display MONTH YEAR text
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        // hgap is the amount of horizontal space btw each node in horizontal flowpane
        double spacingH = calendar.getHgap();
        // The amount of space between rows in a horizontal flowpane.
        double spacingV = calendar.getVgap();

        ArrayList<Event> eventsList = currentCalendar.getEvents();
        int month = dateFocus.getMonth().getValue();
        // list of events in current month
        ArrayList<Event> calendarEventsForMonth = viewStrategy.getEventList(eventsList, month);
        // create map of the above list
        Map<Integer, ArrayList<Event>> eventForMonthMap = viewStrategy.createCalendarMap(calendarEventsForMonth);

        // calculate max date for month
        int monthMaxDate = dateFocus.getMonth().maxLength();
        // in case of leap year
        if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
            monthMaxDate = 28;
        }

        // represents the day of the week for the first day of the month.
        // determine the starting column in a calendar grid where the first day of the month should be placed.
        int firstDayOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1,0,0,0,0,dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth =(calendarWidth/7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight/6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j+1)+(7*i);
                if(calculatedDate > firstDayOffset){
                    int currentDate = calculatedDate - firstDayOffset;
                    if(currentDate <= monthMaxDate){
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        ArrayList<Event> eventsForCurrentDate = eventForMonthMap.get(currentDate);
                        if(eventsForCurrentDate != null){
                            CreateCalendarEvent(eventsForCurrentDate, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    public void drawWeekCalendar() {
        // replace default #### - #### from Month Year into
        // first and last date of week
        hyphen.setVisible(true); // rm hyphen to display MMMM YYYY
        TemporalField day = WeekFields.ISO.dayOfWeek();
        ZonedDateTime firstDateOfWeek = dateFocus.with(day, day.range().getMinimum());
        year.setText(firstDateOfWeek.format(DateTimeFormatter.ofPattern("MM/dd")));
        ZonedDateTime lastDateOfWeek = firstDateOfWeek.plusDays(6);
        month.setText(lastDateOfWeek.format(DateTimeFormatter.ofPattern("MM/dd")));


        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 0.5;
        double spacingH = calendar.getHgap(); // Horizontal spacing
        double spacingV = calendar.getVgap(); // Vertical spacing

        // Get the events list for the current week
        ArrayList<Event> eventsList = currentCalendar.getEvents();
        int currentWeek = dateFocus.get(WeekFields.ISO.weekOfWeekBasedYear());
        int currentHour = dateFocus.getHour();

        WeeklyViewStrategy.setCurrentYear(dateFocus.getYear());
        // list of events in current week
        ArrayList<Event> calendarEventsForWeek = viewStrategy.getEventList(eventsList, currentWeek);
        // Create a map to store events for each day of the week
        Map<Integer, ArrayList<Event>> eventsForWeekMap = viewStrategy.createCalendarMap(calendarEventsForWeek);
        //System.out.println(eventsForWeekMap);

        // Calculate the dimensions of each cell in the calendar grid
        double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
        double rectangleHeight = (calendarHeight / 24) - strokeWidth - spacingV;


        // Iterate over each hour of the day
        for (int hour = 0; hour < 24; hour++) {
            // Iterate over each day of the week
            for (int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.GRAY);
                rectangle.setStrokeWidth(strokeWidth);
                rectangle.setWidth(rectangleWidth);
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                // Calculate the current time (hour) for this cell
                int currentHourOfDay = (hour + currentHour) % 24;


                // Get the events for the current day of the week
                ArrayList<Event> eventsForCurrentDay = eventsForWeekMap.get(dayOfWeek);
                System.out.println(eventsForCurrentDay);

                if(eventsForCurrentDay != null){
                    // Display events for the current day and hour
                    for (Event event : eventsForCurrentDay) {
                        ZonedDateTime eventStartTime = event.getStartTime();
                        ZonedDateTime eventEndTime = event.getEndTime();
                        int eventStartHour = eventStartTime.getHour();
                        int eventEndHour = eventEndTime.getHour();

                        if (currentHourOfDay >= eventStartHour && currentHourOfDay < eventEndHour) {
                            CreateCalendarEvent(eventsForCurrentDay, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                }


                calendar.getChildren().add(stackPane);
            }

        }
    }


    public void addEvent(String eventName, LocalDate eventDate, String startTime, String endTime) {

        //currentCalendar.addEvent("Hangout", "2024-01-15T17:00", "2024-01-16T17:30");
        String formattedDate = eventDate.toString();
        String formattedStartTime = formattedDate + "T" + startTime;
        String formatterEndTime = formattedDate + "T" + endTime;
        currentCalendar.addEvent(eventName, formattedStartTime, formatterEndTime);

        // re-draw the updated calendar
        calendar.getChildren().clear();
        viewStrategy.drawCalendar();

        handleClosePopUp(); // Call the method to handle pop-up closing
    }

    public void editEvent(int eventID, String eventName, String eventDate, String startTime, String endTime) {

        String formattedStartTime = eventDate + "T" + startTime;
        String formatterEndTime = eventDate + "T" + endTime;

        Event eventToEdit = currentCalendar.getEventById(eventID);
        eventToEdit.setName(eventName);
        eventToEdit.updateStartTime(formattedStartTime);
        eventToEdit.updateEndTime(formatterEndTime);

        // re-draw the updated calendar
        calendar.getChildren().clear();
        viewStrategy.drawCalendar();

        handleClosePopUp(); // Call the method to handle pop-up closing
    }


    public void deleteEvent(int eventID) {
        currentCalendar.removeEvent(eventID);

        // re-draw the updated calendar
        calendar.getChildren().clear();
        viewStrategy.drawCalendar();

        handleClosePopUp(); // Call the method to handle pop-up closing
    }

    public void saveSettings() {
        // re-draw the updated calendar
        calendar.getChildren().clear();
        viewStrategy.drawCalendar();

        handleClosePopUp();
    }

    public void handleClosePopUp() {
        // Close the pop-up if it's open
        if (addEventPopupStage != null && addEventPopupStage.isShowing()) {
            addEventPopupStage.close();
        }
        else if (editEventPopupStage != null && editEventPopupStage.isShowing()) {
            editEventPopupStage.close();
        }
        else if (settingsStage != null && settingsStage.isShowing()) {
            settingsStage.close();
        }
    }

    // METHODS FOR STYLING

    // Method to create styled buttons
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: #4CAF50;" + // Green background color
                        "-fx-text-fill: white;" + // White text color
                        "-fx-font-weight: bold;" + // Bold text
                        "-fx-padding: 5px 10px;" + // Padding around the text
                        "-fx-border-radius: 5px;" + // Rounded corners
                        "-fx-cursor: HAND;" // Change cursor to hand on hover
        );
        return button;
    }

}