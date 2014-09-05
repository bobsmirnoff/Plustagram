package com.bobsmirnoff.plustagram20.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "plustagram";
    public static final int DB_VERSION = 2;

    public static final String PEOPLE_COLUMN_ID = "_id";
    public static final String PEOPLE_COLUMN_NAME = "name";
    public static final String PEOPLE_COLUMN_COUNT = "count";

    //people table columns
    public static final String DB_TABLE_PEOPLE = "people_table";
    private static final String DB_CREATE_PEOPLE =
            "create table "
                    + DB_TABLE_PEOPLE + "("
                    + PEOPLE_COLUMN_ID + " integer primary key autoincrement not null, "
                    + PEOPLE_COLUMN_NAME + " text, "
                    + PEOPLE_COLUMN_COUNT + " integer" + ");";

    //pluses table columns
    public static final String DB_TABLE_PLUSES = "pluses_table";
    public static final String PLUSES_COLUMN_ID = "_id";
    public static final String PLUSES_COLUMN_DATE = "date";
    public static final String PLUSES_COLUMN_COMMENT = "comment";
    public static final String PLUSES_COLUMN_TOID = "toid";

    private static final String DB_CREATE_PLUSES =
            "create table "
                    + DB_TABLE_PLUSES + "("
                    + PLUSES_COLUMN_ID + " integer primary key autoincrement not null, "
                    + PLUSES_COLUMN_DATE + " text, "
                    + PLUSES_COLUMN_COMMENT + " text, "
                    + PLUSES_COLUMN_TOID + " integer" + ");";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_PEOPLE);
        db.execSQL(DB_CREATE_PLUSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
