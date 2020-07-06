package com.changhong.sei.report.expression.function.date;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.Calendar;
import java.util.List;

/**
 * @desc：年函数
 * @author：zhaohz
 * @date：2020/7/6 8:58
 */
public class YearFunction extends CalendarFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		Calendar c = buildCalendar(dataList);
		int year=c.get(Calendar.YEAR);
		return year;
	}

	@Override
	public String name() {
		return "year";
	}
}
