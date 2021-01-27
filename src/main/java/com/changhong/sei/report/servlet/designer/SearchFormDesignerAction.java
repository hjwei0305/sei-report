package com.changhong.sei.report.servlet.designer;

import com.changhong.sei.report.servlet.RenderPageServletAction;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @desc：查询栏
 * @author：zhaohz
 * @date：2020/7/7 9:43
 */
public class SearchFormDesignerAction extends RenderPageServletAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		VelocityContext context = new VelocityContext();
		context.put("contextPath", req.getContextPath());
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		Template template=ve.getTemplate("static/searchform.html","utf-8");
		PrintWriter writer=resp.getWriter();
		template.merge(context, writer);
		writer.close();
	}

	@Override
	public String url() {
		return "/searchFormDesigner";
	}
}
