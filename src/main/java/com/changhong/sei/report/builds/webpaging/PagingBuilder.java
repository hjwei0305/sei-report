package com.changhong.sei.report.builds.webpaging;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.definition.Paper;
import com.changhong.sei.report.enums.PagingMode;
import com.changhong.sei.report.expression.model.Report;
import com.changhong.sei.report.model.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc：前端分页构造器
 * @author：zhaohz
 * @date：2020/6/30 10:16
 */
public class PagingBuilder {
	private static Map<PagingMode, Pagination> paginationMap=new HashMap<PagingMode, Pagination>();
	static{
		paginationMap.put(PagingMode.fitpage, new FitPagePagination());
		paginationMap.put(PagingMode.fixrows, new FixRowsPagination());
	}
	public static List<Page> buildPages(Report report){
		Paper paper = report.getPaper();
		Pagination pagination=paginationMap.get(paper.getPagingMode());
		List<Page> pages=pagination.doPaging(report);
		computeExistPageFunctionCells(report);
		return pages;
	}
	public static void computeExistPageFunctionCells(Report report) {
		Context context=report.getContext();
		List<Cell> existPageFunctionCells=context.getExistPageFunctionCells();
		for(Cell cell:existPageFunctionCells){
			List<BindData> dataList=context.buildCellData(cell);
			if(dataList==null || dataList.size()==0){
				continue;
			}
			BindData bindData=dataList.get(0);
			cell.setData(bindData.getValue());
			cell.setBindData(bindData.getDataList());
			cell.doFormat();
			cell.doDataWrapCompute(context);
		}
	}
}
