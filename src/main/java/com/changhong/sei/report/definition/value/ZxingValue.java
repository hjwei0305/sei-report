package com.changhong.sei.report.definition.value;

import com.changhong.sei.report.enums.ValueType;
import com.changhong.sei.report.expression.model.Expression;

/**
 * @desc：条码值
 * @author：zhaohz
 * @date：2020/6/30 16:57
 */
public class ZxingValue implements Value {
	private int width;
	private int height;
	private Source source;
	private String text;
	private String expr;
	private String format;
	private Expression expression;
	private ZxingCategory category;
	private boolean codeDisplay;
	@Override
	public String getValue() {
		// 2019年1月23日 修复表达式时无法获取value数据
		return source == Source.expression ? expr : text;
	}

	@Override
	public ValueType getType() {
		return ValueType.zxing;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public ZxingCategory getCategory() {
		return category;
	}

	public void setCategory(ZxingCategory category) {
		this.category = category;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isCodeDisplay() {
		return codeDisplay;
	}

	public void setCodeDisplay(boolean codeDisplay) {
		this.codeDisplay = codeDisplay;
	}
}
