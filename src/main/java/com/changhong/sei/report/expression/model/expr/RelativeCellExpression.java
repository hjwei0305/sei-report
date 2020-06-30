package com.changhong.sei.report.expression.model.expr;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectListExpressionData;
import com.changhong.sei.report.expression.model.expr.set.CellExpression;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：关联单元格表达式
 * @author：zhaohz
 * @date：2020/6/30 14:57
 */
public class RelativeCellExpression extends CellExpression {
	private static final long serialVersionUID = 8826396779392348224L;
	public RelativeCellExpression(String cellName) {
		super(cellName);
	}
	@Override
	public boolean supportPaging(){
		return false;
	}
	@Override
	protected ExpressionData<?> compute(Cell cell, Cell currentCell, Context context) {
		List<Cell> targetCells= Utils.fetchTargetCells(currentCell, context, cellName);
		int size=targetCells.size();
		if(size==0){
			throw new ReportComputeException("Unknow cell "+cellName);
		}else if(size==1){
			return new ObjectExpressionData(targetCells.get(0).getData());
		}else{
			Cell targetCell=null;
			for(Cell c:targetCells){
				if(c.getRow()==currentCell.getRow() || c.getColumn()==currentCell.getColumn()){
					targetCell=c;
					break;
				}
			}
			if(targetCell!=null){
				return new ObjectExpressionData(targetCell.getData());
			}else{
				List<Object> list=new ArrayList<Object>();
				for(Cell c:targetCells){
					list.add(c.getData()); 
				}
				return new ObjectListExpressionData(list);
			}
		}
	}
}
