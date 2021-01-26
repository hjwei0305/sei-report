package com.changhong.sei.report.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @desc：servlet接口
 * @author：zhaohz
 * @date：2020/7/6 16:39
 */
public interface ServletAction {
	public static final String PREVIEW_KEY="p";
	void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
	String url();
}
