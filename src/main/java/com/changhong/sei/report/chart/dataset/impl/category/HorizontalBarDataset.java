package com.changhong.sei.report.chart.dataset.impl.category;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.model.Cell;

/**
 * @desc：水平柱状图数据集
 * @author：zhaohz
 * @date：2020/6/30 16:36
 */
public class HorizontalBarDataset extends BarDataset {
	@Override
	public String buildDataJson(Context context, Cell cell) {
		String datasetJson=buildDatasetJson(context, cell,null);
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		String labels=getLabels();
		sb.append("\"labels\":").append(labels).append(",");
		sb.append("\"datasets\":[").append(datasetJson).append("]");
		sb.append("}");
		return sb.toString();
	}
	@Override
	public String getType() {
		return "horizontalBar";
	} 
}
