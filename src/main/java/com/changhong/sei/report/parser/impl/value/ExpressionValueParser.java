package com.changhong.sei.report.parser.impl.value;

import com.changhong.sei.report.definition.value.ExpressionValue;
import org.dom4j.Element;

/**
 * @desc：表达式值
 * @author：zhaohz
 * @date：2020/7/6 11:21
 */
public class ExpressionValueParser extends ValueParser {
	@Override
	public ExpressionValue parse(Element element) {
		ExpressionValue value=new ExpressionValue(element.getText());
		return value;
	}
}
