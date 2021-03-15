package com.icbcbecode.util;

import com.alibaba.fastjson.JSON;

public class JsonUtils {
	
	public static boolean isOrNotJson(String jsonStr) {
		try {
			JSON.parse(jsonStr);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

}
