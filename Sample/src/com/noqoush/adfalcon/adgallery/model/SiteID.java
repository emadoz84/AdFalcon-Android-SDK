package com.noqoush.adfalcon.adgallery.model;

import java.util.StringTokenizer;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SiteID implements Parcelable {

	@SerializedName("name")
	public String name;
	@SerializedName("siteID")
	public String siteID;
	@SerializedName("testing")
	public boolean testing;
	@SerializedName("defaultSiteID")
	public boolean defaultSiteID;

	public SiteID() {
		
	}
	
	public SiteID(String ids) {
		StringTokenizer tokenizer = new StringTokenizer(ids, ":");
		this.name = tokenizer.nextToken();
		String flag = tokenizer.nextToken();
		this.testing = Boolean.parseBoolean(flag);
		flag = tokenizer.nextToken();
		this.defaultSiteID = Boolean.parseBoolean(flag);
		this.siteID = tokenizer.nextToken();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSiteID() {
		return siteID;
	}

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}

	public boolean isTesting() {
		return testing;
	}

	public void setTesting(boolean testing) {
		this.testing = testing;
	}

	public String getTitle() {
		return getName() + " " + (isTesting() ? "Testing" : "");
	}

	private SiteID(Parcel in) {

		setName(in.readString());
		setSiteID(in.readString());
		setTesting((in.readInt() == 0 ? false : true));
		setDefaultSiteID((in.readInt() == 0 ? false : true));
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {

		out.writeString(getName());
		out.writeString(getSiteID());
		out.writeInt((isTesting() ? 1 : 0));
		out.writeInt((isDefaultSiteID() ? 1 : 0));
	}

	public static final Parcelable.Creator<SiteID> CREATOR = new Parcelable.Creator<SiteID>() {
		public SiteID createFromParcel(Parcel in) {
			return new SiteID(in);
		}

		public SiteID[] newArray(int size) {
			return new SiteID[size];
		}
	};

	public boolean isDefaultSiteID() {
		return defaultSiteID;
	}

	public void setDefaultSiteID(boolean defaultSiteID) {
		this.defaultSiteID = defaultSiteID;
	}

	@Override
	public boolean equals(Object o) {
		SiteID siteId = (SiteID) o;
		return getSiteID().equalsIgnoreCase(siteId.getSiteID()) && siteId.isTesting() == isTesting();
	}
}
