package com.changhong.sei.report.expression.model.expr.set;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectListExpressionData;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：
 * @author：zhaohz
 * @date：2020/7/1 14:26
 */
public class FromToExpression extends BaseExpression {
	private static final long serialVersionUID = -3250140935488901894L;
	private BaseExpression fromExpression;
	private BaseExpression toExpression;
	
	public FromToExpression(BaseExpression fromExpression, BaseExpression toExpression) {
		this.fromExpression = fromExpression;
		this.toExpression = toExpression;
	}

	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		Object fromData=fromExpression.execute(cell,currentCell, context);
		Object toData=toExpression.execute(cell,currentCell, context);
		int from=convertFloatData(fromData),to=convertFloatData(toData);
		List<Integer> list=new ArrayList<Integer>();
		for(int i=from;i<=to;i++){
			list.add(i);
		}
		return new ObjectListExpressionData(list);
	}
	
	private int convertFloatData(Object data){
		if(data instanceof ObjectExpressionData){
			Object obj=((ObjectExpressionData)data).getData();
			return Utils.toBigDecimal(obj).intValue();
		}else{
			throw new ReportComputeException("Can not convert ["+data+"] to integer.");
		}
	}
}
