package com.changhong.sei.report.chart.dataset;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.model.Cell;

/**
 * @desc：数据集接口
 * @author：zhaohz
 * @date：2020/6/30 15:57
 */
public interface Dataset {
	String buildDataJson(Context context, Cell cell);
	String getType();
}
