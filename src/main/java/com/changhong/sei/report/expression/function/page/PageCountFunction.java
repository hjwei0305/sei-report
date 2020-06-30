package com.changhong.sei.report.expression.function.page;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectListExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.List;

/**
 * @desc：页计数函数
 * @author：zhaohz
 * @date：2020/6/30 14:00
 */
public class PageCountFunction extends PageFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		if(dataList==null){
			return 0;
		}
		int size=0;
		for(ExpressionData<?> data:dataList){
			if(data instanceof ObjectListExpressionData){
				ObjectListExpressionData listExpressionData=(ObjectListExpressionData)data;
				size+=listExpressionData.getData().size();
			}else if(data instanceof ObjectExpressionData){
				size++;
			}
		}
		return size;
	}

	@Override
	public String name() {
		return "pcount";
	}

}
