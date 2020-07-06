package com.changhong.sei.report.expression.function.math;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;
import org.apache.commons.lang.math.RandomUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @desc：随机数函数
 * @author：zhaohz
 * @date：2020/7/6 9:31
 */
public class RandomFunction extends MathFunction {
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		int feed=0;
		if(dataList.size()>0){
			BigDecimal data=buildBigDecimal(dataList);
			feed=data.intValue();
		}
		if(feed==0){
			return Math.random();
		}
		return RandomUtils.nextInt(feed);
	}

	@Override
	public String name() {
		return "random";
	}
}
