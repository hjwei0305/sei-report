package com.changhong.sei.report.enums;

import com.changhong.sei.annotation.Remark;

/**
 * @desc：对齐方式
 * @author：zhaohz
 * @date：2020/6/30 9:41
 */
public enum Alignment {
	@Remark("左对齐")
	left,
	@Remark("右对齐")
	right,
	@Remark("左右居中")
	center,
	@Remark("上对齐")
	top,
	@Remark("上下居中")
	middle,
	@Remark("下对齐")
	bottom;
}
