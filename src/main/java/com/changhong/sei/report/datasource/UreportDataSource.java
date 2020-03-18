package com.changhong.sei.report.datasource;

import com.bstek.ureport.definition.datasource.BuildinDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description 内置数据源，实现BuildinDatasource接口
 */
@Component
public class UreportDataSource implements BuildinDatasource {
    private static final String NAME = "sei-dev";
    private Logger log = LoggerFactory.getLogger(UreportDataSource.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public Connection getConnection() {
        try {
            return jdbcTemplate.getDataSource().getConnection();
        } catch (SQLException e) {
            log.error("Ureport 数据源 获取连接失败！");
            e.printStackTrace();
        }
        return null;
    }
}