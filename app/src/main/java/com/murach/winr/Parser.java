package com.murach.winr;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;

public class Parser {
    private Context parseContext;
    private SQLiteDatabase parseDatabase;

    public Parser(Context context, SQLiteDatabase mDatabase) {
        parseContext = context;
        parseDatabase = mDatabase;
    }

    public void parseXML() {
        //declare instance of XmlPullParserFactory class
        XmlPullParserFactory parserFactory;

        //try/catch block to catch exceptions
        try {
            //instantiate instance of XmlPullParserFactory class
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();

            //get the input stream of xml file from the assests folder
            InputStream is = parseContext.getAssets().open("wineList.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            //take parser object and process into array of java objects
            processParsing(parser);
        } catch (XmlPullParserException e) {

        } catch (IOException e) {
        }
    }

    public SQLiteDatabase processParsing(XmlPullParser parser) throws IOException, XmlPullParserException {
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
                    }
                    parseDatabase.insert(WineContract.WineEntry.TABLE_NAME, null, cv);
                    break;
            }
            eventType = parser.next();
        }
        return parseDatabase;
    }
}
