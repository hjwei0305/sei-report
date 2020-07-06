package com.changhong.sei.report.expression.model.expr.set;

/**
 * @desc：
 * @author：zhaohz
 * @date：2020/7/1 13:38
 */
public class CellCoordinate {
	private String cellName;
	private int position;
	private boolean reverse;
	private CoordinateType coordinateType;
	
	public CellCoordinate(String cellName, CoordinateType coordinateType) {
		this.cellName = cellName;
		this.coordinateType=coordinateType;
	}
	public String getCellName() {
		return cellName;
	}
	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	public boolean isReverse() {
		return reverse;
	}
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
	public CoordinateType getCoordinateType() {
		return coordinateType;
	}
	
	@Override
	public String toString() {
		return cellName+":"+position;
	}
}
