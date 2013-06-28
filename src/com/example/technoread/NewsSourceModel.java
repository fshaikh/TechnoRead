package com.example.technoread;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsSourceModel implements Parcelable {
	public String Title;
	public String Description;
	public String Url;
	public boolean OpenInline;
	
	
	public NewsSourceModel(Parcel in) {
		Title = in.readString();
		Description = in.readString();
		Url = in.readString();
		OpenInline = Boolean.parseBoolean(in.readString());
	}
	public NewsSourceModel() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(Title);
		dest.writeString(Description);
		dest.writeString(Url);
		dest.writeString(String.valueOf(OpenInline));
	}
	
	public static final Parcelable.Creator<NewsSourceModel> CREATOR
    = new Parcelable.Creator<NewsSourceModel>()
    {
		public NewsSourceModel createFromParcel(Parcel in) {
		    return new NewsSourceModel(in);
		}

		public NewsSourceModel[] newArray(int size) {
		    return null;
		}
    };
}
