package com.changhong.sei.report.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc：资源缓存
 * @author：zhaohz
 * @date：2020/6/30 16:21
 */
public class ResourceCache {
	private static Map<String,Object> map=new HashMap<String,Object>();
	public static void putObject(String key,Object obj){
		if(map.containsKey(key)){
			map.remove(key);
		}
		map.put(key, obj);
	}
	public static Object getObject(String key){
		return map.get(key);
	}
}
