package com.changhong.sei.report.expression.function;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.expression.model.data.BindDataListExpressionData;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectListExpressionData;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.utils.Utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @desc：数字格式函数
 * @author：zhaohz
 * @date：2020/6/30 14:15
 */
public class FormatNumberFunction implements Function {
	private final String defaultPattern="#";
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		if(dataList==null){
			return "";
		}
		Object obj=null;
		String pattern=defaultPattern;
		if(dataList.size()==1){
			obj = buildExpressionData(dataList.get(0));
		}else if(dataList.size()>1){
			obj = buildExpressionData(dataList.get(0));
			Object patternData=buildExpressionData(dataList.get(1));
			if(patternData!=null){
				pattern=patternData.toString();				
			}
		}
		
		if(obj==null){
			throw new ReportComputeException("Function [formatnumber] need a number parameter at least");
		}else{
			BigDecimal bigData= Utils.toBigDecimal(obj);
			DecimalFormat df=new DecimalFormat(pattern);
			return df.format(bigData.doubleValue());
		}
	}

	private Object buildExpressionData(ExpressionData<?> data) {
		if(data instanceof ObjectListExpressionData){
			ObjectListExpressionData listExpressionData=(ObjectListExpressionData)data;
			List<?> list=listExpressionData.getData();
			if(list.size()>0){
				return list.get(0);
			}
		}else if(data instanceof ObjectExpressionData){
			return ((ObjectExpressionData)data).getData();
		}else if(data instanceof BindDataListExpressionData){
			BindDataListExpressionData bindDataList=(BindDataListExpressionData)data;
			List<BindData> list=bindDataList.getData();
			if(list.size()>0){
				return list.get(0).getValue();
			}
		}
		return null;
	}

	@Override
	public String name() {
		return "formatnumber";
	}
}
