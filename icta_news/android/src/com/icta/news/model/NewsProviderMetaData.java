package com.icta.news.model;

import android.net.Uri;
import android.provider.BaseColumns;

public class NewsProviderMetaData {
	
	public static final String AUTHORITY = "com.icta.news.model.NewsContentProvider";
	public static final String DATABASE_NAME = "news.db";
	public static final int DATABASE_VERSION = 1;
	public static final String BOOKS_TABLE_NAME = "news";
	
	private NewsProviderMetaData(){
		
	}

	public static final class NewsTableMetaData implements BaseColumns
	{
		private NewsTableMetaData(){
			
		}
		
		public static final String TABLE_NAME = "news";
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/news");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.icta_news.news";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.dir/vnd.icta_news.anews";
		
		public static final String DEFAULT_SORT_ORDER = "created DESC";
		
		public static final String TITLE    = "title";
		public static final String CREATED  = "created";
		public static final String CONTENT  = "content";
		public static final String IMAGEURL = "ImageURL";
		
		
	}
}
