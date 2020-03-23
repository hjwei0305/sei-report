package com.changhong.sei.report.provider;

import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;
import com.changhong.sei.report.entity.ReportEntity;
import com.changhong.sei.report.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description 自定义报表存储器
 */
@RefreshScope
public class JpaProvider implements ReportProvider {
    private final Logger log= LoggerFactory.getLogger(getClass());

    @Value("${ureport.jpa.provider.name}")
    private String name;
    @Value("${ureport.jpa.provider.prefix}")
    private String prefix;
    @Value("${ureport.jpa.provider.disabled}")
    private boolean disabled;

    @Autowired
    private ReportService reportService;

//    @Bean
//    public ReportProvider jpaReportProvider(){
//        return new ReportProvider() {
            /**
             * 根据报表名加载报表文件
             *
             * @param file 报表名称
             * @return 返回的InputStream
             */
            @Override
            public InputStream loadReport(String file) {
                ReportEntity reportEntity = reportService.queryUreportEntityByName(getCorrectName(file));
                byte[] content = reportEntity.getContent();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
                return inputStream;
            }

            /**
             * 根据报表名，删除指定的报表文件
             *
             * @param file 报表名称
             */
            @Override
            public void deleteReport(String file) {
                reportService.deleteReportByName(getCorrectName(file));
            }

            /**
             * 获取所有的报表文件
             *
             * @return 返回报表文件列表
             */
            @Override
            public List<ReportFile> getReportFiles() {
                List<ReportEntity> list = reportService.queryReportFileList();
                List<ReportFile> reportList = new ArrayList<>();
                for (ReportEntity reportEntity : list) {
                    reportList.add(new ReportFile(reportEntity.getName(), reportEntity.getLastEditedDate()));
                }
                return reportList;
            }

            /**
             * 保存报表文件
             *
             * @param file    报表名称
             * @param content 报表的XML内容
             */
            @Override
            public void saveReport(String file, String content) {
                file = getCorrectName(file);
                ReportEntity reportEntity = reportService.queryUreportEntityByName(file);
                Date currentDate = new Date();
                if (ObjectUtils.isEmpty(reportEntity)) {
                    reportEntity = new ReportEntity();
                    reportEntity.setName(file);
                    reportEntity.setContent(content.getBytes());
                    reportEntity.setCreatedDate(currentDate);
                    reportEntity.setLastEditedDate(currentDate);
                    reportService.saveReport(reportEntity);
                } else {
                    reportEntity.setContent(content.getBytes());
                    reportEntity.setLastEditedDate(currentDate);
                    reportService.saveReport(reportEntity);
                }
            }

            /**
             * @return 返回存储器名称
             */
            @Override
            public String getName() {
                return name;
            }

            /**
             * @return 返回是否禁用
             */
            @Override
            public boolean disabled() {
                return disabled;
            }

            /**
             * @return 返回报表文件名前缀
             */
            @Override
            public String getPrefix() {
                return prefix;
            }

            /**
             * @param name
             * @return
             * @description 获取没有前缀的文件名
             */
            private String getCorrectName(String name) {
                log.info("前缀:" + prefix);
                if (name.startsWith(prefix)) {
                    name = name.substring(prefix.length(), name.length());
                }
                return name;
            }
//        };
//    }
}