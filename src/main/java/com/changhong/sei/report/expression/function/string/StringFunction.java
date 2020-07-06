package com.changhong.sei.report.expression.function.string;

import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.expression.function.Function;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectListExpressionData;

import java.util.List;

/**
 * @desc：简单值函数抽象类
 * @author：zhaohz
 * @date：2020/7/6 9:39
 */
public abstract class StringFunction implements Function {
	protected String buildString(List<ExpressionData<?>> dataList) {
		if(dataList==null || dataList.size()==0){
			throw new ReportComputeException("Function ["+name()+"] need a data of string parameter.");
		}
		String result=null;
		ExpressionData<?> data=dataList.get(0);
		if(data instanceof ObjectListExpressionData){
			ObjectListExpressionData listData=(ObjectListExpressionData)data;
			List<?> list=listData.getData();
			if(list==null || list.size()!=1){
				throw new ReportComputeException("Function ["+name()+"] need a data of number parameter.");
			}
			Object obj=list.get(0);
			if(obj==null){
				throw new ReportComputeException("Function ["+name()+"] parameter can not be null.");
			}
			result=obj.toString();
		}else if(data instanceof ObjectExpressionData){
			ObjectExpressionData objData=(ObjectExpressionData)data;
			Object obj=objData.getData();
			if(obj==null){
				throw new ReportComputeException("Function ["+name()+"] parameter can not be null.");
			}
			result=obj.toString();
		}else{
			throw new ReportComputeException("Function ["+name()+"] need a data of number parameter.");
		}
		return result;
	}
}