package com.changhong.sei.report.expression.function.page;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.List;

/**
 * @desc：页码函数
 * @author：zhaohz
 * @date：2020/6/30 14:02
 */
public class PageNumberFunction extends PageFunction {
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		if(currentCell!=null && currentCell.getRow()!=null){
			return currentCell.getRow().getPageIndex();		
		}else{
			return context.getPageIndex();
		}
	}
	@Override
	public String name() {
		return "page";
	}
}
