package com.example.usuario.tasker.objects;

/**
 * Created by Usuario on 26/10/2017.
 */

public class User {
    private String username;
    private String name;
    private String password;
    private boolean admin;

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.admin = false;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return admin;
    }


}
