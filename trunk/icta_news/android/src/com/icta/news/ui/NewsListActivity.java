package com.icta.news.ui;

import com.icta.news.R;
import com.icta.news.model.NewsProviderMetaData.NewsTableMetaData;
import com.icta.news.service.NewsServiceHelper;

import android.app.ListActivity;
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

	private static final String[] FROM = { NewsTableMetaData.TITLE,
				NewsTableMetaData.CREATED };
	private static final int[] TO = { R.id.list_item_title,
				R.id.list_item_date };



	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Log.v(TAG, "onListItemClick ... ListView is -"+l+" view is -"+v+" position is -"+position + " id is - "+id);
		super.onListItemClick(l, v, position, id);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "onCreate");
		setContentView(R.layout.main);
		String[] projection = { NewsTableMetaData._ID ,NewsTableMetaData.TITLE,NewsTableMetaData.CREATED };
		Uri content = NewsTableMetaData.CONTENT_URI;
		/////////////////////////////////////////////////////////////////
		
		 helper = new NewsServiceHelper(getApplicationContext());
		helper.bindService();

		 //helper.invokeService();
		/////////////////////////////////////////////////////////////////
		Log.v(TAG, "id is "+ NewsTableMetaData._ID);
		Log.v(TAG, "content is "+content.toString());
		Cursor cursor = getContentResolver().query(content, projection, null, null, null);
		
		adapter = new SimpleCursorAdapter(this, R.layout.news_list_item , cursor, FROM , TO );
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
		super.onResume();
	}

	@Override
	protected void onStart() {
		Log.v(TAG, "onStart");
		super.onStart();
	}
	
	@Override
	protected void onDestroy() {
		Log.v(TAG, "onDestroy");
		super.onDestroy();
	}
	
	
}
