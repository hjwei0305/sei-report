package com.changhong.sei.report.expression.function;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectListExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：列表函数
 * @author：zhaohz
 * @date：2020/6/30 14:19
 */
public class ListFunction implements Function {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		List<Object> list= new ArrayList<Object>();
		for(ExpressionData<?> d:dataList){
			if(d instanceof ObjectExpressionData){
				ObjectExpressionData exprData=(ObjectExpressionData)d;
				list.add(exprData.getData());
			}else if(d instanceof ObjectListExpressionData){
				ObjectListExpressionData listData=(ObjectListExpressionData)d;
				list.addAll(listData.getData());
			}
		}
		return list;
	}

	@Override
	public String name() {
		return "list";
	}
}
