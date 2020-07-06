package com.changhong.sei.report.parser.impl;

import com.changhong.sei.report.definition.LinkParameter;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.parser.Parser;
import com.changhong.sei.report.utils.ExpressionUtils;
import org.dom4j.Element;

/**
 * @desc：链接参数转换
 * @author：zhaohz
 * @date：2020/7/6 11:01
 */
public class LinkParameterParser implements Parser<LinkParameter> {
	@Override
	public LinkParameter parse(Element element) {
		LinkParameter param=new LinkParameter();
		param.setName(element.attributeValue("name"));
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			if(ele.getName().equals("value")){
				param.setValue(ele.getText());
				Expression expr= ExpressionUtils.parseExpression(ele.getText());
				param.setValueExpression(expr);;
				break;
			}
		}
		return param;
	}
}
