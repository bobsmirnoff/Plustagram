package com.bobsmirnoff.plustagram20.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBWorker {

    /*private static final String DB_NAME = "plustagram";
    private static final int DB_VERSION = 2;

    //people table columns
    public static final String DB_TABLE_PEOPLE = "people_table";
    public static final String PEOPLE_COLUMN_ID = "_id";
    public static final String PEOPLE_COLUMN_NAME = "name";
    public static final String PEOPLE_COLUMN_COUNT = "count";

    //pluses table columns
    public static final String DB_TABLE_PLUSES = "pluses_table";
    public static final String PLUSES_COLUMN_ID = "_id";
    public static final String PLUSES_COLUMN_TOID = "toid";
    public static final String PLUSES_COLUMN_DATE = "date";
    public static final String PLUSES_COLUMN_COMMENT = "comment";*/

    private final Context context;

    private DBHelper helper;
    private SQLiteDatabase db;

    public DBWorker(Context cxt) {
        context = cxt;
    }

    public void open() {
        helper = new DBHelper(context, DBHelper.DB_NAME, null, DBHelper.DB_VERSION);
        db = helper.getWritableDatabase();
    }

    public void close() {
        if (helper != null) helper.close();
    }

    public Cursor getAllPeople() {
        return db.query(DBHelper.DB_TABLE_PEOPLE, null, null, null, null, null, null);
    }

    public Cursor getAllPeopleSorted() {
        return db.query(DBHelper.DB_TABLE_PEOPLE, null, null, null, null, null, DBHelper.PEOPLE_COLUMN_COUNT + " DESC");
    }

    public Cursor getPersonById(long id) {
        return db.query(DBHelper.DB_TABLE_PEOPLE,
                null,
                DBHelper.PEOPLE_COLUMN_ID + " = ?",
                new String[]{Long.valueOf(id).toString()},
                null, null, null);
    }

    public int getCountById(long id) {
        int count = -1;
        Cursor cursor = db.query(DBHelper.DB_TABLE_PEOPLE,
                new String[]{DBHelper.PEOPLE_COLUMN_COUNT},
                DBHelper.PEOPLE_COLUMN_ID + " = ?",
                new String[]{Long.valueOf(id).toString()},
                null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(cursor.getColumnIndex(DBHelper.PEOPLE_COLUMN_COUNT));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return count;
    }

    public void addPerson(String name) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.PEOPLE_COLUMN_NAME, name);
        values.put(DBHelper.PEOPLE_COLUMN_COUNT, 0);
        db.insert(DBHelper.DB_TABLE_PEOPLE, null, values);
    }

    public void renamePerson(long id, String newName) {
        this.editPerson(id, newName, -1);
    }

    public void editPerson(long id, String name, int count) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.PEOPLE_COLUMN_NAME, name);
        if (count != -1) cv.put(DBHelper.PEOPLE_COLUMN_COUNT, count);
        db.update(DBHelper.DB_TABLE_PEOPLE, cv, "_id = " + id, null);
    }

    public void deletePerson(long id) {
        db.delete(DBHelper.DB_TABLE_PEOPLE, DBHelper.PEOPLE_COLUMN_ID + " = " + id, null);
    }

    public void deleteAllPeople() {
        context.deleteDatabase(DBHelper.DB_NAME);
    }

    public void plus(long id, String comment) {
        ContentValues cv = new ContentValues();
        int count = this.getCountById(id);
        cv.put(DBHelper.PEOPLE_COLUMN_COUNT, ++count);
        db.update(DBHelper.DB_TABLE_PEOPLE, cv, "_id = " + id, null);
        this.recordPlus(id, comment);
    }

    //----------------------pluses table methods-----------------------------------

    public Cursor getPlussesForId(long id) {
        return db.query(DBHelper.DB_TABLE_PLUSES,
                null,
                DBHelper.PLUSES_COLUMN_TOID + " = ?",
                new String[]{Long.valueOf(id).toString()},
                null, null, null);
    }

    public Cursor getAllPluses() {
        return db.query(DBHelper.DB_TABLE_PLUSES, null, null, null, null, null, null);
    }

    public int getPlusesCount() {
        Cursor cursor = getAllPluses();
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getPlusedPeopleCount() {
        Cursor cursor = db.query(DBHelper.DB_TABLE_PLUSES, null, null, null,
                DBHelper.PLUSES_COLUMN_TOID, null, null);
        return cursor.getCount();
    }

    private void recordPlus(long toId, String comment) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String strDate = sdf.format(new Date());

        //somehow db.insert does not work
        db.execSQL("INSERT INTO " + DBHelper.DB_TABLE_PLUSES + " ("
                + DBHelper.PLUSES_COLUMN_DATE + ", "
                + DBHelper.PLUSES_COLUMN_COMMENT + ", "
                + DBHelper.PLUSES_COLUMN_TOID + ") " +
                "VALUES ('" + strDate + "', '" + comment + "', " + toId + ")");
    }
}