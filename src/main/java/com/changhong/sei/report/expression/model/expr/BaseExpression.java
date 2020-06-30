package com.changhong.sei.report.expression.model.expr;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.Condition;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：表达式基类
 * @author：zhaohz
 * @date：2020/6/30 13:48
 */
public abstract class BaseExpression implements Expression {
	private static final long serialVersionUID = 3853234856946931008L;
	protected String expr;
	
	@Override
	public final ExpressionData<?> execute(Cell cell, Cell currentCell, Context context) {
		ExpressionData<?> data=compute(cell,currentCell,context);
		return data;
	}
	
	protected abstract ExpressionData<?> compute(Cell cell, Cell currentCell, Context context);
	
	protected List<Cell> filterCells(Cell cell, Context context, Condition condition, List<Cell> targetCells) {
		if(condition==null){
			return targetCells;
		}
		List<Cell> list=new ArrayList<Cell>();
		for(Cell targetCell:targetCells){
			boolean conditionResult=true;
			List<Object> dataList=targetCell.getBindData();
			if(dataList==null){
				conditionResult=false;				
			}else{
				for(Object obj:dataList){
					boolean result=condition.filter(cell,targetCell, obj, context);
					if(!result){
						conditionResult=false;
						break;
					}
				}
			}
			if(!conditionResult){
				continue;
			}
			list.add(targetCell);
		}
		return list;
	}
	
	public void setExpr(String expr) {
		this.expr = expr;
	}
	public String getExpr() {
		return expr;
	}
}
