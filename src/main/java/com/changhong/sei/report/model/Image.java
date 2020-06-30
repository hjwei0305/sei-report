package com.changhong.sei.report.model;

/**
 * @desc：图片
 * @author：zhaohz
 * @date：2020/6/30 17:46
 */
public class Image {
	private String base64Data;
	private String path;
	private int width;
	private int height;
	public Image(String base64Data, int width, int height) {
		this.base64Data=base64Data;
		this.width=width;
		this.height=height;
	}
	public Image(String base64Data, String path, int width, int height) {
		this.base64Data=base64Data;
		this.path=path;
		this.width=width;
		this.height=height;
	}
	public String getBase64Data() {
		return base64Data;
	}
	public String getPath() {
		return path;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}
