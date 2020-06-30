package com.changhong.sei.report.builds.cell;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.model.Cell;

import java.util.List;

/**
 * @desc：单元格构造器接口
 * @author：zhaohz
 * @date：2020/6/30 10:27
 */
public interface CellBuilder {
	Cell buildCell(List<BindData> dataList, Cell cell, Context context);
}
