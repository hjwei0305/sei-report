package com.changhong.sei.report.builds.cell;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.definition.ConditionPropertyItem;
import com.changhong.sei.report.model.Cell;

import java.util.List;

/**
 * @desc：不展开单元格构造器
 * @author：zhaohz
 * @date：2020/6/30 10:29
 */
public class NoneExpandBuilder implements CellBuilder {

	@Override
	public Cell buildCell(List<BindData> dataList, Cell cell, Context context) {
		if(dataList.size()==1){
			BindData bindData=dataList.get(0);
			cell.setData(bindData.getValue());
			cell.setFormatData(bindData.getLabel());
			cell.setBindData(bindData.getDataList());
		}else{
			Object obj=null;
			List<Object> bindData=null;
			for(BindData data:dataList){
				if(obj==null){
					if(data.getLabel()==null){
						obj=data.getValue();					
					}else{
						obj=data.getLabel();										
					}
				}else{
					if(data.getLabel()==null){
						obj=obj+","+data.getValue();					
					}else{
						obj=obj+","+data.getLabel();					
					}
				}
				bindData=data.getDataList();
			}
			cell.setData(obj);
			cell.setBindData(bindData);
		}
		List<ConditionPropertyItem> conditionPropertyItems=cell.getConditionPropertyItems();
		if(conditionPropertyItems!=null && conditionPropertyItems.size()>0){
			context.getReport().getLazyComputeCells().add(cell);
		}else{
			cell.doFormat();
			cell.doDataWrapCompute(context);
		}
		return cell;
	}
}
