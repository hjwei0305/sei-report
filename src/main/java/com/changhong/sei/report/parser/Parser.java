package com.changhong.sei.report.parser;

import org.dom4j.Element;

/**
 * @desc：单元格对象转换接口
 * @author：zhaohz
 * @date：2020/7/6 10:46
 */
public interface Parser<T>{
	T parse(Element element);
}
