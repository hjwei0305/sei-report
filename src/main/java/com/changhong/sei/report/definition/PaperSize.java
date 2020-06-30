package com.changhong.sei.report.definition;

/**
 * @desc：用于返回当前页面尺寸，单位是Point，也就是pt，1mm=2.83pt,1pt=0.35mm
 * @author：zhaohz
 * @date：2020/6/30 10:05
 */
public class PaperSize {
	private int width;
	private int height;
	
	public PaperSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}
