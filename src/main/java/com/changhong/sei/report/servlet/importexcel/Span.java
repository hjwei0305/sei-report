package com.changhong.sei.report.servlet.importexcel;

/**
 * @desc：
 * @author：zhaohz
 * @date：2020/7/6 17:17
 */
public class Span {
	private int rowSpan;
	private int colSpan;
	public Span(int rowSpan,int colSpan) {
		this.rowSpan=rowSpan;
		this.colSpan=colSpan;
	}
	public int getRowSpan() {
		return rowSpan;
	}
	public int getColSpan() {
		return colSpan;
	}
}
