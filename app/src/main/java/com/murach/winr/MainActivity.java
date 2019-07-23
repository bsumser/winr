package com.murach.winr;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
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
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

        //parseXML();

        /*-----------------------------BOILERPLATE CODE-------------------------------*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseXML();
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
                WineContract.WineEntry.COLUMN_PRICE + " DESC"
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
            //launch the settings activity
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.menu_item_two) {
            //launch the dialog fragment
            showAbout();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.red_wines) {
            Intent intent = new Intent(MainActivity.this, RedActivity.class);
            startActivity(intent);
        } else if (id == R.id.white_wine) {
            Intent intent = new Intent(MainActivity.this, WhiteActivity.class);
            startActivity(intent);
        } else if (id == R.id.varietals) {
            Intent intent = new Intent(MainActivity.this, VarietalActivity.class);
            startActivity(intent);
        } else if (id == R.id.regions) {
            Intent intent = new Intent(MainActivity.this, RegionActivity.class);
            startActivity(intent);
        } else if (id == R.id.countries) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    protected void showAbout() {
        // Inflate the about message contents
        View messageView = getLayoutInflater().inflate(R.layout.about, null, false);

        // When linking text, force to always use default color. This works
        // around a pressed color state bug.
        TextView textView = (TextView) messageView.findViewById(R.id.about_credits);
        int defaultColor = textView.getTextColors().getDefaultColor();
        textView.setTextColor(defaultColor);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setIcon(R.drawable.app_icon);
        builder.setTitle(R.string.app_name);
        builder.setView(messageView);
        builder.create();
        builder.show();
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
            InputStream is = getAssets().open("wineList.xml");
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
                    if ("row".equals(eltName)) {
                        cv = new ContentValues();
                    } else if (cv != null) {
                        if ("Color".equals(eltName)) {
                            cv.put(WineContract.WineEntry.COLUMN_COLOR, parser.nextText());
                        } else if ("Year".equals(eltName)) {
                            cv.put(WineContract.WineEntry.COLUMN_YEAR, parser.nextText());
                        } else if ("Varietal".equals(eltName)) {
                            cv.put(WineContract.WineEntry.COLUMN_VARIETAL, parser.nextText());
                        } else if ("Winery".equals(eltName)) {
                            cv.put(WineContract.WineEntry.COLUMN_WINERY, parser.nextText());
                        } else if ("Region".equals(eltName)) {
                            cv.put(WineContract.WineEntry.COLUMN_REGION, parser.nextText());
                        } else if ("Country".equals(eltName)) {
                            cv.put(WineContract.WineEntry.COLUMN_COUNTRY, parser.nextText());
                        } else if ("Price".equals(eltName)) {
                            cv.put(WineContract.WineEntry.COLUMN_PRICE, parser.nextText());
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
