package com.changhong.sei.report.cache;

import com.changhong.sei.report.servlet.RequestHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @desc：对象缓存
 * @author：zhaohz
 * @date：2020/7/6 16:41
 */
public class TempObjectCache{
	private static TempObjectCache tempObjectCache=new TempObjectCache();
	private Map<String, ObjectMap> sessionMap=new HashMap<String, ObjectMap>();
	public static Object getObject(String key){
		return tempObjectCache.get(key);
	}
	public static void putObject(String key,Object obj){
		tempObjectCache.store(key, obj);
	}
	
	public static void removeObject(String key){
		tempObjectCache.remove(key);
	}
	
	public void remove(String key){
		HttpServletRequest req= RequestHolder.getRequest();
		if(req==null){
			return;
		}
		ObjectMap mapObject = getReportMap(req);
		if(mapObject!=null){
			mapObject.remove(key);
		}
	}
	
	public Object get(String key) {
		HttpServletRequest req= RequestHolder.getRequest();
		if(req==null){
			return null;
		}
		ObjectMap mapObject = getReportMap(req);
		return mapObject.get(key);
	}

	public void store(String key, Object obj) {
		HttpServletRequest req= RequestHolder.getRequest();
		if(req==null){
			return;
		}
		ObjectMap mapObject = getReportMap(req);
		mapObject.put(key, obj);
	}

	private ObjectMap getReportMap(HttpServletRequest req) {
		List<String> expiredList=new ArrayList<String>();
		for(String key:sessionMap.keySet()){
			ObjectMap reportObj=sessionMap.get(key);
			if(reportObj.isExpired()){
				expiredList.add(key);
			}
		}
		for(String key:expiredList){
			sessionMap.remove(key);
		}
		String sessionId=req.getSession().getId();
		ObjectMap obj=sessionMap.get(sessionId);
		if(obj!=null){
			return obj;
		}else{
			ObjectMap mapObject=new ObjectMap();
			sessionMap.put(sessionId, mapObject);
			return mapObject;
		}
	}
}
