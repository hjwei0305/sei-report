package com.changhong.sei.report.chart.dataset.impl;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.chart.dataset.Dataset;
import com.changhong.sei.report.chart.dataset.impl.category.BarDataset;
import com.changhong.sei.report.chart.dataset.impl.category.LineDataset;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.model.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：混合数据集
 * @author：zhaohz
 * @date：2020/6/30 16:27
 */
public class MixDataset implements Dataset {
	private List<BarDataset> barDatasets=new ArrayList<BarDataset>();
	private List<LineDataset> lineDatasets=new ArrayList<LineDataset>();
	
	@Override
	public String buildDataJson(Context context, Cell cell) {
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("\"datasets\":[");
		int index=0;
		for(BarDataset ds:barDatasets){
			if(index>0){				
				sb.append(",");
			}
			sb.append(ds.toMixJson(context, cell, index));
		}
		for(LineDataset ds:lineDatasets){
			if(index>0){				
				sb.append(",");
			}
			sb.append(ds.toMixJson(context, cell, index));
		}
		sb.append("],");
		String labels=null;
		if(barDatasets.size()>0){
			labels=barDatasets.get(0).getLabels();
		}else if(lineDatasets.size()>0){
			labels=lineDatasets.get(0).getLabels();
		}else{
			throw new ReportComputeException("Mix chart need one dataset at least.");
		}
		sb.append("labels:").append(labels);
		sb.append("}");
		return sb.toString();
	}

	
	@Override
	public String getType() {
		return "bar";
	}

	public List<BarDataset> getBarDatasets() {
		return barDatasets;
	}

	public void setBarDatasets(List<BarDataset> barDatasets) {
		this.barDatasets = barDatasets;
	}

	public List<LineDataset> getLineDatasets() {
		return lineDatasets;
	}

	public void setLineDatasets(List<LineDataset> lineDatasets) {
		this.lineDatasets = lineDatasets;
	}
}
