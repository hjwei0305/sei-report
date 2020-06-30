package com.changhong.sei.report.definition.value;

import com.changhong.sei.report.enums.ValueType;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.utils.ExpressionUtils;

/**
 * @desc：表达式值
 * @author：zhaohz
 * @date：2020/6/30 16:54
 */
public class ExpressionValue implements Value {
	private String text;
	private Expression expression;
	public ExpressionValue(String text) {
		this.text=text;
		expression= ExpressionUtils.parseExpression(text);
	}

	@Override
	public ValueType getType() {
		return ValueType.expression;
	}
	@Override
	public String getValue() {
		return text;
	}
	public Expression getExpression() {
		return expression;
	}
}
