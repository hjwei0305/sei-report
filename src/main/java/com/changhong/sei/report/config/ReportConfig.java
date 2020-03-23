package com.changhong.sei.report.config;

import com.changhong.sei.report.provider.FileProvider;
import com.changhong.sei.report.provider.JpaProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportConfig {

    @Bean
    public JpaProvider jpaProvider() {
        return new JpaProvider();
    }

    @Bean
    public FileProvider fileProvider() {
        return new FileProvider();
    }
}
