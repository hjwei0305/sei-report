package com.changhong.sei.report.builds.aggregate;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.expr.DatasetExpression;
import com.changhong.sei.report.model.Cell;

import java.util.List;

/**
 * @desc：重分组
 * @author：zhaohz
 * @date：2020/6/30 17:14
 */
public class RegroupAggregate extends GroupAggregate {
	@Override
	public List<BindData> aggregate(DatasetExpression expr, Cell cell, Context context) {
		List<?> objList=context.getDatasetData(expr.getDatasetName());
		return doAggregate(expr, cell, context,objList);
	}
}
