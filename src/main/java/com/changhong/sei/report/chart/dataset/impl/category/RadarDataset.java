package com.changhong.sei.report.chart.dataset.impl.category;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.model.Cell;

/**
 * @desc：雷达图数据集
 * @author：zhaohz
 * @date：2020/6/30 16:39
 */
public class RadarDataset extends CategoryDataset {
	private boolean fill=true;
	private double lineTension=0.1;
	
	@Override
	public String buildDataJson(Context context, Cell cell) {
		String props="\"fill\":"+fill+",\"lineTension\":"+lineTension;
		String datasetJson=buildDatasetJson(context, cell,props);
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		String labels=getLabels();
		sb.append("\"labels\":"+labels+",");
		sb.append("\"datasets\":["+datasetJson+"]");
		sb.append("}");
		return sb.toString();
	}
	
	@Override
	public String getType() {
		return "radar";
	}

	public boolean isFill() {
		return fill;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}

	public double getLineTension() {
		return lineTension;
	}

	public void setLineTension(double lineTension) {
		this.lineTension = lineTension;
	}
}
