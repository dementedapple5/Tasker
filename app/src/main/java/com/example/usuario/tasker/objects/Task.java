package com.example.usuario.tasker.objects;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Usuario on 26/10/2017.
 */

public class Task implements Comparable<Task> {
    private String title;
    private String attendant;
    private String comment;
    private String description;
    private int priority;
    private String creationDate;
    private boolean state;

    public Task(String title, String attendant, String comment, String description, int priority) {
        this.title = title;
        this.attendant = attendant;
        this.comment = comment;
        this.description = description;
        this.priority = priority;
        this.creationDate = setCreationDate();
        this.state = false;
    }

    public Task(String title, String attendant, String comment, String description, int priority, String date, boolean state) {
        this.title = title;
        this.attendant = attendant;
        this.comment = comment;
        this.description = description;
        this.priority = priority;
        this.creationDate = date;
        this.state = state;
    }

    private static String setCreationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }

    public String getTitle() {
        return title;
    }

    public String getAttendant() {
        return attendant;
    }

    public String getComment() {
        return comment;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public boolean isState() {
        return state;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", attendant='" + attendant + '\'' +
                ", comment='" + comment + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", creationDate='" + creationDate + '\'' +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (priority != task.priority) return false;
        if (state != task.state) return false;
        if (title != null ? !title.equals(task.title) : task.title != null) return false;
        if (attendant != null ? !attendant.equals(task.attendant) : task.attendant != null)
            return false;
        if (comment != null ? !comment.equals(task.comment) : task.comment != null) return false;
        if (description != null ? !description.equals(task.description) : task.description != null)
            return false;
        return creationDate != null ? creationDate.equals(task.creationDate) : task.creationDate == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (attendant != null ? attendant.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + priority;
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (state ? 1 : 0);
        return result;
    }


    @Override
    public int compareTo(@NonNull Task task) {
        int comparePrior = this.getPriority() - (task.getPriority());
        return comparePrior == 0 ? this.getTitle().compareTo(task.getTitle()) : comparePrior;
    }
}
