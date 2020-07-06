package com.changhong.sei.report.expression.model.expr.set;

import java.util.List;

/**
 * @desc：
 * @author：zhaohz
 * @date：2020/7/1 14:24
 */
public class CellCoordinateSet {
	private List<CellCoordinate> cellCoordinates;

	public CellCoordinateSet(List<CellCoordinate> cellCoordinates) {
		this.cellCoordinates = cellCoordinates;
	}
	public List<CellCoordinate> getCellCoordinates() {
		return cellCoordinates;
	}
}
