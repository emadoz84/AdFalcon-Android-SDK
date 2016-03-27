package com.noqoush.adfalcon.adgallery.model;

import java.util.Vector;

import android.os.Parcel;
import android.os.Parcelable;

public class AdGroup implements Parcelable{
	
	private String title;
	private Vector<AdUnitSize> sizes = new Vector<AdUnitSize>();
	private Ad type;
	private String siteID;
	private int resID;
	
	public AdGroup(String title, AdType type, int resID) {
		setTitle(title);
		setType(type);
		setResID(resID);
	}
	
	public AdGroup(String title, AdType type, String siteID, int resID) {
		setTitle(title);
		setType(type);
		setSiteID(siteID);
		setResID(resID);
	}

	public void setSizes(Vector<AdUnitSize> sizes) {
		this.sizes = sizes;
	}

	public Vector<AdUnitSize> getSizes() {
		return sizes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Ad getType() {
		return type;
	}

	public void setType(Ad type) {
		this.type = type;
	}

	public String getSiteID() {
		return siteID;
	}

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}
	
	@Override
	public String toString() {
		return getTitle() + "\n" + "Ad Type: " + getType().getTitle() + "\nSizes: " + getSizes().size();
	}

	////////////////////////////////////////
	
	private AdGroup(Parcel in) {
		setSiteID(in.readString());
		setTitle(in.readString());
		setType((Ad) in.readParcelable(getClass().getClassLoader()));
		in.readList(sizes, getClass().getClassLoader());
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeString(getSiteID());
		out.writeString(getTitle());
		out.writeParcelable(getType(), 0);
		out.writeList(getSizes());

	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	public int getResID() {
		return resID;
	}

	public void setResID(int resID) {
		this.resID = resID;
	}

	public static final Parcelable.Creator<AdGroup> CREATOR = new Parcelable.Creator<AdGroup>() {
		public AdGroup createFromParcel(Parcel in) {
			return new AdGroup(in);
		}

		public AdGroup[] newArray(int size) {
			return new AdGroup[size];
		}
	};
}
