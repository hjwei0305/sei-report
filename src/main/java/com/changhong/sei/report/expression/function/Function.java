package com.changhong.sei.report.expression.function;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.List;

/**
 * @desc：函数接口
 * @author：zhaohz
 * @date：2020/6/30 13:57
 */
public interface Function {
	Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell);
	String name();
}
