package com.example.usuario.tasker.pojoObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPojo {

    @SerializedName("username")
    @Expose
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}