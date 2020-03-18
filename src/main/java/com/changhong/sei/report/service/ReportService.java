package com.changhong.sei.report.service;


import com.changhong.sei.report.entity.ReportEntity;

import java.util.List;

public interface ReportService {
    /**
     *  根据报表名称检查报表是否存在
     * @param name 报表名称
     * @return
     */
    public int checkExistByName(String name);

    /**
     *  根据报表名称查询报表
     * @param name 报表名称
     * @return
     */
    public ReportEntity queryUreportEntityByName(String name);

    /**
     * 查询全部报表
     * @return
     */
    public List<ReportEntity> queryReportFileList();

    /**
     * 根据报表名称删除报表
     * @param name
     * @return
     */
    public int deleteReportByName(String name);

    /**
     *  保存更新报表
     */
    public ReportEntity saveReport(ReportEntity entity);
}
