package com.murach.winr;

import android.provider.BaseColumns;

public class WineContract {
    private WineContract() {}
    public static final class WineEntry implements BaseColumns {
        public static final String TABLE_NAME = "tide_data";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_DAY = "day";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_PREDFT = "PredFt";
        public static final String COLUMN_PREDCM = "Predcm";
        public static final String COLUMN_HIGHLOW = "HighLow";
    }
}
