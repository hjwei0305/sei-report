package com.changhong.sei.report.chart.dataset.impl.category;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.model.Cell;

/**
 * @desc：柱状图数据集
 * @author：zhaohz
 * @date：2020/6/30 16:33
 */
public class BarDataset extends CategoryDataset {
	@Override
	public String buildDataJson(Context context, Cell cell) {
		String datasetJson=buildDatasetJson(context, cell,null);
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		String labels=getLabels();
		sb.append("\"labels\":"+labels+",");
		sb.append("\"datasets\":["+datasetJson+"]");
		sb.append("}");
		return sb.toString();
	}
	
	public String toMixJson(Context context, Cell cell, int index){
		String props="\"type\":\"bar\"";
		String datasetJson=buildDatasetJson(context, cell,props);
		return datasetJson;
	}
	@Override
	public String getType() {
		return "bar";
	}
}
