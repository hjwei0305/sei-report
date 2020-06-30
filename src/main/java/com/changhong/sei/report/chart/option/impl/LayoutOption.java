package com.changhong.sei.report.chart.option.impl;

import com.changhong.sei.report.chart.option.Option;
import com.changhong.sei.report.chart.option.Padding;

/**
 * @desc：布局动作
 * @author：zhaohz
 * @date：2020/6/30 16:47
 */
public class LayoutOption implements Option {
	private Padding padding;
	@Override
	public String buildOptionJson() {
		StringBuilder sb=new StringBuilder();
		sb.append("\"layout\":{");
		sb.append("\"padding\":"+padding.toJson());
		sb.append("}");
		return sb.toString();
	}
	@Override
	public String getType() {
		return "layout";
	}
	public Padding getPadding() {
		return padding;
	}

	public void setPadding(Padding padding) {
		this.padding = padding;
	}
}
