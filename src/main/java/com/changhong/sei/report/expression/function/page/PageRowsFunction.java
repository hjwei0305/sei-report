package com.changhong.sei.report.expression.function.page;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.List;

/**
 * @desc：当前页条数函数
 * @author：zhaohz
 * @date：2020/6/30 14:03
 */
public class PageRowsFunction extends PageFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		int pageIndex=currentCell.getRow().getPageIndex();
		if(pageIndex==0)pageIndex=1;
		return context.getCurrentPageRows(pageIndex).size();
	}

	@Override
	public String name() {
		return "prows";
	}
}
