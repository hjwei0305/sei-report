package com.changhong.sei.report.servlet;

import com.changhong.sei.report.cache.CacheUtils;
import com.changhong.sei.report.chart.ChartData;
import com.changhong.sei.report.utils.UnitUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @desc：图表
 * @author：zhaohz
 * @date：2020/7/6 16:46
 */
public class ChartServletAction extends RenderPageServletAction {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method=retriveMethod(req);
		if(method!=null){
			invokeMethod(method, req, resp);
		}
	}
	
	public void storeData(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String file=req.getParameter("_u");
		file=decode(file);
		String chartId=req.getParameter("_chartId");
		ChartData chartData= CacheUtils.getChartData(chartId);
		if(chartData==null){
			return;
		}
		String base64Data=req.getParameter("_base64Data");
		String prefix="data:image/png;base64,";
		if(base64Data!=null){
			if(base64Data.startsWith(prefix)){
				base64Data=base64Data.substring(prefix.length(),base64Data.length());
			}
		}
		chartData.setBase64Data(base64Data);
		String width=req.getParameter("_width");
		String height=req.getParameter("_height");
		chartData.setHeight(UnitUtils.pixelToPoint(Integer.valueOf(height)));
		chartData.setWidth(UnitUtils.pixelToPoint(Integer.valueOf(width)));
	}

	@Override
	public String url() {
		return "/chart";
	}
}
