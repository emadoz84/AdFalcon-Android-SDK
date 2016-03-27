package com.noqoush.adfalcon.adgallery.adfhelper;

import com.noqoush.adfalcon.android.sdk.ADFTargetingParams;
import com.noqoush.adfalcon.android.sdk.nativead.assets.ADFAssetsBinder;

public class ADFAdsInfo {
	
	private String siteId;
	private ADFTargetingParams params;
	private ADFAssetsBinder binder;
	private ADFNativeAdAdapterListener adListener;
	private boolean testing;

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}


	public ADFTargetingParams getParams() {
		return params;
	}


	public void setParams(ADFTargetingParams params) {
		this.params = params;
	}


	public ADFAssetsBinder getBinder() {
		return binder;
	}


	public void setBinder(ADFAssetsBinder binder) {
		this.binder = binder;
	}


	public ADFNativeAdAdapterListener getAdListener() {
		return adListener;
	}


	public void setAdListener(ADFNativeAdAdapterListener adListener) {
		this.adListener = adListener;
	}

	public boolean isTesting() {
		return testing;
	}

	public void setTesting(boolean testing) {
		this.testing = testing;
	}

}
