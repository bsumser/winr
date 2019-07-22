package com.murach.winr;

import android.provider.BaseColumns;

public class WineContract {
    private WineContract() {}
    public static final class WineEntry implements BaseColumns {
       public static final String TABLE_NAME = "wine_data";
        public static final String COLUMN_COLOR = "Color";
        public static final String COLUMN_YEAR = "Year";
        public static final String COLUMN_VARIETAL = "Varietal";
        public static final String COLUMN_WINERY = "Winery";
        public static final String COLUMN_REGION = "Region";
        public static final String COLUMN_COUNTRY = "Country";
        public static final String COLUMN_PRICE = "Price";
    }
}
