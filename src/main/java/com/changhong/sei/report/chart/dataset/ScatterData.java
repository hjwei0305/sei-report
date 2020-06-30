package com.changhong.sei.report.chart.dataset;

/**
 * @desc：散点图数据
 * @author：zhaohz
 * @date：2020/6/30 16:02
 */
public class ScatterData {
	private double x;
	private double y;
	
	public ScatterData(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
}
