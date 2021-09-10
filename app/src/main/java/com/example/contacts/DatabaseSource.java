package com.example.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseSource {

    ContactHelper contactHelper;
    SQLiteDatabase sqLiteDatabase;

    public DatabaseSource(Context context) {
        contactHelper = new ContactHelper(context);
    }

    public void open() {
        sqLiteDatabase = contactHelper.getWritableDatabase();
    }

    public void close() {
        contactHelper.close();
    }

    public boolean addContact(ContactModel contactModel) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactHelper.COL_NAME, contactModel.getName());
        contentValues.put(ContactHelper.COL_NUMBER, contactModel.getNumber());
        long insertedRow = sqLiteDatabase.insert(ContactHelper.TABLE_NAME, null, contentValues);
        this.close();
        return insertedRow > 0;
    }

    public ArrayList<ContactModel> getAllContact() {
        sqLiteDatabase = contactHelper.getReadableDatabase();
        ArrayList<ContactModel> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(ContactHelper.TABLE_NAME, null, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);//0
                String name = cursor.getString(1);
                String phone = cursor.getString(2);

                ContactModel contactModel = new ContactModel(name, phone, id);
                arrayList.add(contactModel);
            } while (cursor.moveToNext());
        }
        this.close();
        cursor.close();
        return arrayList;
    }

    public boolean update(ContactModel contactModel) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactHelper.COL_NAME, contactModel.getName());
        contentValues.put(ContactHelper.COL_NUMBER, contactModel.getNumber());
        int upgradedRow = sqLiteDatabase.update(ContactHelper.TABLE_NAME, contentValues, ContactHelper.COL_NUMBER + " =?", new String[]{String.valueOf(contactModel.getNumber())});
        this.close();
        return upgradedRow > 0;
    }

    public boolean delete(ContactModel contactModel) {
        this.open();
        int deletedRow = sqLiteDatabase.delete(ContactHelper.TABLE_NAME, ContactHelper.COL_ID + " =?", new String[]{String.valueOf(contactModel.getId())});
        this.close();
        return deletedRow > 0;
    }
}
