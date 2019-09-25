package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.BussingContractorSystemCustomReportBean;
import com.nlesd.bcs.dao.BuildCustomReportManager;
import com.nlesd.bcs.dao.BussingContractorSystemCustomReportManager;
public class RunSavedCustomReportRequestHandler extends RequestHandlerImpl
{
	public RunSavedCustomReportRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("rid")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		if (validate_form()) {
			int reportid = Integer.parseInt(request.getParameter("rid").toString());
			BussingContractorSystemCustomReportBean rbean = BussingContractorSystemCustomReportManager.getCustomReportsById(reportid);
			request.setAttribute("tablebody", BuildCustomReportManager.runReportSql(rbean.getReportSql()));
			request.setAttribute("tableheader",BuildCustomReportManager.buildSelectClauseSave(rbean.getReportTableFields(),rbean.getReportTables()));
			path = "admin_view_report.jsp";
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";				
		}

		return path;
	}
}