package com.chocs.taskmanager.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    private int id;
    private TaskTypes type;
    private Subject subject;
    private String description;
    private int daysLeft;
    private LocalDate date;

    public Task() {}

    public Task(int id, TaskTypes type, Subject subject, String description, LocalDate date) {
        this.id = id;
        this.type = type;
        this.subject = subject;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskTypes getType() {
        return type;
    }

    public void setType(TaskTypes type) {
        this.type = type;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void calcDaysLeft() {
        daysLeft = date.getDayOfYear() - LocalDate.now().getDayOfYear();

        if (daysLeft < 0) {
            daysLeft += 365;
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return type + " [" + subject + ", " + description + ", " +
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                ", " + daysLeft + " days left]";
    }
}
