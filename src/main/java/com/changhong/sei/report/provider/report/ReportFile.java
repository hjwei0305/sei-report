package com.changhong.sei.report.provider.report;

import java.util.Date;

/**
 * @desc：报表文件
 * @author：zhaohz
 * @date：2020/7/6 16:00
 */
public class ReportFile {
	private String name;
	private Date updateDate;
	
	public ReportFile(String name, Date updateDate) {
		this.name = name;
		this.updateDate = updateDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
