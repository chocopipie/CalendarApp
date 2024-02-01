package com.example.calendarapp;
import java.util.TimeZone;

public class AppSettings {
    private TimeZone timeZone;
    private Theme theme;

    public AppSettings() {
        // set default values
        this.timeZone = TimeZone.getDefault();
        this.theme = Theme.LIGHT;
    }

    // getters and setters
    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public Theme getTheme() {
        return theme;
    }

    public String getCurrentThemeStylesheet() {
        if (theme == Theme.LIGHT)
            return "Light.css";
        return "Dark.css";
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
