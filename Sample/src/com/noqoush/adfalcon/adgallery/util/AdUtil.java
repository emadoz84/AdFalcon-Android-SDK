package com.noqoush.adfalcon.adgallery.util;

import com.noqoush.adfalcon.adgallery.model.AdUnitSize;
import com.noqoush.adfalcon.android.sdk.constant.ADFAdSize;

public class AdUtil {
	public static ADFAdSize wrapperAdUnitSize(AdUnitSize pUnitSize) {
		int size = Integer.parseInt(pUnitSize.getServerValue());
		switch (size) {
		case 0:
			return ADFAdSize.AD_UNIT_AUTO_BANNER;

		case 1:
			return ADFAdSize.AD_UNIT_AUTO_BANNER;

		case 2:
			return ADFAdSize.AD_UNIT_320x50;
		case 6:
			return ADFAdSize.AD_UNIT_468x60;

		case 7:
			return ADFAdSize.AD_UNIT_728x90;

		case 8:
			return ADFAdSize.AD_UNIT_300x250;

		case 9:
			return ADFAdSize.AD_UNIT_120x600;

		case 10:
			return ADFAdSize.AD_UNIT_320x50;
		default:
			return ADFAdSize.AD_UNIT_AUTO_BANNER;

		}
	}
}
