package io.pivotal.pal.tracker;

import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class TimeEntry {

    private long id ;
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public TimeEntry(long projectId, long userId, LocalDate date, int hours) {

        this.id = 1L;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;

    }

    public TimeEntry(long id, long projectId, long userId, LocalDate date, int hours) {

        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;

    }

    public TimeEntry() {

    }

    @Override
    public boolean equals(Object timeEntry) {
        if (timeEntry == this) {
            return true;
        }

        if (!(timeEntry instanceof TimeEntry)) {
            return false;
        }

        TimeEntry timeEntryObj = (TimeEntry) timeEntry;

        boolean result = Long.compare(id, timeEntryObj.id) == 0
                && Long.compare(projectId, timeEntryObj.projectId) == 0
                && Long.compare(userId, timeEntryObj.userId) == 0
                && date.toString().equals(timeEntryObj.date.toString()) == true
                && Integer.compare(hours, timeEntryObj.hours) == 0;
        return result;
    }
}

