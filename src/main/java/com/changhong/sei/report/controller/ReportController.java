package com.changhong.sei.report.controller;

import com.bstek.ureport.exception.ReportComputeException;
import com.changhong.sei.report.entity.ReportEntity;
import com.changhong.sei.report.model.PageReport;
import com.changhong.sei.report.dto.PageReportDto;
import com.changhong.sei.report.service.PageExportManager;
import com.changhong.sei.report.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Api(value = "平台报表接口")
@RestController
@RequestMapping("/report")
public class ReportController {

    @Resource(name = "ureport.pageExportManager")
    private PageExportManager pageExportManager;
    @Autowired
    private ReportService reportService;

    @PostMapping("/genReport")
    @ApiOperation(value = "获取报表", notes = "参数说明：\n_u:报表全名，注意带上前后缀；\n_type:报表预览类型，1-预览，2-分页预览，3-物理分页预览；\n" +
            "_t:工具栏，默认值1,2,3,4,5,6,7,8,9，可根据具体业务状况筛选需要的打印或导出按钮；\npage:物理分页参数，页码;\n" +
            "rows:物理分页参数，每页展示条数；\n其他参数与数据集中的参数名保持一致")
    public String genReport(HttpServletRequest request) throws ServletException, SQLException {
        Map<String, Object> parameters = buildParameters(request);
        PageReport pageReport = null;
        String file=request.getParameter("_u");
        file=decode(file);
        if(StringUtils.isEmpty(file)){
            throw new ReportComputeException("Report file can not be null.");
        }
        String type = request.getParameter("_type");
        String pageIndex = request.getParameter("_i");
        if (!"3".equals(type)) {
            if (parameters.containsKey("page")) {
                parameters.remove("page");
            }
            if (parameters.containsKey("rows")) {
                parameters.remove("rows");
            }
        }
        int page = 0;
        int rows = 0;
        if (parameters.containsKey("page") && parameters.containsKey("rows")) {
            String pageNum = parameters.get("page").toString();
            String size = parameters.get("rows").toString();
            if (!pageNum.equals("0") && !size.equals("0")) {
                page = 1;
                rows = 30;
                try {
                    page = Integer.valueOf(pageNum);
                    rows = Integer.valueOf(size);
                } catch (Exception e) {
                    throw new ServletException("page和rows必须是整数");
                }
                //重构parameters，把page参数替换成startRow参数
                rebuildParam(parameters, (page-1)*rows);
            }
        }
        if("2".equals(type)) {
            if (!StringUtils.isEmpty(pageIndex) && !pageIndex.equals("0")) {
                int index = 1;
                try {
                    index = Integer.valueOf(pageIndex);
                } catch (Exception e) {
                    throw new ServletException("_i必须是整数");
                }
                pageReport = pageExportManager.exportHtml(file, request.getContextPath(), parameters, index);
            }
        }else if("3".equals(type)){
            if (page!=0&&rows!=0) {
                pageReport = pageExportManager.exportHtml(file, request.getContextPath(), parameters, page, rows);
            }
        }
        if(pageReport==null) pageReport = pageExportManager.exportHtml(file, request.getContextPath(), parameters);
        return new PageReportDto(pageReport).toString();
    }

    private Map<String, Object> buildParameters(HttpServletRequest req) {
        Map<String, Object> parameters = new HashMap();
        Enumeration enumeration = req.getParameterNames();

        while(enumeration.hasMoreElements()) {
            Object obj = enumeration.nextElement();
            if (obj != null) {
                String name = obj.toString();
                String value = req.getParameter(name);
                if (name != null && value != null && !name.startsWith("_")) {
                    parameters.put(name, this.decode(value));
                }
            }
        }

        return parameters;
    }

    private String decode(String value) {
        if (value == null) {
            return value;
        } else {
            try {
                value = URLDecoder.decode(value, "utf-8");
                value = URLDecoder.decode(value, "utf-8");
                return value;
            } catch (Exception var3) {
                return value;
            }
        }
    }

    private void rebuildParam(Map<String, Object> parameters, int startRow) {
        if(parameters.containsKey("page")){
            parameters.remove("page");
            parameters.put("startRow", startRow);
        }
    }

    @PostMapping("/getContent")
    public String genReport(String name){
        ReportEntity entity = reportService.queryUreportEntityByName(name);
        return new String(entity.getContent());
    }
}