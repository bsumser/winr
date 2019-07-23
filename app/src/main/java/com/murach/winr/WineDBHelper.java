package com.murach.winr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.murach.winr.WineContract.*;

public class WineDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "wine_data.db";
    public static final int DATABASE_VERSION = 1;

    public WineDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TIDE_TABLE = "CREATE TABLE " +
                WineEntry.TABLE_NAME + " (" +
                WineEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WineEntry.COLUMN_COLOR + " TEXT NOT NULL, " +
                WineEntry.COLUMN_YEAR + " TEXT NOT NULL, " +
                WineEntry.COLUMN_VARIETAL + " TEXT NOT NULL, " +
                WineEntry.COLUMN_WINERY + " TEXT NOT NULL, " +
                WineEntry.COLUMN_REGION + " TEXT NOT NULL, " +
                WineEntry.COLUMN_COUNTRY + " TEXT NOT NULL, " +
                WineEntry.COLUMN_PRICE + " REAL NOT NULL" +
                ");";
        db.execSQL(SQL_CREATE_TIDE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + WineEntry.TABLE_NAME);
        onCreate(db);
    }
}
