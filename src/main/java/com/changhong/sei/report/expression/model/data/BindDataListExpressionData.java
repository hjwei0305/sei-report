package com.changhong.sei.report.expression.model.data;

import com.changhong.sei.report.builds.BindData;

import java.util.List;

/**
 * @desc：绑定数据列表
 * @author：zhaohz
 * @date：2020/6/30 9:51
 */
public class BindDataListExpressionData implements ExpressionData<List<BindData>> {
	private List<BindData> list;
	
	public BindDataListExpressionData(List<BindData> list) {
		this.list = list;
	}

	@Override
	public List<BindData> getData() {
		return list;
	}
}
