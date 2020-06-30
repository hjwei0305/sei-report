package com.changhong.sei.report.expression.model.expr;

import com.changhong.sei.report.build.BindData;
import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.expression.model.data.BindDataListExpressionData;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectListExpressionData;
import com.changhong.sei.report.model.Cell;

import java.util.List;

/**
 * @desc：变量配置表达式
 * @author：zhaohz
 * @date：2020/6/30 14:59
 */
public class VariableAssignExpression extends BaseExpression {
	private static final long serialVersionUID = 435511939569866187L;
	private String variable;
	private Expression expression;
	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		ExpressionData<?> data=expression.execute(cell, currentCell, context);
		Object obj=null;
		if(data instanceof ObjectExpressionData){
			ObjectExpressionData d=(ObjectExpressionData)data;
			obj=d.getData();
		}else if(data instanceof ObjectListExpressionData){
			ObjectListExpressionData d=(ObjectListExpressionData)data;
			obj=d.getData();
		}else if(data instanceof BindDataListExpressionData){
			BindDataListExpressionData dataList=(BindDataListExpressionData)data;
			List<BindData> bindList=dataList.getData();
			if(bindList.size()==1){
				BindData bindData=bindList.get(0);
				obj=bindData.getValue();
			}else{
				StringBuilder sb=new StringBuilder();
				for(BindData bd:bindList){
					if(sb.length()>0){
						sb.append(",");
					}
					sb.append(bd.getValue());
				}
				obj=sb.toString();
			}
		}
		if(obj!=null){
			context.putVariable(variable, obj);
		}
		return null;
	}
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	public Expression getExpression() {
		return expression;
	}
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
}
