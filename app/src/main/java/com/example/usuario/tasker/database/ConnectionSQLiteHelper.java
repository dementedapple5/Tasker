package com.example.usuario.tasker.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.usuario.tasker.utilities.Utilities;

/**
 * Created by Dani on 31/10/2017.
 */

public class ConnectionSQLiteHelper extends SQLiteOpenHelper {



    public ConnectionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
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
}
