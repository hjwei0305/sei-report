package com.changhong.sei.report.cache;

import com.changhong.sei.report.servlet.RequestHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc：session报表缓存
 * @author：zhaohz
 * @date：2020/7/6 16:34
 */
public class HttpSessionReportCache implements ReportCache {
	private Map<String, ObjectMap> sessionReportMap=new HashMap<String, ObjectMap>();
	private boolean disabled;
	@Override
	public Object getObject(String file) {
		HttpServletRequest req= RequestHolder.getRequest();
		if(req==null){
			return null;
		}
		ObjectMap objMap = getObjectMap(req);
		return objMap.get(file);
	}

	@Override
	public void storeObject(String file, Object object) {
		HttpServletRequest req= RequestHolder.getRequest();
		if(req==null){
			return;
		}
		ObjectMap map = getObjectMap(req);
		map.put(file, object);
	}

	@Override
	public boolean disabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	private ObjectMap getObjectMap(HttpServletRequest req) {
		List<String> expiredList=new ArrayList<String>();
		for(String key:sessionReportMap.keySet()){
			ObjectMap reportObj=sessionReportMap.get(key);
			if(reportObj.isExpired()){
				expiredList.add(key);
			}
		}
		for(String key:expiredList){
			sessionReportMap.remove(key);
		}
		String sessionId=req.getSession().getId();
		ObjectMap obj=sessionReportMap.get(sessionId);
		if(obj!=null){
			return obj;
		}else{
			ObjectMap objMap=new ObjectMap();
			sessionReportMap.put(sessionId, objMap);
			return objMap;
		}
	}
}
