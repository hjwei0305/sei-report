package com.changhong.sei.report.expression.function.string;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.List;

/**
 * @desc：替换
 * @author：zhaohz
 * @date：2020/7/6 9:41
 */
public class ReplaceFunction extends StringFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		if(dataList.size()!=3){
			throw new ReportComputeException("Function ["+name()+"] need three parameters.");
		}
		String text=buildString(dataList);
		String targetText=null,replaceText=null;
		ExpressionData<?> exprData=dataList.get(1);
		if(exprData instanceof ObjectExpressionData){
			ObjectExpressionData objData=(ObjectExpressionData)exprData;
			Object obj=objData.getData();
			if(obj==null){
				throw new ReportComputeException("Function ["+name()+"] parameter can not be null.");
			}
			targetText=obj.toString();
		}
		exprData=dataList.get(2);
		replaceText=buildStart(exprData);
		return text.replaceAll(targetText, replaceText);
	}

	private String buildStart(ExpressionData<?> exprData) {
		if(exprData instanceof ObjectExpressionData){
			ObjectExpressionData objData=(ObjectExpressionData)exprData;
			Object obj=objData.getData();
			if(obj==null){
				throw new ReportComputeException("Function ["+name()+"] parameter can not be null.");
			}
			return obj.toString();
		}
		throw new ReportComputeException("Function ["+name()+"] start position data is invalid : "+exprData);
	}

	@Override
	public String name() {
		return "replace";
	}

}
