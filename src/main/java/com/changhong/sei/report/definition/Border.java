package com.changhong.sei.report.definition;

import com.changhong.sei.report.enums.BorderStyle;

import java.io.Serializable;

/**
 * @desc：边框
 * @author：zhaohz
 * @date：2020/6/30 9:43
 */
public class Border implements Serializable{
	private static final long serialVersionUID = 5320929211828633858L;
	/**
	 * 宽度
	 */
	private Integer width = 0;
	/**
	 * 颜色
	 */
	private String color;
	/**
	 * 样式
	 */
	private BorderStyle style;
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public BorderStyle getStyle() {
		return style;
	}
	public void setStyle(BorderStyle style) {
		this.style = style;
	}
}
