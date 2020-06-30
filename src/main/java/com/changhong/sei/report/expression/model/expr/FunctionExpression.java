package com.changhong.sei.report.expression.model.expr;

import com.changhong.sei.report.expression.function.page.PageFunction;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.expression.function.Function;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectListExpressionData;
import com.changhong.sei.report.expression.model.expr.set.CellExpression;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.utils.ExpressionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @desc：函数表达式
 * @author：zhaohz
 * @date：2020/6/30 13:56
 */
public class FunctionExpression extends BaseExpression {
	private static final long serialVersionUID = -6981657541024043558L;
	private String name;
	private List<BaseExpression> expressions;
	
	@Override
	public ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		Map<String, Function> functions= ExpressionUtils.getFunctions();
		Function targetFunction=functions.get(name);
		if(targetFunction==null){
			throw new ReportComputeException("Function ["+name+"] not exist.");
		}
		List<ExpressionData<?>> dataList=new ArrayList<ExpressionData<?>>();
		if(expressions!=null){
			for(BaseExpression expr:expressions){
				if(targetFunction instanceof PageFunction){
					ExpressionData<?> data=buildPageExpressionData(expr,cell,currentCell, context);
					dataList.add(data);
				}else{
					ExpressionData<?> data=expr.execute(cell,currentCell, context);
					dataList.add(data);					
				}
			}
		}
		Object obj=targetFunction.execute(dataList, context,currentCell);
		if(obj instanceof List){
			return new ObjectListExpressionData((List<?>)obj);
		}
		return new ObjectExpressionData(obj);
	}
	
	private ExpressionData<?> buildPageExpressionData(Expression expr, Cell cell, Cell currentCell, Context context){
		if(expr instanceof CellExpression){
			CellExpression cellExpr=(CellExpression)expr;
			if(cellExpr.supportPaging()){
				return cellExpr.computePageCells(cell, currentCell, context);
			}else{
				return cellExpr.execute(cell, currentCell, context);
			}
		}else{
			return expr.execute(cell, currentCell, context);
		}
	}
	

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setExpressions(List<BaseExpression> expressions) {
		this.expressions = expressions;
	}
	public List<BaseExpression> getExpressions() {
		return expressions;
	}
}
