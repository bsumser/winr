package com.murach.winr;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class WhiteActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red);

        //initiate the database
        WineDBHelper dbHelper = new WineDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        //set the list view
        ListView wineListView = (ListView) findViewById(R.id.wine_list_view);

        //set the query
        Cursor wineCursor = mDatabase.rawQuery("SELECT  * FROM wine_data WHERE Color='White';", null);

        //set the adapter
        WineAdapter mAdapter = new WineAdapter(this, wineCursor);
        //attach to list view
        wineListView.setAdapter(mAdapter);
    }
}

