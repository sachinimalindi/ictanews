package com.icta.news.ui;

import com.icta.news.R;
import com.icta.news.model.NewsProviderMetaData.NewsTableMetaData;
import com.icta.news.service.NewsServiceHelper;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class NewsListActivity extends ListActivity {

	public static final String TAG = "NewsListActivity";
	
	 
	private SimpleCursorAdapter adapter;
	NewsServiceHelper helper;


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		helper.releaseService();
		Log.v(TAG, "onListItemClick ... ListView is -"+l+" view is -"+v+" position is -"+position + " id is - "+id);
		//super.onListItemClick(l, v, position, id);
		Uri viewedNews = Uri.withAppendedPath(NewsTableMetaData.CONTENT_URI, String.valueOf(id));
		//String uString =viewedNews.toString();
		Log.v(TAG, "selected new uri is : "+viewedNews.toString());
		Intent intent = new Intent(this, ViewNewsActivity.class);
		intent.putExtra("uri", viewedNews.toString());
		
		//////////////////////////////////////
		
		////////////////////////////////
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "onCreate");
		setContentView(R.layout.main);
		String[] projection = { NewsTableMetaData._ID ,NewsTableMetaData.TITLE,NewsTableMetaData.CREATED };
		Uri content = NewsTableMetaData.CONTENT_URI;
		/////////////////////////////////////////////////////////////////
		
		//loadNews();
		
		 //helper.invokeService();
		/////////////////////////////////////////////////////////////////
		Log.v(TAG, "id is "+ NewsTableMetaData._ID);
		Log.v(TAG, "content is "+content.toString());
		Cursor cursor = getContentResolver().query(content, projection, null, null, null);
		
		//adapter = new SimpleCursorAdapter(this, R.layout.news_list_item , cursor, FROM , TO );
		//setListAdapter(adapter);
		
		super.onCreate(savedInstanceState);
		
		
	}

	@Override
	protected void onPause() {
		Log.v(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.v(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onStart() {
		Log.v(TAG, "onStart");
		loadNewsIfNotLoaded();
		super.onStart();
	}
	
	@Override
	protected void onDestroy() {
		Log.v(TAG, "onDestroy");
		super.onDestroy();
	}
	
	private void loadNewsIfNotLoaded() {
		if (null == getListAdapter()) {
			loadNews();
		}
	}

	private void loadNews() {
		 helper = new NewsServiceHelper(getApplicationContext());
			helper.bindService();
		
	}
	
	
}
