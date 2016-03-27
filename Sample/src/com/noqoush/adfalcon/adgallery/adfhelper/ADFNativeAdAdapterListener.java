package com.noqoush.adfalcon.adgallery.adfhelper;

import android.view.View;

import com.noqoush.adfalcon.android.sdk.nativead.ADFNativeAd;

public interface ADFNativeAdAdapterListener {
	
	
	/*
	 * This will be called when the SDK begins loading new ads. 
	 * you can return a view, in case you want to display it while the ad is still loading. 
	 */
	View onNativeAdBeingLoaded(ADFNativeAd nativeAd);
	
	/*
	 * This will be called after the ad is loaded successfully and it is ready for viewing 
	 */
	void onNativeAdLoaded(ADFNativeAd nativeAd);
	
	/*
	 * This will be called before displaying the ad view. 
	 */
	void onViewBeingDisplayed(View v);
	
	/*
	 * This will be called if an error occurred or (no available ads) returned, 
	 * You can return a view to be displayed instead of the ad view.
	 */
	View onNativeAdFailed(ADFNativeAd nativeAd, String reason);	
	
	/*
	 * This will be called, if your app supports custom actions.
	 */
	boolean handleCustomAction(String data);
}
