package com.changhong.sei.report.enums;

import com.changhong.sei.annotation.Remark;

import java.io.Serializable;

/**
 * @desc：打印方向
 * @author：zhaohz
 * @date：2020/6/30 10:08
 */
public enum Orientation implements Serializable{
	@Remark("纵向打印")
	portrait,
	@Remark("横向打印")
	landscape;
}
