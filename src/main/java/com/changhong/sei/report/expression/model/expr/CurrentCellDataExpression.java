package com.changhong.sei.report.expression.model.expr;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.NoneExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.utils.Utils;

import java.util.List;

/**
 * @desc：当前单元格数据表达式
 * @author：zhaohz
 * @date：2020/6/30 13:53
 */
public class CurrentCellDataExpression extends BaseExpression {
	private static final long serialVersionUID = 7517926036810650110L;
	private String property;
	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		List<Object> bindDataList=cell.getBindData();
		if(bindDataList==null || bindDataList.size()==0){
			return new NoneExpressionData();
		}
		Object obj=bindDataList.get(0);
		Object data= Utils.getProperty(obj, property);
		return new ObjectExpressionData(data);
	}
	public void setProperty(String property) {
		this.property = property;
	}
}
