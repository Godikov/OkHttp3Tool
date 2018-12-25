package com.humu.myokhttp3.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class JsonUtil
{

	private JsonUtil()
	{
	}

	public static <T> T json2Object(String json, Class<T> clazz)
	{
		return JSON.parseObject(json, clazz);
	}

	public static String object2Json(Object obj)
	{
		return JSON.toJSONString(obj);
	}

	public static <T> T map2Object(Map map, Class<T> clazz)
	{
		T t = null;
		if (map != null)
		{
			String json = object2Json(map);
			t = json2Object(json, clazz);
		}
		return t;
	}

	/**
	 * 判断字符串是否是json格式
	 * @param content json字符串
	 * @return
	 */
	public static boolean isJson(String content) {

		if(TextUtils.isEmpty(content)){
			return false;
		}

		try {
			JSONObject.parseObject(content);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

}
