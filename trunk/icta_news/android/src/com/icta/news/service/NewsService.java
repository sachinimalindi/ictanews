package com.icta.news.service;

import com.icta.news.NewsManager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class NewsService extends Service {
	
	private static final String TAG = "NewsService";
	
	private NewsManager manager = new NewsManager();

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d(TAG, "onBind invoked");
		return stub;
	}

	
	private INewsDownloaderService.Stub stub = new INewsDownloaderService.Stub() {
		
		@Override
		public int getNews() throws RemoteException {
			Log.d(TAG, "getNews");
			manager.getNews();
			Log.d(TAG, "manager.getNews()");
			manager.saveNews(getApplicationContext());
			Log.d(TAG, "saveNews(getApplicationContext()");
			return manager.newsSize();
		}
	};

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		// TODO set Notification
		//use android.app.NotificationManager
		// and android.app.Notification 
		super.onCreate();
	}


	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		// TODO cancel Notification
		//and kill callback 
		super.onDestroy();
	}


	@Override
	public void onStart(Intent intent, int startId) {
		Log.d(TAG, "onStart");
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}
	
	/*class DownloaderTask implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}*/
	
}
