package com.changhong.sei.report.expression.model.expr.set;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.Condition;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectListExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：
 * @author：zhaohz
 * @date：2020/7/1 14:27
 */
public class WholeCellExpression extends CellExpression {
	private static final long serialVersionUID = 4926788994485522808L;
	private Condition condition;
	public WholeCellExpression(String cellName) {
		super(cellName);
	}
	@Override
	public boolean supportPaging(){
		return false;
	}
	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		while(!context.isCellPocessed(cellName)){
			context.getReportBuilder().buildCell(context, null);
		}
		List<Cell> cells=context.getReport().getCellsMap().get(cellName);
		List<Object> list=new ArrayList<Object>();
		for(Cell c:cells){
			Object obj=c.getData();
			if(condition!=null){
				boolean result=condition.filter(cell, currentCell, obj, context);
				if(!result){
					continue;
				}
			}
			list.add(obj);
		}
		if(list.size()==1){
			return new ObjectExpressionData(list.get(0));
		}else{
			return new ObjectListExpressionData(list);
		}
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
}
