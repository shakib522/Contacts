package com.example.contacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ContactHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="contact.db";
    public static final String TABLE_NAME="contact_table";
    public static final int DATABASE_VERSION=1;

    public static final String COL_ID="_id";
    public static final String COL_NAME="name";
    public static final String COL_NUMBER="number";

    private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_NAME+" TEXT,"+COL_NUMBER+" TEXT "+")";

    public ContactHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }
}
