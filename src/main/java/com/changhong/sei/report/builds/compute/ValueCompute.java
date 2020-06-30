package com.changhong.sei.report.builds.compute;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.enums.ValueType;
import com.changhong.sei.report.model.Cell;

import java.util.List;

/**
 * @desc：值计算接口
 * @author：zhaohz
 * @date：2020/6/28 17:22
 */
public interface ValueCompute {
	List<BindData> compute(Cell cell, Context context);
	ValueType type();
}
