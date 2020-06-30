package com.changhong.sei.report.builds.webpaging;

import com.changhong.sei.report.expression.model.Report;

import java.util.List;

/**
 * @desc：页码接口
 * @author：zhaohz
 * @date：2020/6/30 10:17
 */
public interface Pagination {
	List<Page> doPaging(Report report);
}
