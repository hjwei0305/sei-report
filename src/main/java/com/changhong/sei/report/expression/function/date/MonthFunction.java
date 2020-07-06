package com.changhong.sei.report.expression.function.date;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.Calendar;
import java.util.List;

/**
 * @desc：月函数
 * @author：zhaohz
 * @date：2020/7/6 8:53
 */
public class MonthFunction extends CalendarFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		Calendar c = buildCalendar(dataList);
		int month=c.get(Calendar.MONTH)+1;
		return month+1;
	}

	@Override
	public String name() {
		return "month";
	}
}
