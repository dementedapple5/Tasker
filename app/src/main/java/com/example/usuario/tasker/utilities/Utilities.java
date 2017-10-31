package com.example.usuario.tasker.utilities;

/**
 * Created by Dani on 31/10/2017.
 */

public class Utilities {
    public static final String CREATE_USER_TABLE = "CREATE TABLE users(username varchar(30) primary key, name varchar(30), password varchar(30))";
    public static final String CREATE_TASK_TABLE = "CREATE TABLE tasks(id integer primary key, title varchar(30), comment text, description text, state boolean, attendant varchar(30), priority integer, creationDate datetime)";
    public static final String DROP_TASK_TABLE = "DROP TABLE IF EXISTS tasks";
    public static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS users";
    public static final String DATABASE_NAME = "tasker_db";
    public static final int DATABASE_VERSION = 1;



    public static final String USER_TABLE = "users";

    public static final String USER_ID = "id";
    public static final String USER_USERNAME = "username";
    public static final String USER_NAME = "name";
    public static final String USER_PASSWORD = "password";

}
