package com.changhong.sei.report.definition.value;

import com.changhong.sei.report.chart.Chart;
import com.changhong.sei.report.enums.ValueType;

/**
 * @desc：图表值
 * @author：zhaohz
 * @date：2020/6/30 15:46
 */
public class ChartValue implements Value {
	private Chart chart;
	@Override
	public String getValue() {
		return null;
	}
	@Override
	public ValueType getType() {
		return ValueType.chart;
	}
	public void setChart(Chart chart) {
		this.chart = chart;
	}
	public Chart getChart() {
		return chart;
	}
}
