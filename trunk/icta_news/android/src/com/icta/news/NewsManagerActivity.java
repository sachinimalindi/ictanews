package com.icta.news;

import android.app.Activity;


public class NewsManagerActivity extends Activity {

	public NewsManagerActivity() {
		super();
	}
	
	protected NewsManagerApplication getNewsManagerApplication() {
		return (NewsManagerApplication) getApplication();
	}

}
