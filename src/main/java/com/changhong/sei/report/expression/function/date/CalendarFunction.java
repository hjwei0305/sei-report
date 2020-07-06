package com.changhong.sei.report.expression.function.date;

import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.expression.function.Function;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectListExpressionData;
import com.changhong.sei.report.utils.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @desc：日期函数抽象类
 * @author：zhaohz
 * @date：2020/7/6 8:50
 */
public abstract class CalendarFunction implements Function {
	protected Calendar buildCalendar(List<ExpressionData<?>> dataList) {
		Date date=new Date();
		if(dataList!=null && dataList.size()>0){
			ExpressionData<?> data=dataList.get(0);
			if(data instanceof ObjectListExpressionData){
				ObjectListExpressionData listData=(ObjectListExpressionData)data;
				List<?> list=listData.getData();
				if(list==null || list.size()!=1){
					throw new ReportComputeException("Function [day] first parameter need a data of Date.");
				}
				Object obj=list.get(0);
				if(obj==null){
					throw new ReportComputeException("Function [day] first parameter can not be null.");
				}
				date= Utils.toDate(obj);
			}else if(data instanceof ObjectExpressionData){
				ObjectExpressionData objData=(ObjectExpressionData)data;
				Object obj=objData.getData();
				if(obj==null){
					throw new ReportComputeException("Function [day] first parameter can not be null.");
				}
				date= Utils.toDate(obj);
			}else{
				throw new ReportComputeException("Function [day] first parameter need a data of Date.");
			}
		}
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		return c;
	}
}