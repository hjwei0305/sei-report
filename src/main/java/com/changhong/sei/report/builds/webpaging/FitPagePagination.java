package com.changhong.sei.report.builds.webpaging;

import com.changhong.sei.report.definition.Paper;
import com.changhong.sei.report.enums.Band;
import com.changhong.sei.report.enums.Orientation;
import com.changhong.sei.report.expression.model.Report;
import com.changhong.sei.report.model.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：前端自适应页面分页
 * @author：zhaohz
 * @date：2020/6/30 10:20
 */
public class FitPagePagination extends BasePagination implements Pagination {
	@Override
	public List<Page> doPaging(Report report) {
		Paper paper=report.getPaper();
		int height=paper.getHeight()-paper.getBottomMargin()-paper.getTopMargin()-5;
		if(paper.getOrientation().equals(Orientation.landscape)){
			height=paper.getWidth()-paper.getBottomMargin()-paper.getTopMargin()-5;
		}
		List<Row> rows=report.getRows();
		List<Row> headerRows=report.getHeaderRepeatRows();
		List<Row> footerRows=report.getFooterRepeatRows();
		List<Row> titleRows=report.getTitleRows();
		List<Row> summaryRows=report.getSummaryRows();
		int repeatHeaderRowHeight=report.getRepeatHeaderRowHeight(),repeatFooterRowHeight=report.getRepeatFooterRowHeight();
		int titleRowHeight=report.getTitleRowsHeight();
		int rowHeight=titleRowHeight+repeatHeaderRowHeight+repeatFooterRowHeight;
		List<Page> pages=new ArrayList<Page>();
		List<Row> pageRows=new ArrayList<Row>();
		List<Row> pageRepeatHeaders=new ArrayList<Row>();
		List<Row> pageRepeatFooters=new ArrayList<Row>();
		pageRepeatHeaders.addAll(headerRows);
		pageRepeatFooters.addAll(footerRows);
		int i=0;
		int rowSize=rows.size();
		Row row=rows.get(i);
		int pageIndex=1;
		while(row!=null){
			int rowRealHeight=row.getRealHeight();
			if(rowRealHeight==0){
				continue;
			}
			Band band=row.getBand();
			if(band!=null){
				String rowKey=row.getRowKey();
				int index=-1;
				if(band.equals(Band.headerrepeat)){
					for(int j=0;j<pageRepeatHeaders.size();j++){
						Row headerRow=pageRepeatHeaders.get(j);
						if(headerRow.getRowKey().equals(rowKey)){
							index=j;
							break;
						}
					}
					pageRepeatHeaders.remove(index);
					pageRepeatHeaders.add(index,row);
				}else if(band.equals(Band.footerrepeat)){
					for(int j=0;j<pageRepeatFooters.size();j++){
						Row footerRow=pageRepeatFooters.get(j);
						if(footerRow.getRowKey().equals(rowKey)){
							index=j;
							break;
						}
					}
					pageRepeatFooters.remove(index);
					pageRepeatFooters.add(index,row);
				}
				continue;
			}
			rowHeight+=rowRealHeight+1;
			pageRows.add(row);
			row.setPageIndex(pageIndex);
			boolean overflow=false;
			if((i+1)<rows.size()){
				Row nextRow=rows.get(i+1);
				if((rowHeight+nextRow.getRealHeight()) > height){
					overflow=true;
				}
			}
			if(!overflow && row.isPageBreak()){
				overflow=true;
			}
			if(overflow){
				Page newPage=buildPage(pageRows,pageRepeatHeaders,pageRepeatFooters,titleRows,pageIndex,report);
				pageIndex++;
				pages.add(newPage);
				rowHeight=repeatHeaderRowHeight+repeatFooterRowHeight;
				pageRows=new ArrayList<Row>();
			}
			i++;
			if(i<rowSize){
				row=rows.get(i);
			}else{
				row=null;
			}
		}
		if(pageRows.size()>0){
			Page newPage=buildPage(pageRows,pageRepeatHeaders,pageRepeatFooters,titleRows,pageIndex,report);
			pages.add(newPage);
		}
		report.getContext().setTotalPages(pages.size());
		buildPageHeaderFooter(pages, report);
		buildSummaryRows(summaryRows, pages);
		return pages;
	}
}
