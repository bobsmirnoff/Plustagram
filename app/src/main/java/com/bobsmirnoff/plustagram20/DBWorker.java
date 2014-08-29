package com.bobsmirnoff.plustagram20;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBWorker {

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COUNT = "count";
    private static final String DB_NAME = "plustagram_db";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "people_table";
    private final Context context;

    private DBHelper helper;
    private SQLiteDatabase db;

    public DBWorker(Context cxt) {
        context = cxt;
    }

    public void open() {
        helper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        db = helper.getWritableDatabase();
    }

    public void close() {
        if (helper != null) helper.close();
    }

    public Cursor getAllSorted() {
        return db.query(DB_TABLE, null, null, null, null, null, COLUMN_COUNT + " DESC");
    }

    public void addRecord(String name) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_COUNT, 0);
        db.insert(DB_TABLE, null, values);
    }

    public void deleteRecord(long id) {
        db.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    public void deleteAll() {
        context.deleteDatabase(DB_NAME);
    }
}
