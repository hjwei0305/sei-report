package com.changhong.sei.report.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * @desc：
 * @author：zhaohz
 * @date：2020/7/6 16:39
 */
public class RequestHolder {
	private static final ThreadLocal<HttpServletRequest> requestThreadLocal=new ThreadLocal<HttpServletRequest>();
	public static void setRequest(HttpServletRequest request){
		requestThreadLocal.set(request);
	}
	public static HttpServletRequest getRequest(){
		return requestThreadLocal.get();
	}
	public static void clean(){
		requestThreadLocal.remove();
	}
}
