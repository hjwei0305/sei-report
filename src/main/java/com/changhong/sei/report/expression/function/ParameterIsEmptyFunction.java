package com.changhong.sei.report.expression.function;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.List;

/**
 * @desc：判断参数是否为空函数
 * @author：zhaohz
 * @date：2020/6/30 14:23
 */
public class ParameterIsEmptyFunction extends ParameterFunction {
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		Object obj = super.execute(dataList, context, currentCell);
		if(obj==null || obj.toString().trim().equals("")){
			return true;
		}
		return false;
	}
	@Override
	public String name() {
		return "emptyparam";
	}
}
