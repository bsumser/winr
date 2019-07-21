package com.murach.winr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.murach.winr.WineContract.*;

public class WineDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "wineList.db";
    public static final int DATABASE_VERSION = 1;
    public WineDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WINELIST_TABLE = "CREATE TABLE " +
                WineEntry.TABLE_NAME + " (" +
                        WineEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        WineEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                        WineEntry.COLUMN_AMOUNT + " INTEGER NOT NULL, " +
                        WineEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                        ");";
        db.execSQL(SQL_CREATE_WINELIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + WineEntry.TABLE_NAME);
        onCreate(db);
    }
}
