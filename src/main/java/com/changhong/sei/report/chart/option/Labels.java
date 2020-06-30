package com.changhong.sei.report.chart.option;

import com.changhong.sei.report.enums.FontStyle;

/**
 * @desc：标签
 * @author：zhaohz
 * @date：2020/6/30 16:44
 */
public class Labels {
	private int boxWidth=40;
	private int fontSize=12;
	private FontStyle fontStyle= FontStyle.normal;
	private String fontColor="#666";
	private int padding=10;
	public String toJson(){
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("boxWidth:"+boxWidth+",");
		sb.append("fontSize:"+fontSize+",");
		sb.append("fontStyle:\""+fontStyle+"\",");
		sb.append("fontColor:\""+fontColor+"\"");
		sb.append("}");
		return sb.toString();
	}
	public int getBoxWidth() {
		return boxWidth;
	}
	public void setBoxWidth(int boxWidth) {
		this.boxWidth = boxWidth;
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
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	public int getPadding() {
		return padding;
	}
	public void setPadding(int padding) {
		this.padding = padding;
	}
}
