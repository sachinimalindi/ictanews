package com.icta.news.service;

import java.util.ArrayList;
import java.util.List;

import com.icta.news.model.News;
import com.icta.news.ui.adapter.NewsListAdapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListAdapter;

public class NewsServiceHelper {
	
	public static final String TAG = "NewsServiceHelper";
	
	private Context context;
	
	private INewsDownloaderService downloaderService;
	private boolean started = false;
	private NewsServiceConnection conn = null;
	private NewsListAdapter adapter;

	public NewsServiceHelper(Context c) {
		Log.v(TAG, "NewsServiceHelper");
		context = c ;
		init();
	}

	private void init() {
		
		Log.v(TAG, "init");
	}
	
	public void startService() {
		Log.v(TAG, "startService");
		if (started) {
			//"Service already started"
			Log.v(TAG, "startService Service already started");
		} else {
			Log.v(TAG, "startService  is starting");
            Intent intent = new Intent();
           intent.setClassName("com.icta.news.service", "com.icta.news.service.NewsService");
           context.startService(intent);
           started = true;
		}
		
	}
	
	public void stopService() {
		Log.v(TAG, "stopService");
		if (!started) {
			//Service not yet started
			Log.v(TAG, "stopService  Service not yet started");
		} else {
			Log.v(TAG, "stopService is stoping");
			//Intent intent = new Intent();
			Intent intent = new Intent(context,NewsService.class);
	        //intent.setClassName("com.icta.news.service", "com.icta.news.service.NewsService");
	        context.stopService(intent);
	        started = false;
		}
	}
	
	public void bindService(ListAdapter listAdapter) {
		Log.v(TAG, "bindService");
		adapter= (NewsListAdapter) listAdapter;
		if (conn == null) {
			Log.v(TAG, "obindService  conn is null :P" );
			conn = new NewsServiceConnection();
			Log.v(TAG, "bindService conn is cretated");
			Intent intent = new Intent("com.icta.news.service.NewsService");
	      //  intent.setClassName("com.icta.news.service", "com.icta.news.service.NewsService");
	        context.bindService(intent, conn,Context.BIND_AUTO_CREATE);
		} else {
           //Cannot bind - service already bound
			Log.v(TAG, "bindService  Cannot bind - service already bound");
		}
	}
	
	public void releaseService() {
		Log.v(TAG, "releaseService");
		if (conn != null) {
			Log.v(TAG, "releaseService conn is not null :P");
			context.unbindService(conn);
			conn = null ;
		} else {
			Log.v(TAG, "releaseService Cannot unbind - service not bound");
           //Cannot unbind - service not bound
		}
		
	}
	
	
	public List<News> invokeService() {
		Log.v(TAG, "invokeService");
		if (conn == null) {
			Log.v(TAG, "invokeService Cannot invoke - service not bound");
			//Cannot invoke - service not bound
			return null;
		} else {
			Log.v(TAG, "invokeService is invoking ");
			try {
				Log.v(TAG, "invokeService inside try");
				Log.v(TAG, "invokeService service is :"+downloaderService);
				 return downloaderService.getNews();
				 
				//Log.v(TAG, "counter is : "+counter);
			} catch (Exception e) {
				Log.v(TAG, "e is : "+e.getMessage());
				e.printStackTrace();
				e.getCause();
				e.toString();
				return null;
			}
		}
		
	}
	
	public boolean isServiceConnection() {
		if (downloaderService == null) {
			return false;
		} else {
           return true;
		}
	}
	
	  class NewsServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.v(TAG, "onServiceConnected");
			downloaderService = INewsDownloaderService.Stub.asInterface(service);
			Log.v(TAG, "onServiceConnected is");
			ArrayList<News> n =(ArrayList<News>) invokeService();
			Log.v(TAG, "onServiceConnected news are  : "+n.size());
			adapter.appendNewer(n);
			releaseService();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.v(TAG, "onServiceDisconnected");
			downloaderService = null ;
			
		}
		  
	  }

}
