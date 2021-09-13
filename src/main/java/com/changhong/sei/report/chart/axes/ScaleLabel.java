package com.changhong.sei.report.chart.axes;

import com.changhong.sei.report.enums.FontStyle;

/**
 * @desc：规模标签
 * @author：zhaohz
 * @date：2020/6/30 15:55
 */
public class ScaleLabel {
	private boolean display=false;
	private String labelString="";
	private String fontColor="#666";
	private int fontSize=12;
	private FontStyle fontStyle= FontStyle.normal;
	
	public String toJson(){
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("\"display\":").append(display).append(",");
		sb.append("\"labelString\":\"").append(labelString).append("\",");
		sb.append("\"fontColor\":\"").append(fontColor).append("\",");
		sb.append("\"fontSize\":").append(fontSize).append(",");
		sb.append("\"fontStyle\":\"").append(fontStyle).append("\"");
		sb.append("}");
		return sb.toString();
	}
	
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public String getLabelString() {
		return labelString;
	}
	public void setLabelString(String labelString) {
		this.labelString = labelString;
	}
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public FontStyle getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(FontStyle fontStyle) {
		this.fontStyle = fontStyle;
	}
}
