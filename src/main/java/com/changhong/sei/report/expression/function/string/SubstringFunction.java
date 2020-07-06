package com.changhong.sei.report.expression.function.string;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.utils.Utils;

import java.util.List;

/**
 * @desc：求子串
 * @author：zhaohz
 * @date：2020/7/6 9:42
 */
public class SubstringFunction extends StringFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		String text=buildString(dataList);
		int start=0,end=text.length();
		if(dataList.size()>1){
			ExpressionData<?> exprData=dataList.get(1);
			start=buildPos(exprData);
		}
		if(dataList.size()==3){
			ExpressionData<?> exprData=dataList.get(2);
			end=buildPos(exprData);
		}
		return text.substring(start, end);
	}

	private int buildPos(ExpressionData<?> exprData) {
		if(exprData instanceof ObjectExpressionData){
			ObjectExpressionData objData=(ObjectExpressionData)exprData;
			Object obj=objData.getData();
			if(obj==null){
				throw new ReportComputeException("Function ["+name()+"] second parameter can not be null.");
			}
			return Utils.toBigDecimal(obj).intValue();
		}
		throw new ReportComputeException("Function ["+name()+"] position data is invalid : "+exprData);
	}

	@Override
	public String name() {
		return "substring";
	}
}
