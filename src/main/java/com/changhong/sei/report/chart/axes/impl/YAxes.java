package com.changhong.sei.report.chart.axes.impl;

import com.changhong.sei.report.chart.axes.BaseAxes;
import com.changhong.sei.report.chart.axes.ScaleLabel;
import com.changhong.sei.report.chart.axes.YPosition;

/**
 * @desc：
 * @author：zhaohz
 * @date：2020/6/30 15:56
 */
public class YAxes extends BaseAxes {
	private YPosition yposition;
	@Override
	public String toJson() {
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("\"ticks\":{");
		sb.append("\"minRotation\":"+getRotation()+"");
		sb.append("}");
		ScaleLabel scaleLabel=getScaleLabel();
		if(scaleLabel!=null){
			
			sb.append(",\"scaleLabel\":"+scaleLabel.toJson());	
		}
		sb.append("}");
		return sb.toString();
	}
	public YPosition getYposition() {
		return yposition;
	}
	public void setYposition(YPosition yposition) {
		this.yposition = yposition;
	}
}
