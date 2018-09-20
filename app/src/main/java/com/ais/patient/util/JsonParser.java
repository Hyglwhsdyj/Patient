package com.ais.patient.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class JsonParser
{
    public static Gson gson = new Gson();

    public static <T> T fromJson(String data, Class<T> clz)
    {
        return gson.fromJson(data, clz);
    }

    public static <T> T fromJson(String data, Type listType)
    {
        return gson.fromJson(data, listType);
    }

    public static <T> String toJson(T t)
    {
        return gson.toJson(t);
    }
    
	public static <E> String getJsonStringByList(List<E> list) 
	{
		StringBuilder strJson = new StringBuilder("[");
		Gson gson = new Gson();
		for (int i = 0; i < list.size(); i++) {
			if (i != list.size() - 1) {
				strJson.append(gson.toJson(list.get(i)) + ",");
			} else {
				strJson.append(gson.toJson(list.get(i)));
			}
		}
		strJson = strJson.append("]");
		return strJson.toString();
	}
}

