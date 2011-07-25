package com.icta.news.ui;

import com.icta.news.NewsManagerApplication;
import com.icta.news.R;
import com.icta.news.model.News;
import com.icta.news.model.NewsProviderMetaData.NewsTableMetaData;
import com.icta.news.service.NewsServiceHelper;
import com.icta.news.ui.adapter.NewsListAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class NewsListActivity extends ListActivity {

	public static final String TAG = "NewsListActivity";
	
	private NewsManagerApplication app;
	private NewsListAdapter adapter;
	//private SimpleCursorAdapter adapter;
	NewsServiceHelper helper;


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (helper != null) {
			helper.releaseService();
		}
		
		Log.v(TAG, "onListItemClick ... ListView is -"+l+" view is -"+v+" position is -"+position + " id is - "+id);
		//super.onListItemClick(l, v, position, id);
		//Uri viewedNews = Uri.withAppendedPath(NewsTableMetaData.CONTENT_URI, String.valueOf(id));
		//String uString =viewedNews.toString();
		//Log.v(TAG, "selected new uri is : "+viewedNews.toString());
		
		News n = (News) adapter.getItem(position);
		Intent intent = new Intent(this, ViewNewsActivity.class);
		intent.putExtra("news", n);
		
		//////////////////////////////////////
		
		////////////////////////////////
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "onCreate");
		setContentView(R.layout.main);
		
		app = (NewsManagerApplication) getApplication();
		adapter = new NewsListAdapter(this, app.getNews());
	//	String[] projection = { NewsTableMetaData._ID ,NewsTableMetaData.TITLE,NewsTableMetaData.CREATED };
	//	Uri content = NewsTableMetaData.CONTENT_URI;
		/////////////////////////////////////////////////////////////////
		
		//loadNews();
		
		 //helper.invokeService();
		/////////////////////////////////////////////////////////////////
		//Log.v(TAG, "id is "+ NewsTableMetaData._ID);
		//Log.v(TAG, "content is "+content.toString());
	//	Cursor cursor = getContentResolver().query(content, projection, null, null, null);
		
		//adapter = new SimpleCursorAdapter(this, R.layout.news_list_item , cursor, FROM , TO );
		setListAdapter(adapter);
		
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
	//	adapter.forceReload();
		super.onResume();
		loadNewsIfNotLoaded();
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
		Log.v(TAG, "loadNewsIfNotLoaded()");
		Log.v(TAG, "loadNewsIfNotLoaded     Count is : "+(adapter.getCount()>0)+"  and " + adapter.getCount());
		if (adapter.getCount() ==0) {
			loadNews();
		}
	}

	private void loadNews() {
		 helper = new NewsServiceHelper(getApplicationContext());
			helper.bindService();
		adapter.appendNewer(app.getNews());
	}
	
	
}
