package com.changhong.sei.report.expression.function.date;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.Calendar;
import java.util.List;

/**
 * @desc：月中日函数
 * @author：zhaohz
 * @date：2020/7/6 8:53
 */
public class DayFunction extends CalendarFunction {
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		Calendar c = buildCalendar(dataList);
		int day=c.get(Calendar.DAY_OF_MONTH);
		return day;
	}
	
	@Override
	public String name() {
		return "day";
	}
}
