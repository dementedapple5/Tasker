package com.example.usuario.tasker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.usuario.tasker.utilities.Utilities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dani on 31/10/2017.
 */

public class ConnectionSQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tasker_db";
    public static final int DATABASE_VERSION = 1;

    public ConnectionSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ConnectionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilities.CREATE_USER_TABLE);
        db.execSQL(Utilities.CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Utilities.DROP_TASK_TABLE);
        db.execSQL(Utilities.DROP_USER_TABLE);
        onCreate(db);
    }

    //////////////////////////////////////////////
    /////////-user
    ////////////////////////////////////////////////

    //Insert User
    public void createUser(String username, String pass, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilities.USER_USERNAME,username);
        values.put(Utilities.USER_NAME,name);
        values.put(Utilities.USER_PASSWORD,pass);
        db.insert(Utilities.USER_TABLE,null,values);
        db.close();
    }

    //Retrieve Users
    public HashMap<String,String> retrieveUsers(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {Utilities.USER_USERNAME,Utilities.USER_PASSWORD};
        Cursor c = db.query(Utilities.USER_TABLE,columns,null,null,null,null,null);
        c.moveToFirst();
        HashMap<String,String> users = new HashMap<>();
        while (!c.isAfterLast()){
            String username = c.getString(c.getColumnIndex(Utilities.USER_USERNAME));
            String password = c.getString(c.getColumnIndex(Utilities.USER_PASSWORD));
            users.put(username,password);
            c.moveToNext();
        }
        c.close();
        return users;
    }



    ////////////////////////////////////////////////////////////
    //////////-tasks
    ///////////////////////////////////////////////////////////


}
