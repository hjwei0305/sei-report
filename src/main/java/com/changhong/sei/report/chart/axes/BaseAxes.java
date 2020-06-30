package com.changhong.sei.report.chart.axes;

/**
 * @desc：原型抽象类
 * @author：zhaohz
 * @date：2020/6/30 15:54
 */
public abstract class BaseAxes implements Axes {
	private int rotation;
	private ScaleLabel scaleLabel;
	public int getRotation() {
		return rotation;
	}
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	public ScaleLabel getScaleLabel() {
		return scaleLabel;
	}
	public void setScaleLabel(ScaleLabel scaleLabel) {
		this.scaleLabel = scaleLabel;
	}
}
