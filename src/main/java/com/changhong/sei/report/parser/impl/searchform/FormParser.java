package com.changhong.sei.report.parser.impl.searchform;

import com.changhong.sei.report.parser.Parser;

/**
 * @desc：查询框转换接口
 * @author：zhaohz
 * @date：2020/7/6 11:04
 */
public interface FormParser<T> extends Parser<T> {
	boolean support(String name);
}
