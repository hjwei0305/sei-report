package com.changhong.sei.report.expression.function.math;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc：众数
 * @author：zhaohz
 * @date：2020/7/6 9:28
 */
public class ModeFunction extends MathFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		int max=0;
		BigDecimal result=null;
		List<BigDecimal> list=buildDataList(dataList);
		Map<Double,Integer> map=new HashMap<Double,Integer>();
		for(BigDecimal bigData:list){
			if(bigData==null)continue;
			double d=bigData.doubleValue();
			if(map.containsKey(d)){
				int count=map.get(d);
				count++;
				map.put(d, count);
				if(count>max){
					max=count;
					result=bigData;
				}
			}else{
				map.put(d, 1);
				if(result==null){
					max=1;
					result=bigData;					
				}
			}
		}
		return result;
	}
	
	@Override
	public String name() {
		return "mode";
	}
}
