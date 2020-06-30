package com.changhong.sei.report.expression.model.data;

/**
 * @desc：对象表达式数据
 * @author：zhaohz
 * @date：2020/6/30 9:50
 */
public class ObjectExpressionData implements ExpressionData<Object> {
	private Object data;
	public ObjectExpressionData(Object data) {
		this.data=data;
	}
	@Override
	public Object getData() {
		return data;
	}
}
