package com.changhong.sei.report;

import com.changhong.sei.report.servlet.UReportServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import javax.servlet.Servlet;

@ImportResource("classpath:context.xml")
@SpringBootApplication
@ComponentScan(basePackages = {"com.changhong.sei.**"})
public class ReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportApplication.class, args);
	}

	/**注册 UReportServlet
	 * "/ureport/*"的 urlMappings 是一定不能变的，否则系统将无法运行。
	 */
	@Bean
	public ServletRegistrationBean<Servlet> ureportServlet(){
		return new ServletRegistrationBean<>(new UReportServlet(), "/ureport/*");
	}
}