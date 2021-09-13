package com.changhong.sei.report.chart.option.impl;

import com.changhong.sei.report.chart.option.Option;
import com.changhong.sei.report.enums.FontStyle;

/**
 * @desc：工具栏操作
 * @author：zhaohz
 * @date：2020/6/30 16:50
 */
public class TooltipOption implements Option {
	private boolean enabled=true;
	private int titleFontSize=12;
	private FontStyle titleFontStyle= FontStyle.bold;
	private String titleFontColor="#fff";
	private int bodyFontSize=12;
	private FontStyle bodyFontStyle= FontStyle.normal;
	private String bodyFontColor="#fff";
	
	@Override
	public String buildOptionJson() {
		StringBuilder sb=new StringBuilder();
		sb.append("\"tooltips\":{");
		sb.append("\"enabled\":").append(enabled).append(",");
		sb.append("\"titleFontSize\":").append(titleFontSize).append(",");
		sb.append("\"titleFontStyle\":\"").append(titleFontStyle).append("\",");
		sb.append("\"titleFontColor\":\"").append(titleFontColor).append("\",");
		sb.append("\"bodyFontSize\":").append(bodyFontSize).append(",");
		sb.append("\"bodyFontStyle\":\"").append(bodyFontStyle).append("\",");
		sb.append("\"bodyFontColor\":\"").append(bodyFontColor).append("\",");
		sb.append("}");
		return sb.toString();
	}

	@Override
	public String getType() {
		return "tooltips";
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getTitleFontSize() {
		return titleFontSize;
	}

	public void setTitleFontSize(int titleFontSize) {
		this.titleFontSize = titleFontSize;
	}

	public FontStyle getTitleFontStyle() {
		return titleFontStyle;
	}

	public void setTitleFontStyle(FontStyle titleFontStyle) {
		this.titleFontStyle = titleFontStyle;
	}

	public String getTitleFontColor() {
		return titleFontColor;
	}

	public void setTitleFontColor(String titleFontColor) {
		this.titleFontColor = titleFontColor;
	}

	public int getBodyFontSize() {
		return bodyFontSize;
	}

	public void setBodyFontSize(int bodyFontSize) {
		this.bodyFontSize = bodyFontSize;
	}

	public FontStyle getBodyFontStyle() {
		return bodyFontStyle;
	}

	public void setBodyFontStyle(FontStyle bodyFontStyle) {
		this.bodyFontStyle = bodyFontStyle;
	}

	public String getBodyFontColor() {
		return bodyFontColor;
	}

	public void setBodyFontColor(String bodyFontColor) {
		this.bodyFontColor = bodyFontColor;
	}
	
}
