package com.icta.news.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class RestClient {
	
	private static final String TAG = "RestClient";

	
	 private int responseCode;
	 //url = http://www.icta.lk/apps/services/getNews.php
	 public String get(String url) {
		 HttpGet getRequest = new HttpGet(url);
		 HttpClient httpclient = new DefaultHttpClient();  
		 String response ="";
		 HttpResponse getResponse;
		try {
			getResponse = httpclient.execute(getRequest);
			responseCode=getResponse.getStatusLine().getStatusCode();
			 HttpEntity entity =getResponse.getEntity();
			 
			 if(entity!= null){
				 InputStream stream = entity.getContent();
				 response=  convertStreamToString(stream);
			     stream.close();
			 }
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		return response;
		
	}
	 
	 private  String convertStreamToString(InputStream is) {
		 
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder sb = new StringBuilder();
	 
	        String line = null;
	        try {
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return sb.toString();
	    }
	 
	/* public Bitmap retrieveBitmap(String url) throws Exception {
		  InputStream inputStream = null;
		      try {
		         inputStream = this.retrieveStream(url);
		         final Bitmap bitmap = BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
		         return bitmap;
		      }
		      finally {
		         Utils.closeStreamQuietly(inputStream);
		      }
		   }*/
		   /////////////// json example :P
		   
	 
	 /*Log.i("Praeda",result);
	 
     // A Simple JSONObject Creation
     JSONObject json=new JSONObject(result);
     Log.i("Praeda","<jsonobject>\n"+json.toString()+"\n</jsonobject>");

     // A Simple JSONObject Parsing
     JSONArray nameArray=json.names();
     JSONArray valArray=json.toJSONArray(nameArray);
     for(int i=0;i<valArray.length();i++)
     {
         Log.i("Praeda","<jsonname"+i+">\n"+nameArray.getString(i)+"\n</jsonname"+i+">\n"
                 +"<jsonvalue"+i+">\n"+valArray.getString(i)+"\n</jsonvalue"+i+">");
     }

     // A Simple JSONObject Value Pushing
     json.put("sample key", "sample value");
     Log.i("Praeda","<jsonobject>\n"+json.toString()+"\n</jsonobject>");*/

}
