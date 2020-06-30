package com.changhong.sei.report.builds.compute;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.enums.ValueType;
import com.changhong.sei.report.expression.model.expr.DatasetExpression;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.utils.DatasetUtils;

import java.util.List;

/**
 * @desc：数据集值计算
 * @author：zhaohz
 * @date：2020/6/28 17:25
 */
public class DatasetValueCompute implements ValueCompute {
	@Override
	public List<BindData> compute(Cell cell, Context context) {
		DatasetExpression expr=(DatasetExpression)cell.getValue();
		return DatasetUtils.computeDatasetExpression(expr, cell, context);
	}
	@Override
	public ValueType type() {
		return ValueType.dataset;
	}
}
