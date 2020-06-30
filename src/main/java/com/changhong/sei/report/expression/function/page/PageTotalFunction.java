package com.changhong.sei.report.expression.function.page;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.List;

/**
 * @desc：总页数函数
 * @author：zhaohz
 * @date：2020/6/30 14:05
 */
public class PageTotalFunction extends PageFunction {
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		return context.getTotalPages();
	}
	@Override
	public String name() {
		return "pages";
	}
}
