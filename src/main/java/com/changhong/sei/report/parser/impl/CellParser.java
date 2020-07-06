package com.changhong.sei.report.parser.impl;

import com.changhong.sei.report.definition.CellDefinition;
import com.changhong.sei.report.definition.CellStyle;
import com.changhong.sei.report.definition.ConditionPropertyItem;
import com.changhong.sei.report.definition.LinkParameter;
import com.changhong.sei.report.definition.value.Value;
import com.changhong.sei.report.enums.Expand;
import com.changhong.sei.report.exception.ReportException;
import com.changhong.sei.report.exception.ReportParseException;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.parser.Parser;
import com.changhong.sei.report.parser.impl.value.*;
import com.changhong.sei.report.utils.ExpressionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc：元素转换
 * @author：zhaohz
 * @date：2020/7/6 10:51
 */
public class CellParser implements Parser<CellDefinition> {
	private Map<String, Parser<?>> parsers=new HashMap<String, Parser<?>>();
	public CellParser() {
		parsers.put("simple-value",new SimpleValueParser());
		parsers.put("image-value",new ImageValueParser());
		parsers.put("expression-value",new ExpressionValueParser());
		parsers.put("dataset-value",new DatasetValueParser());
		parsers.put("slash-value",new SlashValueParser());
		parsers.put("zxing-value",new ZxingValueParser());
		parsers.put("chart-value",new ChartValueParser());
		parsers.put("cell-style",new CellStyleParser());
		parsers.put("link-parameter",new LinkParameterParser());
		parsers.put("condition-property-item",new ConditionParameterItemParser());
	}
	@Override
	public CellDefinition parse(Element element) {
		CellDefinition cell=new CellDefinition();
		cell.setName(element.attributeValue("name"));
		cell.setColumnNumber(Integer.valueOf(element.attributeValue("col")));
		cell.setRowNumber(Integer.valueOf(element.attributeValue("row")));
		cell.setLeftParentCellName(element.attributeValue("left-cell"));
		cell.setTopParentCellName(element.attributeValue("top-cell"));
		String rowSpan=element.attributeValue("row-span");
		if(StringUtils.isNotBlank(rowSpan)){
			cell.setRowSpan(Integer.valueOf(rowSpan));
		}
		String colSpan=element.attributeValue("col-span");
		if(StringUtils.isNotBlank(colSpan)){
			cell.setColSpan(Integer.valueOf(colSpan));
		}
		String expand=element.attributeValue("expand");
		if(StringUtils.isNotBlank(expand)){
			cell.setExpand(Expand.valueOf(expand));
		}
		String fillBlankRows=element.attributeValue("fill-blank-rows");
		if(StringUtils.isNotBlank(fillBlankRows)){
			cell.setFillBlankRows(Boolean.valueOf(fillBlankRows));
			String multiple=element.attributeValue("multiple");
			if(StringUtils.isNotBlank(multiple)){
				cell.setMultiple(Integer.valueOf(multiple));
			}
		}
		cell.setLinkTargetWindow(element.attributeValue("link-target-window"));
		String linkUrl=element.attributeValue("link-url");
		cell.setLinkUrl(linkUrl);
		if(StringUtils.isNotBlank(linkUrl)){
			if(linkUrl.startsWith(ExpressionUtils.EXPR_PREFIX) && linkUrl.endsWith(ExpressionUtils.EXPR_SUFFIX)){
				String expr=linkUrl.substring(2,linkUrl.length()-1);
				Expression urlExpression= ExpressionUtils.parseExpression(expr);
				cell.setLinkUrlExpression(urlExpression);
			}
		}
		List<LinkParameter> linkParameters=null;
		List<ConditionPropertyItem> conditionPropertyItems=null;
		for(Object obj:element.elements()){
			if(!(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			Object parseData=parseValue(ele);
			if(parseData instanceof Value){
				Value value=(Value)parseData;
				cell.setValue(value);
			}else if(parseData instanceof CellStyle){
				CellStyle cellStyle=(CellStyle)parseData;
				cell.setCellStyle(cellStyle);
			}else if(parseData instanceof LinkParameter){
				if(linkParameters==null){
					linkParameters=new ArrayList<LinkParameter>();
				}
				linkParameters.add((LinkParameter)parseData);
			}else if(parseData instanceof ConditionPropertyItem){
				if(conditionPropertyItems==null){
					conditionPropertyItems=new ArrayList<ConditionPropertyItem>();
				}
				conditionPropertyItems.add((ConditionPropertyItem)parseData);
			}
		}
		if(linkParameters!=null){
			cell.setLinkParameters(linkParameters);
		}
		cell.setConditionPropertyItems(conditionPropertyItems);
		if(cell.getValue()==null){
			throw new ReportException("Cell ["+cell.getName()+"] value not define.");
		}
		return cell;
	}
	
	private Object parseValue(Element element){
		Parser<?> parser=parsers.get(element.getName());
		if(parser!=null){
			return parser.parse(element);			
		}
		throw new ReportParseException("Unknow element :"+element.getName());
	}
}
