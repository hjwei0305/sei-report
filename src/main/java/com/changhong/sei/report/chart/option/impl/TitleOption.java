package com.changhong.sei.report.chart.option.impl;

import com.changhong.sei.report.chart.option.Option;
import com.changhong.sei.report.chart.option.Position;
import com.changhong.sei.report.enums.FontStyle;

/**
 * @desc：标题操作
 * @author：zhaohz
 * @date：2020/6/30 16:49
 */
public class TitleOption implements Option {
	private boolean display;
	private Position position= Position.top;
	private int fontSize=14;
	private String fontColor="#666";
	private FontStyle fontStyle= FontStyle.bold;
	private int padding=10;
	private String text;
	@Override
	public String buildOptionJson() {
		StringBuilder sb=new StringBuilder();
		sb.append("\"title\":{");
		sb.append("\"display\":").append(display).append(",");
		sb.append("\"text\":\"").append(text).append("\",");
		sb.append("\"position\":\"").append(position).append("\",");
		sb.append("\"fontSize\":").append(fontSize).append(",");
		sb.append("\"fontColor\":\"").append(fontColor).append("\",");
		sb.append("\"fontStyle\":\"").append(fontStyle).append("\",");
		sb.append("\"padding\":\"").append(padding).append("\"");
		sb.append("}");
		return sb.toString();
	}
	@Override
	public String getType() {
		return "title";
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	public FontStyle getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(FontStyle fontStyle) {
		this.fontStyle = fontStyle;
	}
	public int getPadding() {
		return padding;
	}
	public void setPadding(int padding) {
		this.padding = padding;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
