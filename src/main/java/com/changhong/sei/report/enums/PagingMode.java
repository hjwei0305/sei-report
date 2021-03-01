package com.changhong.sei.report.enums;

import com.changhong.sei.annotation.Remark;

import java.io.Serializable;

/**
 * @desc：前端分页模式
 * @author：zhaohz
 * @date：2020/6/30 10:06
 */
public enum PagingMode implements Serializable{
	@Remark("自动")
	fitpage,
	@Remark("固定行数")
	fixrows;
}
