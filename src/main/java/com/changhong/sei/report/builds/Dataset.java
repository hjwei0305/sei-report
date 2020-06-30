package com.changhong.sei.report.builds;

import java.util.List;

/**
 * @desc：数据集
 * @author：zhaohz
 * @date：2020/6/30 10:24
 */
public class Dataset {
	private String name;
	private List<?> data;
	
	public Dataset(String name, List<?> data) {
		this.name = name;
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public List<?> getData() {
		return data;
	}
}
