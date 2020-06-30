package com.changhong.sei.report.builds.cell.right;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.definition.BlankCellInfo;
import com.changhong.sei.report.definition.value.SimpleValue;
import com.changhong.sei.report.definition.value.Value;
import com.changhong.sei.report.enums.DuplicateType;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.model.Column;

/**
 * @author Jacky.gao
 * @since 2016年11月7日
 */
public class CellRightDuplicator {
	private Cell cell;
	private int cellColNumber;
	private DuplicateType duplicateType;
	private BlankCellInfo blankCellInfo;
	private boolean nonChild=false;
	public CellRightDuplicator(Cell cell, DuplicateType duplicateType, int cellColNumber) {
		this.cell=cell;
		this.duplicateType=duplicateType;
		this.cellColNumber=cellColNumber;
	}
	public CellRightDuplicator(Cell cell, DuplicateType duplicateType, BlankCellInfo blankCellInfo, int cellColNumber) {
		this.cell=cell;
		this.duplicateType=duplicateType;
		this.blankCellInfo=blankCellInfo;
		if(cellColNumber>0){
			this.cellColNumber=cellColNumber;
		}else{
			this.cellColNumber=cell.getColumn().getColumnNumber();
		}
	}
	
	public Cell duplicate(RightDuplicate rightDuplicate, Cell newMainCell){
		switch(duplicateType){
		case Blank:
			processBlankCell(rightDuplicate,newMainCell);
			break;
		case Self:
			processSelfBlankCell(rightDuplicate);
			break;
		case IncreseSpan:
			processIncreaseSpanCell(rightDuplicate);
			break;
		case Duplicate:
			throw new ReportComputeException("Invalid duplicator.");
		}
		return null;
	}


	private void processSelfBlankCell(RightDuplicate rightDuplicate){
		Cell newBlankCell=cell.newCell();
		newBlankCell.setValue(new SimpleValue(""));
		Column newCol=rightDuplicate.newColumn(newBlankCell.getColumn(),cellColNumber);
		newCol.getCells().add(newBlankCell);
		newBlankCell.getRow().getCells().add(newBlankCell);
		newBlankCell.setColumn(newCol);
		Cell leftParentCell=newBlankCell.getLeftParentCell();
		if(leftParentCell!=null){
			leftParentCell.addRowChild(newBlankCell);
		}
		Cell topParentCell=newBlankCell.getTopParentCell();
		if(topParentCell!=null){
			topParentCell.addColumnChild(newBlankCell);
		}
		Context context=rightDuplicate.getContext();
		context.addBlankCell(newBlankCell);
	}

	public Cell duplicateChildrenCell(RightDuplicate rightDuplicate, Cell topParent, Cell originalCell, boolean parentNonChild){
		Cell newCell=cell.newCell();
		Column newCol=rightDuplicate.newColumn(newCell.getColumn(),cellColNumber);
		newCol.getCells().add(newCell);
		newCell.getRow().getCells().add(newCell);
		newCell.setColumn(newCol);
		if(newCell.getTopParentCell()==originalCell){
			newCell.setTopParentCell(topParent);
			if(parentNonChild){
				nonChild=true;
			}
		}else{
			nonChild=true;
		}
		Cell leftParentCell=newCell.getLeftParentCell();
		if(leftParentCell!=null){
			leftParentCell.addRowChild(newCell);
		}
		Cell topParentCell=newCell.getTopParentCell();
		if(topParentCell!=null){
			topParentCell.addColumnChild(newCell);
		}
		Context context=rightDuplicate.getContext();
		Value value=newCell.getValue();
		if(value instanceof SimpleValue){
			newCell.setData(value.getValue());
			newCell.setProcessed(true);
			context.addReportCell(newCell);
		}else{
			if(nonChild){
				newCell.setValue(new SimpleValue(""));
				context.addBlankCell(newCell);
			}else{
				context.addCell(newCell);
			}
		}
		return newCell;
	}

	private void processIncreaseSpanCell(RightDuplicate rightDuplicate){
		int colSpan=cell.getColSpan();
		colSpan+=rightDuplicate.getColSize();
		if(colSpan==1){
			colSpan++;
		}
		cell.setColSpan(colSpan);
	}

	private void processBlankCell(RightDuplicate rightDuplicate, Cell newMainCell){
		Context context=rightDuplicate.getContext();
		Cell newBlankCell=cell.newColumnBlankCell(context,blankCellInfo,rightDuplicate.getMainCell());
		if(blankCellInfo.isParent() && newMainCell.getTopParentCell()==cell){
			newMainCell.setTopParentCell(newBlankCell);
		}
		Column col=rightDuplicate.newColumn(newBlankCell.getColumn(),cellColNumber);
		col.getCells().add(newBlankCell);
		newBlankCell.getRow().getCells().add(newBlankCell);
		newBlankCell.setColumn(col);
		context.addReportCell(newBlankCell);
	}
	
	public DuplicateType getDuplicateType() {
		return duplicateType;
	}
	public Cell getCell() {
		return cell;
	}
	public boolean isNonChild() {
		return nonChild;
	}
	public void setNonChild(boolean nonChild) {
		this.nonChild = nonChild;
	}
}
