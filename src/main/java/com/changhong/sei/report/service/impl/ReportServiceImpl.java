package com.changhong.sei.report.service.impl;


import com.changhong.sei.report.dao.ReportDao;
import com.changhong.sei.report.entity.ReportEntity;
import com.changhong.sei.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;

    @Override
    public int checkExistByName(String name) {
        return reportDao.findAllByName(name).size();
    }

    @Override
    public ReportEntity queryUreportEntityByName(String name) {
        return reportDao.findByName(name);
    }

    @Override
    public List<ReportEntity> queryReportFileList() {
        return reportDao.findAll();
    }

    @Override
    @Transactional
    public int deleteReportByName(String name) {
        return reportDao.deleteByName(name);
    }

    @Override
    public ReportEntity saveReport(ReportEntity entity) {
        return reportDao.save(entity);
    }
}