package com.oryx.db;

import java.util.ArrayList;

import com.oryx.db.SubscriptionContract.SubscriptionEntry;
import com.oryx.handlers.URLItem;
import com.oryx.handlers.UserSubItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHandler {

	private static DBHandler dbh = null;
	private SubscriptionsDBHelper dbHelper;

	private DBHandler(Context c) {

		dbHelper = new SubscriptionsDBHelper(c);
	}

	public static DBHandler getHandler(Context c) {
		if (dbh == null) {
			return new DBHandler(c);
		} else {
			return dbh;
		}
	}

	public int addSubcription(URLItem item) {

		int res = -1;
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(SubscriptionEntry.COLUMN_NAME_URL, item.getUrl());
		values.put(SubscriptionEntry.COLUMN_NAME_TITLE, item.getTitle());
		values.put(SubscriptionEntry.COLUMN_NAME_TYPE, item.getType());
		values.put(SubscriptionEntry.COLUMN_NAME_TAG, item.getTag());

		long newRowId;
		try {
			newRowId = db.insertOrThrow(SubscriptionEntry.TABLE_NAME, null,
					values);
			res = 1;
		} catch (SQLiteConstraintException e) {
			res = 0;
		} catch (Exception e) {
			res = -1;
		}
		db.close();
		return res;
	}

	public ArrayList<UserSubItem> getSubcriptions() {

		ArrayList<UserSubItem> mArrayList = new ArrayList<UserSubItem>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		try {
			String QUERY = "SELECT * FROM " + SubscriptionEntry.TABLE_NAME;
			Cursor c = db.rawQuery(QUERY, null);

			while (c.moveToNext()) {
				String url = c.getString(c
						.getColumnIndex(SubscriptionEntry.COLUMN_NAME_URL));
				String name = c.getString(c
						.getColumnIndex(SubscriptionEntry.COLUMN_NAME_TITLE));
				int type = c.getInt(c
						.getColumnIndex(SubscriptionEntry.COLUMN_NAME_TYPE));
				String tag = c.getString(c
						.getColumnIndex(SubscriptionEntry.COLUMN_NAME_TAG));
				mArrayList.add(new UserSubItem(name, url, type, tag));
			}
		} catch (Exception e) {

		}
		db.close();
		return mArrayList;
	}

	public boolean deleteSubcription(String url) {
		boolean res = false;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			String selection = SubscriptionEntry.COLUMN_NAME_URL + " LIKE ?";
			String[] selectionArgs = { url };
			db.delete(SubscriptionEntry.TABLE_NAME, selection, selectionArgs);
			res = true;
		} catch (Exception e) {

		}
		db.close();
		return res;
	}
}
