package com.changhong.sei.report.chart.option.impl;

import com.changhong.sei.report.chart.option.Easing;
import com.changhong.sei.report.chart.option.Option;

/**
 * @desc：动画动作
 * @author：zhaohz
 * @date：2020/6/30 16:46
 */
public class AnimationsOption implements Option {
	private int duration=1000;
	private Easing easing= Easing.easeOutQuart;
	@Override
	public String buildOptionJson() {
		StringBuilder sb=new StringBuilder();
		sb.append("\"animation\":{");
		sb.append("\"duration\":"+duration+",");
		sb.append("\"easing\":\""+easing+"\"");
		sb.append("}");
		return sb.toString();
	}
	@Override
	public String getType() {
		return "animation";
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Easing getEasing() {
		return easing;
	}
	public void setEasing(Easing easing) {
		this.easing = easing;
	}
}
