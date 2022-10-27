package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantSearchNonHrBean;
import com.esdnl.personnel.jobs.dao.ApplicantSearchNonHrManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewMissingCoeReportRequestHandler extends RequestHandlerImpl {

	public ViewMissingCoeReportRequestHandler() {
		requiredRoles = new String[] {
				"ETHICS-REPORT-VIEWER"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				
				ArrayList<ApplicantSearchNonHrBean> apps = ApplicantSearchNonHrManager.viewMissingCoeReport();
				request.setAttribute("apps", apps);
				path = "view_missing_coe.jsp";
				
			}catch(Exception e) {
				request.setAttribute("msg", e.getMessage());
				path = "view_missing_coe.jsp";
			}
		}
		

		return path;
		}
}
