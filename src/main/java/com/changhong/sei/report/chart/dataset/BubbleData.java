package com.changhong.sei.report.chart.dataset;

/**
 * @desc：气泡图数据
 * @author：zhaohz
 * @date：2020/6/30 16:00
 */
public class BubbleData {
	private double x;
	private double y;
	private double r;
	
	public BubbleData(double x, double y, double r) {
		this.x = x;
		this.y = y;
		this.r = r;
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

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}
}
