package com.oryx.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.oryx.db.SubscriptionContract.SubscriptionEntry;

public class SubscriptionsDBHelper extends SQLiteOpenHelper{

	private static final String TEXT_TYPE = " TEXT";
	private static final String INT_TYPE = " INTEGER";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + SubscriptionEntry.TABLE_NAME + " (" +
	    		SubscriptionEntry._ID + " INTEGER PRIMARY KEY," +
			    SubscriptionEntry.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
			    SubscriptionEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
			    SubscriptionEntry.COLUMN_NAME_TYPE + INT_TYPE + COMMA_SEP +
			    SubscriptionEntry.COLUMN_NAME_TAG + TEXT_TYPE+ COMMA_SEP +
			    " UNIQUE("+SubscriptionEntry.COLUMN_NAME_URL+")"+
	    " )";

	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + SubscriptionEntry.TABLE_NAME;
	
	
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Subcriptions.db";

    public SubscriptionsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
