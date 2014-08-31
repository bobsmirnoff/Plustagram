package com.bobsmirnoff.plustagram20.Database;

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
    private static final String DB_TABLE_PEOPLE = "people_table";
    private static final String DB_TABLE_PLUSES = "pluses_table";
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
        return db.query(DB_TABLE_PEOPLE, null, null, null, null, null, COLUMN_COUNT + " DESC");
    }

    public Cursor getById(long id) {
        return db.query(DB_TABLE_PEOPLE,
                null,
                COLUMN_ID + " = ?",
                new String[]{Long.valueOf(id).toString()},
                null, null, null);
    }

    public void add(String name) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_COUNT, 0);
        db.insert(DB_TABLE_PEOPLE, null, values);
    }

    public void rename(long id, String newName) {
        this.edit(id, newName, -1);
    }

    public void edit(long id, String name, int count) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        if (count != -1) cv.put(COLUMN_COUNT, count);
        db.update(DB_TABLE_PEOPLE, cv, "_id = " + id, null);
    }

    public void delete(long id) {
        db.delete(DB_TABLE_PEOPLE, COLUMN_ID + " = " + id, null);
    }

    public void deleteAll() {
        context.deleteDatabase(DB_NAME);
    }
}
