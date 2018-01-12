package com.zyzf.polymer.pay.common.core.utils;

import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;

public class JSONStringBuilder {
	private JSONObject js = null;
	
	public JSONStringBuilder(String json){
		js = JSONObject.parseObject(json);
	}
	
	public JSONStringBuilder(JSONObject js){
		this.js = js;
	}
	
	/**
	 * 从json对象中获得字符串
	 * @param list
	 * @return
	 */
	public static String getJSONString(List<Object> list) {
		return JSONObject.toJSONString(list);
	}
	/**
	 * 从Map对象中获得json字符串
	 * @param map
	 * @return
	 */
	public static String getJSONString(Map<Object,Object> map) {
		return JSONObject.toJSONString(map);
	}
	
	public static String getJSONString(Object obj) {
		return JSONObject.toJSONString(obj);
	}

	/**
	 * 根据Key获得值
	 * @param key
	 * @return
	 */
	public String getString(String key){
		return js.containsKey(key) ? js.getString(key) : "";
	}

}
