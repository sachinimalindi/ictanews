package com.icta.news.net;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;

import com.icta.news.model.News;

public class JsonParser {

	ArrayList<News> news = new ArrayList<News>();
	News currentNews;

	public JsonParser(String json) {
		buildNews(json);
	}
	
	private void buildNews(String json) {
		JSONArray aryJSONStrings;
		try {
			aryJSONStrings = new JSONArray(json);
			
			for (int i=0; i<aryJSONStrings.length(); i++) {
				setCurrentNews();
				currentNews.setTitle(aryJSONStrings.getJSONObject(i).getString("title"));
				 DateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 Date date = parser.parse(aryJSONStrings.getJSONObject(i).getString("created"));
				currentNews.setCreated(date.getTime()/1000);
				currentNews.setContent(aryJSONStrings.getJSONObject(i).getString("content"));
				currentNews.setImageURL(aryJSONStrings.getJSONObject(i).getString("image"));
				addNews(currentNews);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	private void addNews(News news) {
		this.news.add(news);
	}

	private void setCurrentNews() {
		News currentN = new News(0, null, 0, null, null);
		this.currentNews = currentN;
	}
	
	public ArrayList<News> getNews() {	
		return news;
	}

	
}
