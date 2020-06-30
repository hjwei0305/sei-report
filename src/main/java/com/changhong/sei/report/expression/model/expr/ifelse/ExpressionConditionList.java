package com.changhong.sei.report.expression.model.expr.ifelse;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.expression.model.condition.Join;
import com.changhong.sei.report.model.Cell;

import java.util.List;

/**
 * @desc：表达式条件列表
 * @author：zhaohz
 * @date：2020/6/30 15:33
 */
public class ExpressionConditionList {
	private List<ExpressionCondition> conditions;
	private List<Join> joins;
	public ExpressionConditionList(List<ExpressionCondition> conditions, List<Join> joins) {
		this.conditions = conditions;
		this.joins = joins;
	}
	public boolean eval(Context context, Cell cell, Cell currentCell){
		if(conditions.size()==1){
			return conditions.get(0).eval(context, cell,currentCell);
		}
		for(int i=0;i<conditions.size();i++){
			ExpressionCondition condition=conditions.get(i);
			boolean result=condition.eval(context, cell,currentCell);
			Join join=null;
			if(i<joins.size()){
				join=joins.get(i);				
			}
			if(join==null){
				return result;
			}else{
				if(join.equals(Join.and) && !result){
					return false;
				}
				if(join.equals(Join.or) && result){
					return true;
				}				
			}
		}
		return true;
	}
	public List<ExpressionCondition> getConditions() {
		return conditions;
	}
}
