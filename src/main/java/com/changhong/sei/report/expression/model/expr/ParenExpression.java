package com.changhong.sei.report.expression.model.expr;

import com.changhong.sei.report.enums.Operator;

import java.util.List;

/**
 * @desc：括号表达式
 * @author：zhaohz
 * @date：2020/6/30 14:56
 */
public class ParenExpression extends JoinExpression {
	private static final long serialVersionUID = 142186918381087393L;

	public ParenExpression(List<Operator> operators, List<BaseExpression> expressions) {
		super(operators, expressions);
	}
}
