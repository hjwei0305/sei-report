package com.changhong.sei.report.model;

import java.io.Serializable;

/**
 * @desc：（单元格）范围
 * @author：zhaohz
 * @date：2020/6/29 16:14
 */
public class Range implements Serializable{
	private int start=-1;
	private int end;
	public Range() {
	}
	public Range(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getStart() {
		return start;
	}
	public int getEnd() {
		return end;
	}
}
