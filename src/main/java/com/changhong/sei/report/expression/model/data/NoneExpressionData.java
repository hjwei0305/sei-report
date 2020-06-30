package com.changhong.sei.report.expression.model.data;

/**
 * @desc：空值表达式数据
 * @author：zhaohz
 * @date：2020/6/30 13:51
 */
public class NoneExpressionData implements ExpressionData<Object> {
	@Override
	public Object getData() {
		return null;
	}
}
