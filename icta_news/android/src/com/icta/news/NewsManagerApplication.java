package com.icta.news;
import java.util.ArrayList;

import com.icta.news.R;
import com.icta.news.model.News;
import com.icta.news.model.NewsProviderMetaData.NewsTableMetaData;

import android.app.Application;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;


public class NewsManagerApplication extends Application {
	
	public static final String TAG = "NewsManagerApplication";

	private ArrayList<News> news;
	
	private static final String[] FROM = { NewsTableMetaData.TITLE, NewsTableMetaData.CREATED };
    private static final int[] TO = { R.id.list_item_title, R.id.list_item_date };
	
	@Override
	public void onCreate() {
	    super.onCreate();
	    if (null == news) {
			loadNews();
		}
	}

	private void loadNews() {
		
		news = new ArrayList<News>();
		
		String[] projection = { NewsTableMetaData._ID ,NewsTableMetaData.TITLE,NewsTableMetaData.CREATED,NewsTableMetaData.CONTENT ,NewsTableMetaData.IMAGEURL};
		Uri content = NewsTableMetaData.CONTENT_URI;
		
		Cursor cursor = getContentResolver().query(content, projection, null, null, null);
		
		cursor.moveToFirst();
		News n;
		
		if (! cursor.isAfterLast()) {
			do {
				int id = cursor.getInt(0);
				Log.v(TAG, "news id is : "+id);
				String title =  cursor.getString(1);
				Log.v(TAG, "news title is : "+title);
				String con= cursor.getString(3);
				Log.v(TAG, "news content is : "+con);
				
				int created = cursor.getInt(2);
				Log.v(TAG, "news created is : "+created);
				String imageUrl = cursor.getString(4);
				Log.v(TAG, "news imageUrl is : "+imageUrl);
				//Log.v(TAG, "news 0th is : "+cursor.getString(0));//id
				//Log.v(TAG, "news 1th is : "+cursor.getString(1));//title
				//Log.v(TAG, "news 2th is : "+cursor.getString(2));//created
				//Log.v(TAG, "news 3th is : "+cursor.getString(3));//content
				//Log.v(TAG, "news 4th is : "+cursor.getString(4));//imageurl
				
				n = new News(id, title, created, con , imageUrl);
					
				news.add(n);
			} while (cursor.moveToNext());
			
		}
		cursor.close();
		
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	public ArrayList<News> getNews() {
		return news;
	}

	public void setNews(ArrayList<News> news) {
		this.news = news;
	}

}
