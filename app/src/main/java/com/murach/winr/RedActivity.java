package com.murach.winr;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class RedActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_one);

        //initiate the database
        WineDBHelper dbHelper = new WineDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        //parse the xml file
        //parseXML();

        //set the list view
        ListView wineListView = (ListView) findViewById(R.id.wine_list_view);

        //test output data
        String[] menuItems = {"one", "two", "three"};
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                menuItems
        );

        //set the query
        Cursor wineCursor = mDatabase.rawQuery("SELECT  * FROM wine_data;", null);

        //set the adapter
        WineAdapter mAdapter = new WineAdapter(this, wineCursor);
        //attach to list view
        wineListView.setAdapter(mAdapter);
    }
}
