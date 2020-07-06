package com.changhong.sei.report.parser.impl.value;

import com.changhong.sei.report.definition.value.Source;
import com.changhong.sei.report.definition.value.Value;
import com.changhong.sei.report.definition.value.ZxingCategory;
import com.changhong.sei.report.definition.value.ZxingValue;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.utils.ExpressionUtils;
import org.dom4j.Element;

/**
 * @desc：条码
 * @author：zhaohz
 * @date：2020/7/6 11:24
 */
public class ZxingValueParser extends ValueParser {

	@Override
	public Value parse(Element element) {
		ZxingValue value=new ZxingValue();
		Source source= Source.valueOf(element.attributeValue("source"));
		value.setSource(source);
		value.setWidth(Integer.valueOf(element.attributeValue("width")));
		value.setHeight(Integer.valueOf(element.attributeValue("height")));
		value.setFormat(element.attributeValue("format"));
		value.setCategory(ZxingCategory.valueOf(element.attributeValue("category")));
		value.setCodeDisplay(Boolean.valueOf(element.attributeValue("code-display")));
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			if(ele.getName().equals("text")){
				if(source.equals(Source.text)){
					value.setText(ele.getText());					
				}else{
					value.setExpr(ele.getText());					
				}
				break;
			}
		}
		if(source.equals(Source.expression)){
			String expr=value.getExpr();
			Expression expression= ExpressionUtils.parseExpression(expr);
			value.setExpression(expression);
		}
		return value;
	}
}
