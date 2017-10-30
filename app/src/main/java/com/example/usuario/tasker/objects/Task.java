package com.example.usuario.tasker.objects;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Usuario on 26/10/2017.
 */

public class Task {
    private String title;
    private User attendant;
    private String comment;
    private String description;
    private int priority;
    private String creationDate;
    private boolean state;

    public Task(String title, User attendant, String comment, String description, int priority) {
        this.title = title;
        this.attendant = attendant;
        this.comment = comment;
        this.description = description;
        this.priority = priority;
        this.creationDate = setCreationDate();
        this.state = false;
    }

    private static String setCreationDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }

}
