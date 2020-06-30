package com.changhong.sei.report.definition.value;

import com.changhong.sei.report.enums.ValueType;
import com.changhong.sei.report.expression.model.Expression;

/**
 * @desc：图形值
 * @author：zhaohz
 * @date：2020/6/30 16:55
 */
public class ImageValue implements Value {
	private String path;
	private String expr;
	private Expression expression;
	private Source source;
	private int width;
	private int height;
	@Override
	public ValueType getType() {
		return ValueType.image;
	}
	@Override
	public String getValue() {
		if(this.source.equals(Source.text)){
			return path;
		}else{
			return expr;
		}
	}
	public void setSource(Source source) {
		this.source = source;
	}
	public Source getSource() {
		return source;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setExpr(String expr) {
		this.expr = expr;
	}
	public String getExpr() {
		return expr;
	}
	public Expression getExpression() {
		return expression;
	}
	public void setExpression(Expression expression) {
		this.expression = expression;
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
}
