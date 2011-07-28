package com.icta.news.ui;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import com.icta.news.R;
import com.icta.news.model.News;
import com.icta.news.model.NewsProviderMetaData.NewsTableMetaData;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewNewsActivity extends Activity {

	public static final String TAG = "ViewNewsActivity";
	
	private ImageView image;
	private TextView title;
	private TextView created;
	private TextView content;
	
	//private String imageUri;
	//private String[] projection = { NewsTableMetaData._ID ,NewsTableMetaData.TITLE,NewsTableMetaData.CREATED,NewsTableMetaData.IMAGEURL };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_view);
		 setUpViews();
		 readNews();
		 showNews();
	}

	private void showNews() {
		Log.v(TAG, "showNews");
		// TODO Auto-generated method stub
		
	}

	private void readNews() {
		Log.v(TAG, "readNews");
		/*Cursor c = getCursor();
		Log.v(TAG, "readNews cursor is : "+c.toString());
		Log.v(TAG, "readNews is titleCol "+c.getColumnIndex(NewsTableMetaData.TITLE));
		int titleCol = c.getColumnIndex(NewsTableMetaData.TITLE);
		Log.v(TAG, "readNews contentCol is "+ c.getColumnIndex(NewsTableMetaData.CONTENT));
		int contentCol = c.getColumnIndex(NewsTableMetaData.CONTENT);
		Log.v(TAG, "readNews createdCol is  "+c.getColumnIndexOrThrow(NewsTableMetaData.CREATED));
		int createdCol = c.getColumnIndexOrThrow(NewsTableMetaData.CREATED);
		Log.v(TAG, "readNews imageUrlCol is " +c.getColumnIndex(NewsTableMetaData.IMAGEURL));
		int imageUrlCol = c.getColumnIndex(NewsTableMetaData.IMAGEURL);
		int i =2;
		//Log.v(TAG, "readNews title is "+c.getString(i));
		
		//title.setText(2);
		Log.v(TAG, "readNews content is "+c.getString(contentCol));
		content.setText(c.getString(contentCol));
		long l = c.getLong(createdCol)*1000;
		Date d = new Date(l);
		created.setText(d.toString());
		imageUri = c.getString(imageUrlCol);*/

		 Bundle data = getIntent().getExtras();
		 News n = data.getParcelable("news");
		 
		 Drawable drawable = null;
		 if (n.getImageURL()==null) {
			image.setVisibility(View.GONE);
		} else {

			try {
				InputStream is =  (InputStream) new URL(n.getImageURL()).getContent();
				drawable = Drawable.createFromStream(is, "src name");
				
			} catch (MalformedURLException e) {
				Log.v(TAG, "e is :"+e);
			} catch (IOException e) {
				Log.v(TAG, "e is :"+e);
			}
		}
			 image.setImageDrawable(drawable);
		 
		 title.setText(n.getTitle());
		 content.setText(n.getContent());
		 
		 Date d = new Date(n.getCreated()*1000);
		 created.setText(d.toString());
		
	}

/*	private Cursor getCursor() {
		Log.v(TAG, "getCursor");
		Bundle extras = getIntent().getExtras(); 
		String uriString = extras.getString("uri");
		Uri uri = Uri.parse(uriString);
		return getContentResolver().query(uri, projection, null, null, null);
	}*/

	private void setUpViews() {
		Log.v(TAG, "setUpViews");
		image = (ImageView) findViewById(R.id.news_image);
		title = (TextView) findViewById(R.id.news_title);
		content =  (TextView) findViewById(R.id.news_content);
		created = (TextView) findViewById(R.id.news_created);
		
	}

}
