package com.icta.news.ui.adapter;

import java.util.ArrayList;

import com.icta.news.R;
import com.icta.news.model.News;
import com.icta.news.ui.layout.NewsListItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

public class NewsListAdapter extends ArrayAdapter<News> {

	private Context context;
	private ArrayList<News> news;
	
	
	public NewsListAdapter(Context context, ArrayList<News> news) {
		super(context, R.layout.news_list_item, news);
		this.context = context;
		this.news = news;
	}
	
	
	//public void forceReload() {
	//	notifyDataSetChanged();
	//}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NewsListItem listItem;
		if ( null == convertView ) {
			listItem = (NewsListItem) View.inflate(context, R.layout.news_list_item , null);
		} else {
            listItem = (NewsListItem) convertView ;
		}
		
		listItem.setNews(news.get(position));
		return listItem;
	}
	
	 public void appendNewer(ArrayList<News> nList){
		 setNotifyOnChange(false);
		 for (News news : nList) {
			//insert(news, 0);
			 add(news);
		}
		 notifyDataSetChanged();

	 }

}
