package com.icta.news;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import com.icta.news.model.News;
import com.icta.news.model.NewsProviderMetaData;
import com.icta.news.model.NewsProviderMetaData.NewsTableMetaData;
import com.icta.news.net.JsonParser;
import com.icta.news.net.RestClient;

public class NewsManager {
	
	private static final String TAG = "NewsManager";

	private String url = "http://www.icta.lk/apps/services/getNews.php";
	private RestClient client;
	private JsonParser parser;
	
	private ArrayList<News> news;
	//ContentValues newsData = new ContentValues();
	
	public NewsManager() {
		init();
	}

	private void init() {
		client = new RestClient();
		
	}
	
	public List<News> getNews() {
		parser = new JsonParser(client.get(url));
		return news = parser.getNews();
	}
	
	public void saveNews(Context c) {
		for (News ne : news) {
			ContentValues newsData = new ContentValues();
			newsData.put(NewsTableMetaData.TITLE, ne.getTitle());
			newsData.put(NewsTableMetaData.CREATED, ne.getCreated());
			newsData.put(NewsTableMetaData.CONTENT, ne.getContent());
			newsData.put(NewsTableMetaData.IMAGEURL, ne.getImageURL());
			
			c.getContentResolver().insert(NewsTableMetaData.CONTENT_URI, newsData);
		}
		
	}
	
	public int newsSize() {
		return news.size();
	}
		
}
