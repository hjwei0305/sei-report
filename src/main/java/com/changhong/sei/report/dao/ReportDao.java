package com.changhong.sei.report.dao;

import com.changhong.sei.report.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportDao extends JpaRepository<ReportEntity, String>, JpaSpecificationExecutor<ReportEntity> {
    List<ReportEntity> findAllByName(String name);
    ReportEntity findByName(String name);
    int deleteByName(String name);
}