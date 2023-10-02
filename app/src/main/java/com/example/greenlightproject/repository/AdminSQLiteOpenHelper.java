package com.example.greenlightproject.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String COMMENTS_TABLE_CREATE = "CREATE TABLE perfiles(_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, password TEXT, email TEXT, telefono TEXT, pais TEXT, ciudad TEXT, localidad TEXT, documento TEXT)";
    private static final String DB_NAME = "greenlight";
    private static final int DB_VERSION = 1;

    public AdminSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COMMENTS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
