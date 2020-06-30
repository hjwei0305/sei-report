package com.changhong.sei.report.expression.parse.builds;

import com.changhong.sei.report.dsl.ReportParserParser;
import com.changhong.sei.report.expression.model.expr.BaseExpression;
import com.changhong.sei.report.expression.model.expr.FunctionExpression;
import com.changhong.sei.report.utils.ExpressionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：函数表达式构造器
 * @author：zhaohz
 * @date：2020/6/30 17:18
 */
public class FunctionExpressionBuilder extends BaseExpressionBuilder {
	@Override
	public BaseExpression build(ReportParserParser.UnitContext unitContext) {
		ReportParserParser.FunctionContext ctx=unitContext.function();
		FunctionExpression expr=new FunctionExpression();
		expr.setExpr(ctx.getText());
		expr.setName(ctx.Identifier().getText());
		ReportParserParser.FunctionParameterContext functionParameterContext=ctx.functionParameter();
		if(functionParameterContext!=null){
			List<BaseExpression> exprList=new ArrayList<BaseExpression>();
			List<ReportParserParser.ItemContext> itemContexts=functionParameterContext.item();
			if(itemContexts!=null){
				for(int i=0;i<itemContexts.size();i++){
					ReportParserParser.ItemContext itemContext=itemContexts.get(i);
					BaseExpression baseExpr= ExpressionUtils.getExprVisitor().parseItemContext(itemContext);
					exprList.add(baseExpr);
				}
			}
			expr.setExpressions(exprList);
		}
		return expr;
	}

	@Override
	public boolean support(ReportParserParser.UnitContext unitContext) {
		return unitContext.function()!=null;
	}
}
