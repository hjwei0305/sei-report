package com.changhong.sei.report.expression.parse.builds;

import com.changhong.sei.report.dsl.ReportParserParser;
import com.changhong.sei.report.expression.model.expr.BaseExpression;

/**
 * @desc：表达式构建器接口
 * @author：zhaohz
 * @date：2020/6/30 13:46
 */
public interface ExpressionBuilder {
	BaseExpression build(ReportParserParser.UnitContext unitContext);
	boolean support(ReportParserParser.UnitContext unitContext);
}
