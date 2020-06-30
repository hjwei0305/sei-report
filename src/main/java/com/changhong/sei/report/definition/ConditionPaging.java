package com.changhong.sei.report.definition;

import com.changhong.sei.report.enums.PagingPosition;

/**
 * @desc：分页条件
 * @author：zhaohz
 * @date：2020/6/30 10:00
 */
public class ConditionPaging {
	private PagingPosition position;
	/**
	 * 当position为after时，line用来指定当前行后多少行进行分页 
	 */
	private int line;
	
	public PagingPosition getPosition() {
		return position;
	}
	public void setPosition(PagingPosition position) {
		this.position = position;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
}
