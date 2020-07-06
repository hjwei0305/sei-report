package com.changhong.sei.report.expression.function.math;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.utils.Utils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @desc：四舍五入
 * @author：zhaohz
 * @date：2020/7/6 9:32
 */
public class RoundFunction extends MathFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		BigDecimal data=buildBigDecimal(dataList);
		int pos=0;
		if(dataList.size()==2){
			ExpressionData<?> exprData=dataList.get(1);
			if(exprData instanceof ObjectExpressionData){
				ObjectExpressionData objData=(ObjectExpressionData)exprData;
				Object obj=objData.getData();
				if(obj==null){
					throw new ReportComputeException("Round Function second parameter can not be null.");
				}
				pos= Utils.toBigDecimal(obj).intValue();
			}
		}
		return data.setScale(pos, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public String name() {
		return "round";
	}
}
