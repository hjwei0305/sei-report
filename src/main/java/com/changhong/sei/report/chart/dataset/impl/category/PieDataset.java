package com.changhong.sei.report.chart.dataset.impl.category;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.model.Cell;

import java.util.List;
import java.util.Map;

/**
 * @desc：饼状图数据集
 * @author：zhaohz
 * @date：2020/6/30 16:38
 */
public class PieDataset extends CategoryDataset {
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
	@Override
	protected String buildDatasets(Map<Object,Map<Object,List<Object>>> map,String props){
		StringBuilder sb=new StringBuilder();
		int i=0; 
		for(Object series:map.keySet()){
			if(i>0){
				sb.append(",");
			}
			sb.append("{");
			sb.append("\"label\":\""+series+"\",");
			Map<Object,List<Object>> categoryMap=map.get(series);
			sb.append("\"backgroundColor\":"+buildBackgroundColor(i, categoryMap.size())+",");
			sb.append("\"data\":"+buildData(categoryMap));
			if(props!=null){
				sb.append(","+props);
			}
			sb.append("}");
			i++;
		}
		return sb.toString();
	}
	
	private String buildBackgroundColor(int start,int size){
		StringBuilder sb=new StringBuilder();
		sb.append("[");
		for(int i=start;i<size;i++){
			String color=getRgbColor(i);
			if(sb.length()>1){
				sb.append(",");
			}
			sb.append("\"rgb("+color+")\"");
		}
		sb.append("]");
		return sb.toString();
	}
	
	@Override
	public String getType() {
		return "pie";
	}
}
