package com.changhong.sei.report.enums;

import com.changhong.sei.annotation.Remark;

/**
 * @desc：单元格值类型
 * @author：zhaohz
 * @date：2020/6/30 9:34
 */
public enum ValueType {
	@Remark("文本")
	simple,
	@Remark("表达式")
	expression,
	@Remark("数据集")
	dataset,
	@Remark("图片")
	image,
	@Remark("图表")
	chart,
	@Remark("斜表头")
	slash,
	@Remark("条码")
	zxing;
}
