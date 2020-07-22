package com.nlesd.bcs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.dao.ApplicationSettingsManager;

public class ViewWeeklyReportStatusRequestHandler extends RequestHandlerImpl {
	public ViewWeeklyReportStatusRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-WEEKLY-REPORT-STATUS"
		};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		
		if(request.getParameter("stype") == null) {
			//just viewing the page
			request.setAttribute("abean",  ApplicationSettingsManager.getApplicationSettings());
		}else {
			//we are updating the status
			if(request.getParameter("stype").equals("E")) {
				ApplicationSettingsManager.updateWeeklyReportStatus(1);
			}else {
				ApplicationSettingsManager.updateWeeklyReportStatus(0);
			}
			request.setAttribute("abean",  ApplicationSettingsManager.getApplicationSettings());
		}
		
			path = "admin_view_weekly_report_status.jsp";
		
		return path;
	}

}