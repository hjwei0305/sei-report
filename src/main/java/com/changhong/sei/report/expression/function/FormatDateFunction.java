package com.changhong.sei.report.expression.function;

import com.changhong.sei.report.build.BindData;
import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.expression.model.data.BindDataListExpressionData;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectListExpressionData;
import com.changhong.sei.report.model.Cell;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @desc：日期格式函数
 * @author：zhaohz
 * @date：2020/6/30 14:14
 */
public class FormatDateFunction implements Function {
	private final String defaultPattern="yyyy-MM-dd HH:mm:ss";
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		if(dataList==null){
			return "";
		}
		Object obj=null;
		String pattern=defaultPattern;
		for(ExpressionData<?> data:dataList){
			if(data instanceof ObjectListExpressionData){
				ObjectListExpressionData listExpressionData=(ObjectListExpressionData)data;
				List<?> list=listExpressionData.getData();
				if(list.size()>0){
					obj=list.get(0);
				}
				if(list.size()>1){
					pattern=list.get(1).toString();
				}
			}else if(data instanceof ObjectExpressionData){
				obj=((ObjectExpressionData)data).getData();
			}else if(data instanceof BindDataListExpressionData){
				BindDataListExpressionData bindDataList=(BindDataListExpressionData)data;
				List<BindData> list=bindDataList.getData();
				if(list.size()>0){
					obj=list.get(0).getValue();
				}
				if(list.size()>1){
					pattern=list.get(1).getValue().toString();
				}
			}
		}
		if(obj==null){
			throw new ReportComputeException("Function [formatdate] need a Date type parameter at least");
		}else{
			if(obj instanceof Date){
				SimpleDateFormat sd=new SimpleDateFormat(pattern);
				return sd.format((Date)obj);
			}else{
				throw new ReportComputeException("Function [formatdate] first parameter is Date type");
			}
		}
	}

	@Override
	public String name() {
		return "formatdate";
	}
}
