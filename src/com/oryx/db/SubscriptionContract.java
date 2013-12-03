package com.oryx.db;

import android.provider.BaseColumns;

public class SubscriptionContract {

    public SubscriptionContract() {}

    public static abstract class SubscriptionEntry implements BaseColumns {
        public static final String TABLE_NAME = "subscriptions";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_TAG = "tag";
    }

    
}
