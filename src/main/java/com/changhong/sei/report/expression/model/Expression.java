package com.changhong.sei.report.expression.model;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;

import java.io.Serializable;

/**
 * @desc：表达式接口
 * @author：zhaohz
 * @date：2020/6/30 9:24
 */
public interface Expression extends Serializable{
	ExpressionData<?> execute(Cell cell, Cell currentCell, Context context);
}