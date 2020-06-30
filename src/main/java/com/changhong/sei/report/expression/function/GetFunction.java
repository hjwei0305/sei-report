package com.changhong.sei.report.expression.function;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.BindDataListExpressionData;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectListExpressionData;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.utils.Utils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：获取属性值函数
 * @author：zhaohz
 * @date：2020/6/30 14:17
 */
public class GetFunction implements Function {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		int index=1;
		String propertyName=null;
		List<Object> list= new ArrayList<Object>();
		if(dataList.size()==1){
			buildList(dataList.get(0),list);
		}else if(dataList.size()==2){
			buildList(dataList.get(0),list);
			index = buildIndex(dataList.get(1));
		}else if(dataList.size()==3){
			buildList(dataList.get(0),list);
			index = buildIndex(dataList.get(1));
			ExpressionData<?> d=dataList.get(2);
			if(d instanceof ObjectExpressionData){
				ObjectExpressionData exprData=(ObjectExpressionData)d;
				Object obj=exprData.getData();
				if(obj!=null){
					propertyName=obj.toString();
				}
			}
		}
		Object obj=null;
		if(list.size()>0){
			if(index<=list.size()){
				obj = list.get(index-1);
			}else{
				obj = list.get(list.size()-1);
			}
		}
		if(StringUtils.isNotBlank(propertyName)){
			obj= Utils.getProperty(obj, propertyName);
		}
		return obj;
	}

	private int buildIndex(ExpressionData<?> d) {
		int index=1;
		if(d instanceof ObjectExpressionData){
			ObjectExpressionData exprData=(ObjectExpressionData)d;
			Object obj=exprData.getData();
			if(obj!=null){
				index= Utils.toBigDecimal(obj).intValue();
			}
		}
		return index;
	}

	private void buildList(ExpressionData<?> d, List<Object> list) {
		if(d instanceof ObjectExpressionData){
			ObjectExpressionData exprData=(ObjectExpressionData)d;
			list.add(exprData.getData());
		}else if(d instanceof ObjectListExpressionData){
			ObjectListExpressionData listData=(ObjectListExpressionData)d;
			list.addAll(listData.getData());
		}else if(d instanceof BindDataListExpressionData){
			BindDataListExpressionData listData=(BindDataListExpressionData)d;
			for(BindData bindData:listData.getData()){
				list.addAll(bindData.getDataList());
			}
		}
	}

	@Override
	public String name() {
		return "get";
	}
}
