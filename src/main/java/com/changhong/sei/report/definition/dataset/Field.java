package com.changhong.sei.report.definition.dataset;

/**
 * @desc：字段
 * @author：zhaohz
 * @date：2020/6/30 11:22
 */
public class Field {
	private String name;

	public Field(String name) {
		this.name=name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
