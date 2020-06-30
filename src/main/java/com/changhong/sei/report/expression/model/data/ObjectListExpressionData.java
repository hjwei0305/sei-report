package com.changhong.sei.report.expression.model.data;

import java.util.List;

/**
 * @desc：对象列表表达式数据
 * @author：zhaohz
 * @date：2020/6/30 9:49
 */
public class ObjectListExpressionData implements ExpressionData<List<?>> {
	private List<?> list;
	public ObjectListExpressionData(List<?> list) {
		this.list=list;
	}
	@Override
	public List<?> getData() {
		return list;
	}
}
