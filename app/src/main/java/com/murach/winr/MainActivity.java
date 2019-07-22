package com.murach.winr;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private WineAdapter mAdapter;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initiate the database
        WineDBHelper dbHelper = new WineDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        //parse the xml file
        parseXML();

        //setup the recyclerView
        RecyclerView recyclerView = findViewById(R.id.red_wine_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set the adapter
        mAdapter = new WineAdapter(this, getSomeItems());
        recyclerView.setAdapter(mAdapter);

        /*-----------------------------BOILERPLATE CODE-------------------------------*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        /*-----------------------------END BOILERPLATE CODE-------------------------------*/
    }

    private Cursor getAllItems() {
        return mDatabase.query(
                WineContract.WineEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WineContract.WineEntry.COLUMN_DATE + " DESC"
        );
    }

    private Cursor getSomeItems() {
        return mDatabase.rawQuery("SELECT * FROM " + WineContract.WineEntry.TABLE_NAME, null);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.red_wines) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentOne()).commit();
        } else if (id == R.id.white_wine) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentTwo()).commit();
        } else if (id == R.id.varietals) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentThree()).commit();
        } else if (id == R.id.regions) {

        } else if (id == R.id.countries) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void parseXML() {
        //declare instance of XmlPullParserFactory class
        XmlPullParserFactory parserFactory;

        //try/catch block to catch exceptions
        try {
            //instantiate instance of XmlPullParserFactory class
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();

            //get the input stream of xml file from the assests folder
            InputStream is = getAssets().open("tide_data.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            //take parser object and process into array of java objects
            processParsing(parser);
        } catch (XmlPullParserException e) {

        } catch (IOException e) {
        }
    }

    private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException {
        int eventType = parser.getEventType();
        //holder for values to be put in database
        ContentValues cv = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            //placeholder for tide data fields
            String eltName = null;

            switch (eventType) {
                //switch case to determine which xml tag the loop is at
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    //if else to determine which tag and add data to current java object
                    if ("item".equals(eltName)) {
                        cv = new ContentValues();
                    } else if (cv != null) {
                        if ("date".equals(eltName)) {
                            cv.put(WineContract.WineEntry.COLUMN_DATE, parser.nextText());
                        } else if ("day".equals(eltName)) {
                            cv.put(WineContract.WineEntry.COLUMN_DAY, parser.nextText());
                        } else if ("time".equals(eltName)) {
                            cv.put(WineContract.WineEntry.COLUMN_TIME, parser.nextText());
                        } else if ("pred_in_ft".equals(eltName)) {
                            cv.put(WineContract.WineEntry.COLUMN_PREDFT, parser.nextText());
                        } else if ("pred_in_cm".equals(eltName)) {
                            cv.put(WineContract.WineEntry.COLUMN_PREDCM, parser.nextText());
                        } else if ("highlow".equals(eltName)) {
                            cv.put(WineContract.WineEntry.COLUMN_HIGHLOW, parser.nextText());
                        }
                        mDatabase.insert(WineContract.WineEntry.TABLE_NAME, null, cv);
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

    /*//test function for printing data from xml
    private void printDays(ArrayList<Day> days) {
        StringBuilder builder = new StringBuilder();

        for (Day day : days) {
            builder.append(day.date).append("\n").
                    append(day.day).append("\n").
                    append(day.time).append("\n").
                    append(day.ft).append("\n").
                    append(day.cm).append("\n").
                    append(day.highlow).append("\n\n");
        }
        //txt.setText(builder.toString());
    }*/
}
