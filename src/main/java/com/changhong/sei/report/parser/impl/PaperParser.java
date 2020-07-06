package com.changhong.sei.report.parser.impl;

import com.changhong.sei.report.definition.Paper;
import com.changhong.sei.report.definition.PaperSize;
import com.changhong.sei.report.enums.HtmlReportAlign;
import com.changhong.sei.report.enums.Orientation;
import com.changhong.sei.report.enums.PagingMode;
import com.changhong.sei.report.enums.PaperType;
import com.changhong.sei.report.parser.Parser;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

/**
 * @desc：纸张转换
 * @author：zhaohz
 * @date：2020/7/6 11:02
 */
public class PaperParser implements Parser<Paper> {
	@Override
	public Paper parse(Element element) {
		Paper paper=new Paper();
		String orientation=element.attributeValue("orientation");
		paper.setOrientation(Orientation.valueOf(orientation));
		paper.setPaperType(PaperType.valueOf(element.attributeValue("type")));
		if(paper.getPaperType().equals(PaperType.CUSTOM)){
			paper.setWidth(Integer.valueOf(element.attributeValue("width")));
			paper.setHeight(Integer.valueOf(element.attributeValue("height")));
		}else{
			PaperSize size=paper.getPaperType().getPaperSize();
			paper.setWidth(size.getWidth());
			paper.setHeight(size.getHeight());
		}
		String leftMargin=element.attributeValue("left-margin");
		if(StringUtils.isNotBlank(leftMargin)){
			paper.setLeftMargin(Integer.valueOf(leftMargin));			
		}
		String rightMargin=element.attributeValue("right-margin");
		if(StringUtils.isNotBlank(rightMargin)){
			paper.setRightMargin(Integer.valueOf(rightMargin));			
		}
		String topMargin=element.attributeValue("top-margin");
		if(StringUtils.isNotBlank(topMargin)){
			paper.setTopMargin(Integer.valueOf(topMargin));			
		}
		String bottomMargin=element.attributeValue("bottom-margin");
		if(StringUtils.isNotBlank(bottomMargin)){
			paper.setBottomMargin(Integer.valueOf(bottomMargin));			
		}
		paper.setPagingMode(PagingMode.valueOf(element.attributeValue("paging-mode")));
		if(paper.getPagingMode().equals(PagingMode.fixrows)){
			paper.setFixRows(Integer.valueOf(element.attributeValue("fixrows")));
		}
		String columnEnabled=element.attributeValue("column-enabled");
		if(StringUtils.isNotBlank(columnEnabled)){
			paper.setColumnEnabled(Boolean.valueOf(columnEnabled));
		}
		if(paper.isColumnEnabled()){
			paper.setColumnCount(Integer.valueOf(element.attributeValue("column-count")));
			paper.setColumnMargin(Integer.valueOf(element.attributeValue("column-margin")));
		}
		String htmlReportAlign=element.attributeValue("html-report-align");
		if(StringUtils.isNotBlank(htmlReportAlign)){
			paper.setHtmlReportAlign(HtmlReportAlign.valueOf(htmlReportAlign));
		}
		String htmlIntervalRefreshValue=element.attributeValue("html-interval-refresh-value");
		if(StringUtils.isNotBlank(htmlIntervalRefreshValue)){
			paper.setHtmlIntervalRefreshValue(Integer.valueOf(htmlIntervalRefreshValue));
		}
		paper.setBgImage(element.attributeValue("bg-image"));
		return paper;
	}
}
