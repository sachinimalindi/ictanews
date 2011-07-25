package com.icta.news.ui.layout;

import java.util.Date;

import com.icta.news.R;
import com.icta.news.model.News;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NewsListItem extends RelativeLayout {
	
	private News news;
	private TextView title;
	private TextView date;
	
	public NewsListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		title = (TextView) findViewById(R.id.list_item_title);
		date = (TextView) findViewById(R.id.list_item_date);
	}

	public void setNews(News news) {
		this.news = news;
		title.setText(news.getTitle());
		Date d = new Date(news.getCreated()*1000);
		date.setText(d.toString());
	}

	public News getNews() {
		return news;
	}
	
	

}
