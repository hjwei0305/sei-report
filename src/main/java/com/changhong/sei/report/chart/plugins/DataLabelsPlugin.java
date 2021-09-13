package com.changhong.sei.report.chart.plugins;

/**
 * @desc：数据标签插件
 * @author：zhaohz
 * @date：2020/6/30 15:48
 */
public class DataLabelsPlugin implements Plugin {
	private boolean display;
	@Override
	public String getName() {
		return "data-labels";
	}
	@Override
	public String toJson(String type) {
		StringBuilder sb=new StringBuilder();
		sb.append("\"datalabels\":{\"display\":").append(display).append(",");
		sb.append("\"font\":{");
		sb.append("\"size\":14,");
		sb.append("\"weight\":\"bold\"");
		sb.append("}");
		sb.append("}");
		return sb.toString();
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
}
