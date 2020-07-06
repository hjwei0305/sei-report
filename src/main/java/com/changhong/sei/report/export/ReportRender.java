package com.changhong.sei.report.export;

import com.changhong.sei.report.builds.ReportBuilder;
import com.changhong.sei.report.cache.CacheUtils;
import com.changhong.sei.report.definition.CellDefinition;
import com.changhong.sei.report.definition.ReportDefinition;
import com.changhong.sei.report.enums.Expand;
import com.changhong.sei.report.exception.ReportException;
import com.changhong.sei.report.exception.ReportParseException;
import com.changhong.sei.report.export.builder.down.DownCellbuilder;
import com.changhong.sei.report.export.builder.right.RightCellbuilder;
import com.changhong.sei.report.expression.model.Report;
import com.changhong.sei.report.parser.ReportParser;
import com.changhong.sei.report.provider.report.ReportProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @desc：报表呈现
 * @author：zhaohz
 * @date：2020/7/6 15:52
 */
public class ReportRender implements ApplicationContextAware {
	private ReportParser reportParser;
	private ReportBuilder reportBuilder;
	private Collection<ReportProvider> reportProviders;
	private DownCellbuilder downCellParentbuilder=new DownCellbuilder();
	private RightCellbuilder rightCellParentbuilder=new RightCellbuilder();
	public Report render(String file, Map<String,Object> parameters){
		ReportDefinition reportDefinition=getReportDefinition(file);
		return reportBuilder.buildReport(reportDefinition,parameters);
	}
	
	public Report render(ReportDefinition reportDefinition, Map<String,Object> parameters){
		return reportBuilder.buildReport(reportDefinition,parameters);
	}
	
	public ReportDefinition getReportDefinition(String file){
		ReportDefinition reportDefinition= CacheUtils.getReportDefinition(file);
		if(reportDefinition==null){
			reportDefinition=parseReport(file);
			rebuildReportDefinition(reportDefinition);
			CacheUtils.cacheReportDefinition(file, reportDefinition);
		}
		return reportDefinition;
	}
	
	public void rebuildReportDefinition(ReportDefinition reportDefinition){
		List<CellDefinition> cells=reportDefinition.getCells();
		for(CellDefinition cell:cells){
			addRowChildCell(cell,cell);
			addColumnChildCell(cell,cell);
		}
		for(CellDefinition cell:cells){
			Expand expand=cell.getExpand();
			if(expand.equals(Expand.Down)){
				downCellParentbuilder.buildParentCell(cell,cells);
			}else if(expand.equals(Expand.Right)){
				rightCellParentbuilder.buildParentCell(cell,cells);
			}
		}
	}
	
	public ReportDefinition parseReport(String file){
		InputStream inputStream=null;
		try {
			inputStream=buildReportFile(file);
			ReportDefinition reportDefinition=reportParser.parse(inputStream,file);
			return reportDefinition;
		}finally{
			try {
				if(inputStream!=null){
					inputStream.close();					
				}
			} catch (IOException e) {
				throw new ReportParseException(e);
			}
		}
	}
	
	private InputStream buildReportFile(String file){
		InputStream inputStream=null;
		for(ReportProvider provider:reportProviders){
			if(file.startsWith(provider.getPrefix())){
				inputStream=provider.loadReport(file);
			}
		}
		if(inputStream==null){
			throw new ReportException("Report ["+file+"] not support.");
		}
		return inputStream;
	}
	
	private void addRowChildCell(CellDefinition cell, CellDefinition childCell){
		CellDefinition leftCell=cell.getLeftParentCell();
		if(leftCell==null){
			return;
		}
		List<CellDefinition> childrenCells=leftCell.getRowChildrenCells();
		childrenCells.add(childCell);
		addRowChildCell(leftCell,childCell);
	}
	private void addColumnChildCell(CellDefinition cell, CellDefinition childCell){
		CellDefinition topCell=cell.getTopParentCell();
		if(topCell==null){
			return;
		}
		List<CellDefinition> childrenCells=topCell.getColumnChildrenCells();
		childrenCells.add(childCell);
		addColumnChildCell(topCell,childCell);
	}
	public void setReportParser(ReportParser reportParser) {
		this.reportParser = reportParser;
	}
	public void setReportBuilder(ReportBuilder reportBuilder) {
		this.reportBuilder = reportBuilder;
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		reportProviders=applicationContext.getBeansOfType(ReportProvider.class).values();
	}
}
