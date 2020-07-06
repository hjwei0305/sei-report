package com.changhong.sei.report.parser.impl.value;

import com.changhong.sei.report.definition.MappingItem;
import com.changhong.sei.report.definition.value.DatasetValue;
import com.changhong.sei.report.definition.value.GroupItem;
import com.changhong.sei.report.definition.value.Value;
import com.changhong.sei.report.enums.AggregateType;
import com.changhong.sei.report.enums.MappingType;
import com.changhong.sei.report.enums.Op;
import com.changhong.sei.report.enums.Order;
import com.changhong.sei.report.expression.model.Condition;
import com.changhong.sei.report.expression.model.condition.Join;
import com.changhong.sei.report.expression.model.condition.PropertyExpressionCondition;
import com.changhong.sei.report.utils.ExpressionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：数据集
 * @author：zhaohz
 * @date：2020/7/6 11:20
 */
public class DatasetValueParser extends ValueParser {
	@Override
	public Value parse(Element element) {
		DatasetValue value=new DatasetValue();
		value.setAggregate(AggregateType.valueOf(element.attributeValue("aggregate")));
		value.setDatasetName(element.attributeValue("dataset-name"));
		value.setProperty(element.attributeValue("property"));
		String order=element.attributeValue("order");
		if(StringUtils.isNotBlank(order)){
			value.setOrder(Order.valueOf(order));
		}
		String mappingType=element.attributeValue("mapping-type");
		if(StringUtils.isNotBlank(mappingType)){
			value.setMappingType(MappingType.valueOf(mappingType));
		}
		value.setMappingDataset(element.attributeValue("mapping-dataset"));
		value.setMappingKeyProperty(element.attributeValue("mapping-key-property"));
		value.setMappingValueProperty(element.attributeValue("mapping-value-property"));
		List<GroupItem> groupItems=null;
		List<MappingItem> mappingItems=null;
		List<Condition> conditions=new ArrayList<Condition>();
		PropertyExpressionCondition topCondition=null;
		PropertyExpressionCondition prevCondition=null;
		value.setConditions(conditions);
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			if(ele.getName().equals("condition")){
				PropertyExpressionCondition condition = parseCondition(ele);
				conditions.add(condition);
				if(topCondition==null){
					topCondition=condition;
					prevCondition=topCondition;
				}else{
					prevCondition.setNextCondition(condition);
					prevCondition.setJoin(condition.getJoin());
					prevCondition=condition;
				}				
			}else if(ele.getName().equals("group-item")){
				if(groupItems==null){
					groupItems=new ArrayList<GroupItem>();
					value.setGroupItems(groupItems);
				}
				GroupItem item=new GroupItem();
				item.setName(ele.attributeValue("name"));
				groupItems.add(item);
				PropertyExpressionCondition groupItemTopCondition=null;
				List<Condition> itemConditions=new ArrayList<Condition>();
				for(Object o:ele.elements()){
					if(o==null || !(o instanceof Element)){
						continue;
					}
					PropertyExpressionCondition itemCondition=parseCondition((Element)o);
					itemConditions.add(itemCondition);
					if(groupItemTopCondition==null){
						groupItemTopCondition=itemCondition;
					}else{
						groupItemTopCondition.setNextCondition(itemCondition);
					}
				}
				item.setCondition(groupItemTopCondition);
				item.setConditions(itemConditions);
			}else if(ele.getName().equals("mapping-item")){
				MappingItem item=new MappingItem();
				item.setLabel(ele.attributeValue("label"));
				item.setValue(ele.attributeValue("value"));
				if(mappingItems==null){
					mappingItems=new ArrayList<MappingItem>();
				}
				mappingItems.add(item);
			}
		}
		if(topCondition!=null){
			value.setCondition(topCondition);
		}
		if(mappingItems!=null){
			value.setMappingItems(mappingItems);
		}
		return value;
	}

	private PropertyExpressionCondition parseCondition(Element ele) {
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
	}
}
