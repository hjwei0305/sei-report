package com.changhong.sei.report.parser.impl;

import com.changhong.sei.report.definition.ConditionCellStyle;
import com.changhong.sei.report.definition.ConditionPaging;
import com.changhong.sei.report.definition.ConditionPropertyItem;
import com.changhong.sei.report.definition.LinkParameter;
import com.changhong.sei.report.enums.Op;
import com.changhong.sei.report.expression.model.Condition;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.expression.model.condition.BaseCondition;
import com.changhong.sei.report.expression.model.condition.BothExpressionCondition;
import com.changhong.sei.report.expression.model.condition.Join;
import com.changhong.sei.report.expression.model.condition.PropertyExpressionCondition;
import com.changhong.sei.report.parser.Parser;
import com.changhong.sei.report.utils.ExpressionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc：条件参数明细转换
 * @author：zhaohz
 * @date：2020/7/6 10:55
 */
public class ConditionParameterItemParser implements Parser<ConditionPropertyItem> {
	private Map<String, Parser<?>> parsers=new HashMap<String, Parser<?>>();
	public ConditionParameterItemParser() {
		parsers.put("cell-style",new CellStyleParser());
		parsers.put("link-parameter",new LinkParameterParser());
		parsers.put("paging",new ConditionPagingParser());
	}
	@Override
	public ConditionPropertyItem parse(Element element) {
		ConditionPropertyItem item=new ConditionPropertyItem();
		String rowHeight=element.attributeValue("row-height");
		if(StringUtils.isNotBlank(rowHeight)){
			item.setRowHeight(Integer.valueOf(rowHeight));
		}
		String colWidth=element.attributeValue("col-width");
		if(StringUtils.isNotBlank(colWidth)){
			item.setColWidth(Integer.valueOf(colWidth));
		}
		item.setName(element.attributeValue("name"));
		item.setNewValue(element.attributeValue("new-value"));
		item.setLinkUrl(element.attributeValue("link-url"));
		item.setLinkTargetWindow(element.attributeValue("link-target-window"));
		List<LinkParameter> parameters=null;
		List<Condition> conditions=new ArrayList<Condition>();
		item.setConditions(conditions);
		BaseCondition topCondition=null;
		BaseCondition prevCondition=null;
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			String name=ele.getName();
			if(name.equals("condition")){
				BaseCondition condition = parseCondition(ele);
				conditions.add(condition);
				if(topCondition==null){
					topCondition=condition;
					prevCondition=condition;
				}else{
					prevCondition.setNextCondition(condition);
					prevCondition.setJoin(condition.getJoin());
					prevCondition=condition;
				}
				continue;
			}
			Parser<?> parser=parsers.get(name);
			if(parser==null){
				continue;
			}
			Object data=parser.parse(ele);
			if(data instanceof ConditionCellStyle){
				item.setCellStyle((ConditionCellStyle)data);
			}else if(data instanceof LinkParameter){
				if(parameters==null){
					parameters=new ArrayList<LinkParameter>();
				}
				parameters.add((LinkParameter)data);
			}else if(data instanceof ConditionPaging){
				item.setPaging((ConditionPaging)data);
			}
		}
		item.setCondition(topCondition);
		item.setLinkParameters(parameters);
		return item;
	}
	
	private BaseCondition parseCondition(Element ele) {
		String type=ele.attributeValue("type");
		if(type==null || type.equals("property")){
			PropertyExpressionCondition condition=new PropertyExpressionCondition();
			String property=ele.attributeValue("property");
			condition.setLeftProperty(property);
			condition.setLeft(property);
			String operation=ele.attributeValue("op");
			condition.setOperation(operation);
			condition.setOp(Op.parse(operation));
			for(Object o:ele.elements()){
				if(o==null || !(o instanceof Element)){
					continue;
				}
				Element e=(Element)o;
				if(!e.getName().equals("value")){
					continue;
				}
				String expr=e.getTextTrim();
				condition.setRightExpression(ExpressionUtils.parseExpression(expr));
				condition.setRight(expr);
				break;
			}
			String join=ele.attributeValue("join");
			if(StringUtils.isNotBlank(join)){
				condition.setJoin(Join.valueOf(join));
			}
			return condition;
		}else{
			BothExpressionCondition exprCondition=new BothExpressionCondition();
			exprCondition.setOperation(ele.attributeValue("op"));
			exprCondition.setOp(Op.parse(ele.attributeValue("op")));
			for(Object o:ele.elements()){
				if(o==null || !(o instanceof Element)){
					continue;
				}
				Element e=(Element)o;
				String name=e.getName();
				if(name.equals("left")){
					String left=e.getText();
					Expression leftExpr= ExpressionUtils.parseExpression(left);
					exprCondition.setLeft(left);
					exprCondition.setLeftExpression(leftExpr);
				}else if(name.equals("right")){
					String right=e.getText();
					Expression rightExpr= ExpressionUtils.parseExpression(right);
					exprCondition.setRight(right);
					exprCondition.setRightExpression(rightExpr);
				}
			}
			String join=ele.attributeValue("join");
			if(StringUtils.isNotBlank(join)){
				exprCondition.setJoin(Join.valueOf(join));
			}
			return exprCondition;
		}
	}
}