package com.noqoush.adfalcon.adgallery.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ad implements Parcelable {

	private String serverKey;
	private String serverValue;
	private String title;

	public Ad(String title, String key, String value) {
		setTitle(title);
		setServerKey(key);
		setServerValue(value);
	}

	private Ad(Parcel in) {
		setTitle(in.readString());
		setServerKey(in.readString());
		setServerValue(in.readString());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeString(getTitle());
		out.writeString(getServerKey());
		out.writeString(getServerValue());
	}

	public static final Parcelable.Creator<Ad> CREATOR = new Parcelable.Creator<Ad>() {
		public Ad createFromParcel(Parcel in) {
			return new Ad(in);
		}

		public Ad[] newArray(int size) {
			return new Ad[size];
		}
	};

	public String getServerValue() {
		return serverValue;
	}

	private void setServerValue(String serverValue) {
		this.serverValue = serverValue;
	}

	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	public String getServerKey() {
		return serverKey;
	}

	private void setServerKey(String serverKey) {
		this.serverKey = serverKey;
	}

	@Override
	public String toString() {
		return getTitle();
	}

}
