package com.chocs.taskmanager.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    private int id;
    private int typeId;
    private int subjectId;
    private String description;
    private LocalDate date;

    public Task() {}

    public Task(int id, int type, int subjectId, String description, LocalDate date) {
        this.id = id;
        this.typeId = type;
        this.subjectId = subjectId;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int type) {
        this.typeId = type;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int calcDaysLeft() {
        return date.getDayOfYear() - LocalDate.now().getDayOfYear();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Task [" + id + ", "  + subjectId + ", " + description + ", " +
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                ", " + calcDaysLeft() + " days left]";
    }
}
