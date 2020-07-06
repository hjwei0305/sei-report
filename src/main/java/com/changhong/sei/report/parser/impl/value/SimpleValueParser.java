package com.changhong.sei.report.parser.impl.value;

import com.changhong.sei.report.definition.value.SimpleValue;
import com.changhong.sei.report.definition.value.Value;
import org.dom4j.Element;

/**
 * @desc：普通文本
 * @author：zhaohz
 * @date：2020/7/6 11:23
 */
public class SimpleValueParser extends ValueParser {
	@Override
	public Value parse(Element element) {
		SimpleValue simpleValue=new SimpleValue(element.getText());
		return simpleValue;
	}
}
