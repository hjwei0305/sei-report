package com.changhong.sei.report.builds.aggregate;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.definition.value.GroupItem;
import com.changhong.sei.report.enums.Order;
import com.changhong.sei.report.expression.model.Condition;
import com.changhong.sei.report.expression.model.expr.DatasetExpression;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.utils.DataUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc：自定义分组计算
 * @author：zhaohz
 * @date：2020/6/30 17:09
 */
public class CustomGroupAggregate extends Aggregate {

	@Override
	public List<BindData> aggregate(DatasetExpression expr, Cell cell, Context context) {
		List<?> objList= DataUtils.fetchData(cell, context, expr.getDatasetName());
		List<BindData> list = doAggregate(expr, cell, context, objList);
		return list;
	}
	protected List<BindData> doAggregate(DatasetExpression expr, Cell cell, Context context, List<?> objList) {
		List<BindData> list=new ArrayList<BindData>();
		List<GroupItem> groupItems=expr.getGroupItems();
		if(objList.size()==0){
			list.add(new BindData(""));
			return list;
		}else if(objList.size()==1){
			Object o=objList.get(0);
			boolean conditionResult=doCondition(expr.getCondition(),cell,o,context);
			if(!conditionResult){
				list.add(new BindData(""));
				return list;
			}
			String itemName=groupData(groupItems, cell, context, o);
			if(itemName==null){
				list.add(new BindData(""));
				return list;
			}
			List<Object> rowList=new ArrayList<Object>();
			rowList.add(o);
			list.add(new BindData(itemName,rowList));
			return list;
		}
		Map<Object,List<Object>> map=new HashMap<Object,List<Object>>();
		for(Object o:objList){
			boolean conditionResult=doCondition(expr.getCondition(),cell,o,context);
			if(!conditionResult){
				continue;
			}
			String itemName=groupData(groupItems, cell, context, o);
			if(itemName==null){
				continue;
			}
			List<Object> rowList=null;
			if(map.containsKey(itemName)){
				rowList=map.get(itemName);
			}else{
				rowList=new ArrayList<Object>();
				map.put(itemName, rowList);
				list.add(new BindData(itemName,rowList));
			}
			rowList.add(o);				
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
	private String groupData(List<GroupItem> groupItems, Cell cell, Context context, Object o){
		for(GroupItem item:groupItems){
			Condition condition=item.getCondition();
			boolean doCondition=doCondition(condition, cell, o, context);
			if(doCondition){
				return item.getName();
			}
		}
		return null;
	}
}
