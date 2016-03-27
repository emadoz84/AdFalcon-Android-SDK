package com.noqoush.adfalcon.adgallery.adfhelper;

import android.widget.BaseAdapter;
import android.widget.ListView;

import com.noqoush.adfalcon.android.sdk.nativead.assets.ADFAssetsBinder;

public class ADFNativeAdAdapterModel {

	private ListView listView;
	private BaseAdapter adapter;
	private int minAdHeight = 0;
	private ADFAdsPosition adsPosition = new ADFAdsPosition();
	private ADFAdsInfo adsInfo = new ADFAdsInfo();
	
	public ADFNativeAdAdapterModel(ListView listView, BaseAdapter adapter, String siteId, ADFAssetsBinder binder, boolean isTesting){
		setListView(listView);
		setAdapter(adapter);
		getAdsInfo().setBinder(binder);
		getAdsInfo().setSiteId(siteId);
		getAdsInfo().setTesting(isTesting);
	}
	
	public ADFNativeAdAdapterModel(ListView listView, BaseAdapter adapter, String siteId, ADFAssetsBinder binder, ADFNativeAdAdapterListener adapterListener, boolean isTesting){
		this(listView, adapter, siteId, binder, isTesting);
		getAdsInfo().setAdListener(adapterListener);
	}
	
	public ADFNativeAdAdapterModel(ListView listView, BaseAdapter adapter, String siteId, ADFAssetsBinder binder, ADFNativeAdAdapterListener adapterListener, int numberOfAds, int start, int space, boolean isTesting){
		this(listView, adapter, siteId, binder, adapterListener, isTesting);
		getAdsPosition().setNumberOfAds(numberOfAds);
		getAdsPosition().setStart(start);
		getAdsPosition().setSpace(space);
	}

	public BaseAdapter getAdapter() {
		return adapter;
	}


	public void setAdapter(BaseAdapter adapter) {
		this.adapter = adapter;
	}


	public int getMinAdHeight() {
		return minAdHeight;
	}


	public void setMinAdHeight(int minAdHeight) {
		this.minAdHeight = minAdHeight;
	}


	public ListView getListView() {
		return listView;
	}

	public void setListView(ListView listView) {
		this.listView = listView;
	}
	
	public ADFAdsPosition getAdsPosition() {
		return adsPosition;
	}

	
	public void setAdsPosition(ADFAdsPosition adsPosition) {
		this.adsPosition = adsPosition;
	}


	public ADFAdsInfo getAdsInfo() {
		return adsInfo;
	}


	public void setAdsInfo(ADFAdsInfo adsInfo) {
		this.adsInfo = adsInfo;
	}
	
}
