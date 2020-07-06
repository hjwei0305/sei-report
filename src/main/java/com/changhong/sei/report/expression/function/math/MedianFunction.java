package com.changhong.sei.report.expression.function.math;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * @desc：中位数
 * @author：zhaohz
 * @date：2020/7/6 9:26
 */
public class MedianFunction extends MathFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		List<BigDecimal> list=buildDataList(dataList);
		int size=list.size();
		if(size==1){
			return list.get(0);
		}else if(size==2){
			BigDecimal data=list.get(0).add(list.get(1));
			return data.divide(new BigDecimal(2),8,BigDecimal.ROUND_HALF_UP);
		}
		Collections.sort(list);
		int mode=size % 2;
		if(mode==0){
			int half = size / 2;
			int start=half-1;
			BigDecimal data=list.get(start).add(list.get(half));
			return data.divide(new BigDecimal(2),8,BigDecimal.ROUND_HALF_UP);
		}else{
			int half=size / 2;
			return list.get(half);
		}
	}
	
	@Override
	public String name() {
		return "median";
	}
}
