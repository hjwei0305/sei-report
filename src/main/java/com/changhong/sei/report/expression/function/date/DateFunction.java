package com.changhong.sei.report.expression.function.date;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.model.Cell;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @desc：日期函数
 * @author：zhaohz
 * @date：2020/7/6 8:52
 */
public class DateFunction extends CalendarFunction {
	private String pattern="yyyy-MM-dd HH:mm:ss";
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		SimpleDateFormat sd=new SimpleDateFormat(pattern);
		Date date=new Date();
		if(dataList.size()==1){
			ExpressionData<?> data=dataList.get(0);
			sd=buildPattern(data);
		}
		if(dataList.size()==2){
			Calendar c = buildCalendar(dataList);
			date=c.getTime();
			ExpressionData<?> data=dataList.get(1);
			sd=buildPattern(data);
		}
		return sd.format(date);
	}

	private SimpleDateFormat buildPattern(ExpressionData<?> data) {
		SimpleDateFormat sd=null;
		if(data instanceof ObjectExpressionData){
			ObjectExpressionData objectData=(ObjectExpressionData)data;
			String newPattern=(String)objectData.getData();
			sd=new SimpleDateFormat(newPattern);
		}else{
			throw new ReportComputeException("Unknow date format pattern:"+data.getData());
		}
		return sd;
	}

	@Override
	public String name() {
		return "date";
	}
}
