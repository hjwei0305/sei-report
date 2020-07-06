package com.changhong.sei.report.expression.function.math;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.math.BigDecimal;
import java.util.List;

/**
 * @desc：标准差
 * @author：zhaohz
 * @date：2020/7/6 9:38
 */
public class StdevpFunction extends MathFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		List<BigDecimal> list=buildDataList(dataList);
		BigDecimal total=new BigDecimal(0);
		for(BigDecimal bigData:list){
			total=total.add(bigData);
		}
		int size=list.size();
		BigDecimal avg=total.divide(new BigDecimal(size),8,BigDecimal.ROUND_HALF_UP);
		double sum=0;
		for(BigDecimal bigData:list){
			BigDecimal data=bigData.subtract(avg);
			sum+=Math.pow(data.doubleValue(), 2);
		}
		BigDecimal result=new BigDecimal(sum);
		result=result.divide(new BigDecimal(size),8,BigDecimal.ROUND_HALF_UP);
		return Math.sqrt(result.doubleValue());
	}
	
	
	@Override
	public String name() {
		return "stdevp";
	}
}
