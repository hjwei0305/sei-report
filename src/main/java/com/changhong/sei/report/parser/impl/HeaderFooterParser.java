package com.changhong.sei.report.parser.impl;

import com.changhong.sei.report.definition.HeaderFooterDefinition;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.parser.Parser;
import com.changhong.sei.report.utils.ExpressionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

/**
 * @desc：表头表脚转换
 * @author：zhaohz
 * @date：2020/7/6 11:00
 */
public class HeaderFooterParser implements Parser<HeaderFooterDefinition> {
	@Override
	public HeaderFooterDefinition parse(Element element) {
		HeaderFooterDefinition hf=new HeaderFooterDefinition();
		String margin=element.attributeValue("margin");
		if(StringUtils.isNotBlank(margin)){
			hf.setMargin(Integer.valueOf(margin));
		}
		String bold=element.attributeValue("bold");
		if(StringUtils.isNotBlank(bold)){
			hf.setBold(Boolean.valueOf(bold));
		}
		String italic=element.attributeValue("italic");
		if(StringUtils.isNotBlank(italic)){
			hf.setItalic(Boolean.valueOf(italic));
		}
		String underline=element.attributeValue("underline");
		if(StringUtils.isNotBlank(underline)){
			hf.setUnderline(Boolean.valueOf(underline));
		}
		String fontFamily=element.attributeValue("font-family");
		if(StringUtils.isNotBlank(fontFamily)){
			hf.setFontFamily(fontFamily);			
		}
		String forecolor=element.attributeValue("forecolor");
		if(StringUtils.isNotBlank(forecolor)){
			hf.setForecolor(forecolor);			
		}
		String fontSize=element.attributeValue("font-size");
		if(StringUtils.isNotBlank(fontSize)){
			hf.setFontSize(Integer.valueOf(fontSize));			
		}
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			String name=ele.getName();
			if(name.equals("left")){
				hf.setLeft(ele.getText());
				if(StringUtils.isNotBlank(hf.getLeft())){
					Expression expr= ExpressionUtils.parseExpression(hf.getLeft());
					hf.setLeftExpression(expr);
				}
			}else if(name.equals("center")){
				hf.setCenter(ele.getText());
				if(StringUtils.isNotBlank(hf.getCenter())){
					Expression expr= ExpressionUtils.parseExpression(hf.getCenter());
					hf.setCenterExpression(expr);
				}
			}else if(name.equals("right")){
				hf.setRight(ele.getText());
				if(StringUtils.isNotBlank(hf.getRight())){
					Expression expr= ExpressionUtils.parseExpression(hf.getRight());
					hf.setRightExpression(expr);
				}
			}
		}
		return hf;
	}
}