package com.changhong.sei.report.builds.compute;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.enums.ValueType;
import com.changhong.sei.report.model.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：简单值计算
 * @author：zhaohz
 * @date：2020/6/28 17:30
 */
public class SimpleValueCompute implements ValueCompute {

	@Override
	public List<BindData> compute(Cell cell, Context context) {
		List<BindData> list=new ArrayList<BindData>();
		list.add(new BindData(cell.getValue().getValue(),null,null));
		return list;
	}

	@Override
	public ValueType type() {
		return ValueType.simple;
	}
}
