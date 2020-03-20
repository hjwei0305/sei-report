package com.changhong.sei.report;

import com.bstek.ureport.console.UReportServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import javax.servlet.Servlet;

@ImportResource("classpath:context.xml")
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.changhong.sei.notify.api",
		"com.changhong.sei.basic.service.client"})
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