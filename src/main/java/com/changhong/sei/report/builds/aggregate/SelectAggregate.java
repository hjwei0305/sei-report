package com.changhong.sei.report.builds.aggregate;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.enums.Order;
import com.changhong.sei.report.expression.model.expr.DatasetExpression;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.utils.DataUtils;
import com.changhong.sei.report.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc：选择
 * @author：zhaohz
 * @date：2020/6/30 17:15
 */
public class SelectAggregate extends Aggregate {
	@Override
	public List<BindData> aggregate(DatasetExpression expr, Cell cell, Context context) {
		List<?> objList= DataUtils.fetchData(cell, context, expr.getDatasetName());
		return doAggregate(expr, cell, context, objList);
	}

	protected List<BindData> doAggregate(DatasetExpression expr, Cell cell, Context context, List<?> objList) {
		List<BindData> list=new ArrayList<BindData>();
		Map<String,String> mappingMap=context.getMapping(expr);
		String property=expr.getProperty();
		for(Object o:objList){
			boolean conditionResult=doCondition(expr.getCondition(),cell,o,context);
			if(!conditionResult){
				continue;
			}
			List<Object> bindList=new ArrayList<Object>();
			bindList.add(o);
			Object data= Utils.getProperty(o, property);
			Object mappingData=mappingData(mappingMap,data);
			if(mappingData==null){
				list.add(new BindData(data,bindList));
			}else{
				list.add(new BindData(data,mappingData,bindList));
			}
		}
		if(list.size()==0){
			List<Object> rowList=new ArrayList<Object>();
			rowList.add(new HashMap<String,Object>());
			list.add(new BindData("",rowList));
		}
		if(list.size()>1){
			Order order=expr.getOrder();
			orderBindDataList(list, order);			
		}
		return list;
	}
}
