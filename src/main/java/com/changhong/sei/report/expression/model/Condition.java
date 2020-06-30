package com.changhong.sei.report.expression.model;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.model.Cell;

/**
 * @desc：条件接口
 * @author：zhaohz
 * @date：2020/6/30 9:37
 */
public interface Condition {
	/**
	 * @param cell 当前Condition所在的单元格
	 * @param currentCell 当前Condition正在处理的单元格
	 * @param obj 要判断的对象
	 * @param context 上下文对象
	 * @return 返回是否符合条件
	 */
	boolean filter(Cell cell, Cell currentCell, Object obj, Context context);
}
