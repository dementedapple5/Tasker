package com.example.usuario.tasker.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dani on 31/10/2017.
 */

public class ConnectionSQLiteHelper extends SQLiteOpenHelper {
    private final String CREATE_USER_TABLE = "CREATE TABLE users(id integer primary key, username varchar(30), name varchar(30), password varchar(30))";
    private final String CREATE_TASK_TABLE = "CREATE TABLE tasks(id integer primary key, title varchar(30), comment text, description text, state boolean, attendant varchar(30), priority integer, creationDate datetime)";
    private final String DROP_TASK_TABLE = "DROP TABLE IF EXISTS tasks";
    private final String DROP_USER_TABLE = "DROP TABLE IF EXISTS users";
    public static final String DATABASE_NAME = "tasker_db";
    public static final int DATABASE_VERSION = 1;


    public ConnectionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ConnectionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TASK_TABLE);
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }
}
