package com.icta.news.model;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable{
	
	private int id;
	private String title;
	private long created;
	private String content;
	private String ImageURL;
	
	public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {

		@Override
		public News createFromParcel(Parcel source) {
			return new News(source);
		}

		@Override
		public News[] newArray(int size) {
			return new News[size];
		}
	};
	
	
	public News(Parcel in) {
		readFromParcel(in);
	}

	public News(int id, String title, long created, String content,
			String imageURL) {
		super();
		this.id = id;
		this.title = title;
		this.created = created;
		this.content = content;
		ImageURL = imageURL;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getCreated() {
		return created;
	}
	public void setCreated(long created) {
		this.created = created;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImageURL() {
		return ImageURL;
	}
	public void setImageURL(String imageURL) {
		ImageURL = imageURL;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(id);
		out.writeString(title);
		out.writeLong(created);
		out.writeString(content);
		out.writeString(ImageURL);
		
	}
	

	private void readFromParcel(Parcel in) {
		this.id = in.readInt();
		this.title = in.readString();
		this.created = in.readLong();
		this.content = in.readString();
		this.ImageURL = in.readString();
		
	}

}
