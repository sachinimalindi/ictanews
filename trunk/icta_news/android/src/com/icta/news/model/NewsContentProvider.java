package com.icta.news.model;

import java.util.Date;
import java.util.HashMap;

import com.icta.news.model.NewsProviderMetaData.NewsTableMetaData;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class NewsContentProvider extends ContentProvider {
	
	private static final String TAG = "NewsContentProvider";
    
	private static HashMap<String, String> sNewsProjectionMap;
	static
	{
		sNewsProjectionMap = new HashMap<String, String>();
		sNewsProjectionMap.put(NewsTableMetaData._ID, NewsTableMetaData._ID);
		//title content image
		sNewsProjectionMap.put(NewsTableMetaData.TITLE
				, NewsTableMetaData.TITLE);
		sNewsProjectionMap.put(NewsTableMetaData.CONTENT
				, NewsTableMetaData.CONTENT);
		sNewsProjectionMap.put(NewsTableMetaData.IMAGEURL
				, NewsTableMetaData.IMAGEURL);
		//date
		sNewsProjectionMap.put(NewsTableMetaData.CREATED
				, NewsTableMetaData.CREATED);

	}

	private static final UriMatcher sUriMatcher;
	private static final int INCOMING_NEWS_COLLECTION_URI_INDICATOR = 1;

	private static final int INCOMING_SINGLE_NEWS_URI_INDICATOR = 2;
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(NewsProviderMetaData.AUTHORITY
				, "news"
				, INCOMING_NEWS_COLLECTION_URI_INDICATOR);
		sUriMatcher.addURI(NewsProviderMetaData.AUTHORITY
				, "anews/#",
				INCOMING_SINGLE_NEWS_URI_INDICATOR);
	}
	// Deal with OnCreate call back
	private DatabaseHelper mOpenHelper;
	
	
	private static class DatabaseHelper extends SQLiteOpenHelper{


		public DatabaseHelper(Context context) {
			super(context, NewsProviderMetaData.DATABASE_NAME , null , NewsProviderMetaData.DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.v(TAG, "onCreate "+"CREATE TABLE " +NewsTableMetaData.TABLE_NAME +" ( "
					+ NewsTableMetaData._ID + " INTEGER PRIMARY KEY,"
					+ NewsTableMetaData.TITLE + " TEXT,"
					+ NewsTableMetaData.CONTENT + " TEXT,"
					+ NewsTableMetaData.CREATED + " INTEGER NOT NULL DEFAULT (strftime('%s','now')),"
					+ NewsTableMetaData.IMAGEURL + " TEXT "
					+ ");");
			db.execSQL("CREATE TABLE " +NewsTableMetaData.TABLE_NAME +" ( "
					+ NewsTableMetaData._ID + " INTEGER PRIMARY KEY,"
					+ NewsTableMetaData.TITLE + " TEXT,"
					+ NewsTableMetaData.CONTENT + " TEXT,"
					+ NewsTableMetaData.CREATED + " INTEGER NOT NULL DEFAULT (strftime('%s','now')),"
					+ NewsTableMetaData.IMAGEURL + " TEXT "
					+ ");");
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + NewsTableMetaData.TABLE_NAME);
			onCreate(db);
			
		}
		
	}
	
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) 
		{
			case INCOMING_NEWS_COLLECTION_URI_INDICATOR:
				count = db.delete(NewsTableMetaData.TABLE_NAME, where, whereArgs);
				break;
			case INCOMING_SINGLE_NEWS_URI_INDICATOR:
				String rowId = uri.getPathSegments().get(1);
				count = db.delete(NewsTableMetaData.TABLE_NAME
						, NewsTableMetaData._ID + "=" + rowId
						+ (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : "")
						, whereArgs);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) 
		{
			case INCOMING_NEWS_COLLECTION_URI_INDICATOR:
				return NewsTableMetaData.CONTENT_TYPE;

			case INCOMING_SINGLE_NEWS_URI_INDICATOR:
				return NewsTableMetaData.CONTENT_ITEM_TYPE;

			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// Validate the requested uri
		if (sUriMatcher.match(uri) != INCOMING_NEWS_COLLECTION_URI_INDICATOR) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		/*Long now = Long.valueOf(System.currentTimeMillis());

		//validate input fields
		// Make sure that the fields are all set
		if (values.containsKey(NewsTableMetaData.CREATED) == false) {
		//	values.put(NewsTableMetaData.CREATED, new Date(System.currentTimeMillis()));
	
		}

		if (values.containsKey(NewsTableMetaData.TITLE) == false) {
			values.put(NewsTableMetaData.TITLE, now);
		}
		if (values.containsKey(NewsTableMetaData.CONTENT) == false) {
			values.put(NewsTableMetaData.CONTENT, now);
			throw new SQLException(
					"Failed to insert row because Book Name is needed " + uri);
		}

		if (values.containsKey(NewsTableMetaData.IMAGEURL) == false) {
			values.put(NewsTableMetaData.IMAGEURL, "Unknown ISBN");
		}*/

		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		 try {
		long newID = db.insertOrThrow(NewsTableMetaData.TABLE_NAME, null, values);
		
		if (newID > 0) {
			Uri newUri = ContentUris.withAppendedId(NewsTableMetaData.CONTENT_URI, newID);
			getContext().getContentResolver().notifyChange(newUri, null);
			return newUri;
		}else {
			throw new SQLException("Failed to insert row into " + uri);
		}
		 }catch (Exception e) {
			// Log.i(DEBUG_TAG, "Ignoring constraint failure.");
		}
		/*long rowId = db.insert(NewsTableMetaData.TABLE_NAME
				, NewsTableMetaData.TITLE, values);

		if (rowId > 0) {
			Uri insertedNewsUri = ContentUris.withAppendedId(
					NewsTableMetaData.CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(insertedNewsUri, null);
			return insertedNewsUri;
		}
		throw new SQLException("Failed to insert row into " + uri);*/
		 return null;
	}

	@Override
	public boolean onCreate() {
		mOpenHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection
			, String[] selectionArgs, String sortOrder) {
		Log.v(TAG, "Cursor query");
		Log.v(TAG, "uri is :"+uri);
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (sUriMatcher.match(uri))
		{
			case INCOMING_NEWS_COLLECTION_URI_INDICATOR:
			    qb.setTables(NewsTableMetaData.TABLE_NAME);
			//	qb.setProjectionMap(sNewsProjectionMap);
				break;

			case INCOMING_SINGLE_NEWS_URI_INDICATOR:
				qb.setTables(NewsTableMetaData.TABLE_NAME);
				qb.setProjectionMap(sNewsProjectionMap);
				qb.appendWhere(NewsTableMetaData._ID + "="
						+ uri.getPathSegments().get(1));
				break;

			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}

		// If no sort order is specified use the default
		String orderBy;
		if (TextUtils.isEmpty(sortOrder)) {
			orderBy = NewsTableMetaData.DEFAULT_SORT_ORDER;
		} else {
			orderBy = sortOrder;
		}

		// Get the database and run the query
		SQLiteDatabase db =
			mOpenHelper.getReadableDatabase();

		Cursor c = qb.query(db, projection, selection,
				selectionArgs, null, null, orderBy);

		int i = c.getCount();
		// Tell the cursor what uri to watch,
		// so it knows when its source data changes
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
