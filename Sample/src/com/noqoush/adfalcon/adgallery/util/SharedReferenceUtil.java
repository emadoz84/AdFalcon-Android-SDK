package com.noqoush.adfalcon.adgallery.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedReferenceUtil {

	public static void put(Context context, String key, String value) {
		try {
			SharedPreferences sharedPrefs = PreferenceManager
					.getDefaultSharedPreferences(context);
			Editor editor = sharedPrefs.edit();
			editor.putString(key, value);
			editor.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getValue(Context context, String key) {
		try {
			SharedPreferences sharedPrefs = PreferenceManager
					.getDefaultSharedPreferences(context);
			return sharedPrefs.getString(key, null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
}
