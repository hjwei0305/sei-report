package com.changhong.sei.report.servlet;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc：主servlet
 * @author：zhaohz
 * @date：2020/7/6 16:39
 */
public class UReportServlet extends HttpServlet {
	private static final long serialVersionUID = 533049461276487971L;
	public static final String URL = "/ureport";
	private Map<String, ServletAction> actionMap = new HashMap<String, ServletAction>();

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WebApplicationContext applicationContext = getWebApplicationContext(config);
		Collection<ServletAction> handlers = applicationContext.getBeansOfType(ServletAction.class).values();
		for (ServletAction handler : handlers) {
			String url = handler.url();
			if (actionMap.containsKey(url)) {
				throw new RuntimeException("Handler [" + url + "] already exist.");
			}
			actionMap.put(url, handler);
		}
	}
	
	protected WebApplicationContext getWebApplicationContext(ServletConfig config){
		return WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getContextPath() + URL;
		String uri = req.getRequestURI();
		String targetUrl = uri.substring(path.length());
		if (targetUrl.length() < 1) {
			outContent(resp, "Welcome to use ureport,please specify target url.");
			return;
		}
		int slashPos = targetUrl.indexOf("/", 1);
		if (slashPos > -1) {
			targetUrl = targetUrl.substring(0, slashPos);
		}
		ServletAction targetHandler = actionMap.get(targetUrl);
		if (targetHandler == null) {
			outContent(resp, "Handler [" + targetUrl + "] not exist.");
			return;
		}
		RequestHolder.setRequest(req);
		try{
			targetHandler.execute(req, resp);
		}catch(Exception ex){
			resp.setCharacterEncoding("UTF-8");
			PrintWriter pw=resp.getWriter();
			Throwable e=buildRootException(ex);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			String errorMsg = e.getMessage();
			if(StringUtils.isBlank(errorMsg)){
				errorMsg=e.getClass().getName();
			}
			pw.write(errorMsg);
			pw.close();				
			throw new ServletException(ex);
		}finally{
			RequestHolder.clean();
		}
	}
	private Throwable buildRootException(Throwable throwable){
		if(throwable.getCause()==null){
			return throwable;
		}
		return buildRootException(throwable.getCause());
	}

	private void outContent(HttpServletResponse resp, String msg) throws IOException {
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		pw.write("<html>");
		pw.write("<header><title>UReport Console</title></header>");
		pw.write("<body>");
		pw.write(msg);
		pw.write("</body>");
		pw.write("</html>");
		pw.flush();
		pw.close();
	}

	/*@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String protocol = req.getProtocol();
		String msg = lStrings.getString("http.method_post_not_supported");
		if (protocol.endsWith("1.1")) {
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
		} else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
		}
		resp.setContentType("application/json; charset=utf-8");
		resp.setHeader("cache-control", "no-cache");
		PrintWriter out = resp.getWriter();
		InputStreamReader insr = new InputStreamReader(request.getInputStream(),"utf-8");
		String result = "";
		int respInt = insr.read();
		while(respInt!=-1) {
			result +=(char)respInt;
			respInt = insr.read();
		}
		JSONObject jsonRet = JSONObject.parseObject(result);

	}*/
}