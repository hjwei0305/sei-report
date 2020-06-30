package com.changhong.sei.report.chart.option.impl;

import com.changhong.sei.report.chart.option.Labels;
import com.changhong.sei.report.chart.option.Option;
import com.changhong.sei.report.chart.option.Position;

/**
 * @desc：
 * @author：zhaohz
 * @date：2020/6/30 16:48
 */
public class LegendOption implements Option {
	private boolean display=true;
	private Position position= Position.top;
	private Labels labels;
	@Override
	public String buildOptionJson() {
		StringBuilder sb=new StringBuilder();
		sb.append("\"legend\":{");
		sb.append("\"display\":"+display+",");
		sb.append("\"position\":\""+position+"\"");
		if(labels!=null){
			sb.append("\"labels\":"+labels.toJson());			
		}
		sb.append("}");
		return sb.toString();
	}
	@Override
	public String getType() {
		return "legend";
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Labels getLabels() {
		return labels;
	}
	public void setLabels(Labels labels) {
		this.labels = labels;
	}
}
