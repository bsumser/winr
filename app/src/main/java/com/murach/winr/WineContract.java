package com.murach.winr;

import android.provider.BaseColumns;

public class WineContract {
    private WineContract() {}
    public static final class WineEntry implements BaseColumns {
        public static final String TABLE_NAME = "wineList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
