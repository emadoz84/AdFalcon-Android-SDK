package com.noqoush.adfalcon.adgallery.util;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONUtil {

	public static String toJSON(Object obj) {
		return new GsonBuilder().create().toJson(obj);
	}
	
	public static <T> T fromJSON(String json, Class<T> typeOfClass) {
		Gson gson = new Gson();
		return gson.fromJson(json, typeOfClass);
	}
	
	public static <T> List<T> fromJSON(String json, Type listType) {
		
		//Type listType = new TypeToken<List<T>>() {}.getType();
		Gson gson = new Gson();
		
		return gson.fromJson(json, listType);
	}

}
