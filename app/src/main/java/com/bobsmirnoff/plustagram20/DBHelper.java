package com.bobsmirnoff.plustagram20;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COUNT = "count";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_COMMENT = "comment";
    private static final String DB_TABLE_PEOPLE = "people_table";
    private static final String DB_CREATE_PEOPLE =
            "create table "
                    + DB_TABLE_PEOPLE + "("
                    + COLUMN_ID + " integer primary key autoincrement, "
                    + COLUMN_NAME + " text, "
                    + COLUMN_COUNT + " integer" + ");";
    private static final String DB_TABLE_PLUSES = "pluses_table";
    private static final String DB_CREATE_PLUSES =
            "create table "
                    + DB_TABLE_PLUSES + "("
                    + COLUMN_ID + " integer primary key autoincrement, "
                    + COLUMN_NAME + " text, "
                    + COLUMN_DATE + " text, "
                    + COLUMN_COMMENT + " text" + ");";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_PEOPLE);
        db.execSQL(DB_CREATE_PLUSES);

        int pls[] = new int[]{1, 5, 10, 100};
        ContentValues cv = new ContentValues();
        for (int i = 0; i < 4; i++) {
            cv.put(COLUMN_NAME, "name");
            cv.put(COLUMN_COUNT, pls[i]);
            db.insert(DB_TABLE_PEOPLE, null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
